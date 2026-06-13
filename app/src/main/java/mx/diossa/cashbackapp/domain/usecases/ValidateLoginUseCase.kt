package mx.diossa.cashbackapp.domain.usecases

import mx.diossa.cashbackapp.data.local.entity.UserEntity
import mx.diossa.cashbackapp.data.remote.dto.LoginData
import mx.diossa.cashbackapp.data.remote.dto.LoginRequest
import mx.diossa.cashbackapp.data.repository.UserRepository
import javax.inject.Inject

class ValidateLoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(username: String, password: String): LoginRequest {
        if(username.isBlank() || password.isBlank()){
            throw Exception("Los campos no pueden estar vacíos")
        }
        return userRepository.validateUser(username, password)
    }
}