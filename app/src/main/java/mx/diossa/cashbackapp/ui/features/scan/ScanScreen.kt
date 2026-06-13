package mx.diossa.cashbackapp.ui.features.scan

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import mx.diossa.cashbackapp.ui.components.QrInstructions
import mx.diossa.cashbackapp.ui.features.exchange.ExchangeViewModel
import mx.diossa.cashbackapp.ui.features.navegation.Screen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalGetImage::class)
@Composable
fun ScanScreen(navController: NavController, viewModel: ScanViewModel = hiltViewModel(), exchangeViewModel: ExchangeViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val cameraExecutor = remember { ContextCompat.getMainExecutor(context) }
    val validationState by viewModel.validationState.collectAsState()

    var hasPermission by remember { mutableStateOf(false) }

    // Pide permiso
    val permLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted -> hasPermission = granted }

    LaunchedEffect(validationState) {
        (validationState as? QrValidationState.Success)?.let { successState ->
            exchangeViewModel.setTicketDetails(successState.details)
            navController.navigate(Screen.Products.route)
            viewModel.resetState()
        }
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            permLauncher.launch(Manifest.permission.CAMERA)
        } else hasPermission = true
    }

    Box(Modifier.fillMaxSize()) {
        if (hasPermission) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->
                    val previewView = PreviewView(ctx).apply {
                        scaleType = PreviewView.ScaleType.FILL_CENTER
                    }

                    // Espera a que ProcessCameraProvider esté listo
                    cameraProviderFuture.addListener({
                        val cameraProvider = cameraProviderFuture.get()

                        // 1) Preview
                        val previewUseCase = Preview.Builder()
                            .setTargetRotation(previewView.display.rotation)
                            .build()
                            .also { it.setSurfaceProvider(previewView.surfaceProvider) }

                        // 2) ImageAnalysis
                        val analysisUseCase = ImageAnalysis.Builder()
                            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                            .build()
                            .also { useCase ->
                                // Crea el scanner **una vez**
                                val scanner = BarcodeScanning.getClient(
                                    BarcodeScannerOptions.Builder()
                                        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                                        .build()
                                )
                                useCase.setAnalyzer(cameraExecutor) { proxy ->
                                    proxy.image?.let { mediaImage ->
                                        val image = InputImage.fromMediaImage(
                                            mediaImage,
                                            proxy.imageInfo.rotationDegrees
                                        )
                                        scanner.process(image)
                                            .addOnSuccessListener { barcodes ->
                                                barcodes.firstNotNullOfOrNull { it.rawValue }
                                                    ?.let { viewModel.processScannedCode(it) }
                                            }
                                            .addOnCompleteListener { proxy.close() }
                                    } ?: proxy.close()
                                }
                            }

                        // 3) Bind
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycle,
                            CameraSelector.DEFAULT_BACK_CAMERA,
                            previewUseCase,
                            analysisUseCase
                        )
                    }, cameraExecutor)

                    previewView
                }
            )
            when (val state = validationState) {
                is QrValidationState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is QrValidationState.Error -> {
                    QrInstructions(
                        state.message,
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                    LaunchedEffect(state) {
                        kotlinx.coroutines.delay(3000)
                        viewModel.resetState()
                    }
                }
                is QrValidationState.Success -> {
                    LaunchedEffect(state) {
                        val cashbackJson = Gson().toJson(state.details)
                        val encodedJson = URLEncoder.encode(cashbackJson, StandardCharsets.UTF_8.toString())
                        navController.navigate("ticket/$encodedJson/${state.isValid}")
                        viewModel.resetState()
                    }
                }
                QrValidationState.Idle -> {
                    QrInstructions(
                        "Enfoca el código QR dentro del recuadro",
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .background(Color.Black.copy(alpha = 0.4f))
                            .padding(8.dp)
                    )
                }
            }
        }

        QrInstructions(
            "Enfoca el código QR dentro del recuadro",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .background(Color.Black.copy(alpha = 0.4f))
                .padding(8.dp)
        )
    }

}