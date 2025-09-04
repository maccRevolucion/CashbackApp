package mx.diossa.cashbackapp.domain.usecases

import mx.diossa.cashbackapp.data.local.entity.UserEntity
import mx.diossa.cashbackapp.data.remote.dto.LoginData
import mx.diossa.cashbackapp.data.repository.UserRepository
import javax.inject.Inject

class ValidateLoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(username: String, password: String): Result<LoginData> {
        if(username.isBlank() || password.isBlank()){
            return Result.failure(Exception("Los campos no pueden estar vacíos"))
        }
        return userRepository.login(username, password)
    }
}