package mx.diossa.cashbackapp.core.utils

import zj.com.customize.sdk.Other
import mx.diossa.cashbackapp.core.utils.Command
import java.io.UnsupportedEncodingException

object PrinterCommand {

    fun POS_Set_PrtInit() = Other.byteArraysToBytes(arrayOf(Command.ESC_Init))

    fun POS_Set_LF() = Other.byteArraysToBytes(arrayOf(Command.LF))

    fun POS_Set_PrtAndFeedPaper(feed: Int): ByteArray? {
        if (feed !in 0..255) return null
        Command.ESC_J[2] = feed.toByte()
        return Other.byteArraysToBytes(arrayOf(Command.ESC_J))
    }

    fun POS_Set_PrtSelfTest() = Other.byteArraysToBytes(arrayOf(Command.US_vt_eot))

    fun POS_Set_Beep(m: Int, t: Int): ByteArray? {
        if (m !in 1..9 || t !in 1..9) return null
        Command.ESC_B_m_n[2] = m.toByte()
        Command.ESC_B_m_n[3] = t.toByte()
        return Other.byteArraysToBytes(arrayOf(Command.ESC_B_m_n))
    }

    fun POS_Set_Cut(cut: Int): ByteArray? {
        if (cut !in 0..255) return null
        Command.GS_V_m_n[3] = cut.toByte()
        return Other.byteArraysToBytes(arrayOf(Command.GS_V_m_n))
    }

    fun POS_Set_Cashbox(nMode: Int, nTime1: Int, nTime2: Int): ByteArray? {
        if (nMode !in 0..1 || nTime1 !in 0..255 || nTime2 !in 0..255) return null
        Command.ESC_p[2] = nMode.toByte()
        Command.ESC_p[3] = nTime1.toByte()
        Command.ESC_p[4] = nTime2.toByte()
        return Other.byteArraysToBytes(arrayOf(Command.ESC_p))
    }

    fun POS_Set_CodePage(page: Int): ByteArray? {
        if (page > 255) return null
        Command.ESC_t[2] = page.toByte()
        return Other.byteArraysToBytes(arrayOf(Command.ESC_t))
    }
    fun POS_Print_Text(
        text: String,
        encoding: String,
        codepage: Int,
        widthTimes: Int,
        heightTimes: Int,
        fontType: Int
    ): ByteArray? {
        if (codepage !in 0..255 || text.isEmpty()) return null
        val pbString = try { text.toByteArray(charset(encoding)) } catch (e: UnsupportedEncodingException) { return null }
        val intToWidth = byteArrayOf(0x00, 0x10, 0x20, 0x30)
        val intToHeight = byteArrayOf(0x00, 0x01, 0x02, 0x03)
        Command.GS_ExclamationMark[2] = ((intToWidth[widthTimes] + intToHeight[heightTimes]).toByte())
        Command.ESC_t[2] = codepage.toByte()
        Command.ESC_M[2] = fontType.toByte()

        return if (codepage == 0) {
            Other.byteArraysToBytes(arrayOf(Command.GS_ExclamationMark, Command.ESC_t, Command.FS_and, Command.ESC_M, pbString))
        } else {
            Other.byteArraysToBytes(arrayOf(Command.GS_ExclamationMark, Command.ESC_t, Command.FS_dot, Command.ESC_M, pbString))
        }
    }
}