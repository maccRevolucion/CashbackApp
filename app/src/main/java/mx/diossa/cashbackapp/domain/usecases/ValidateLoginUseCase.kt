package mx.diossa.cashbackapp.domain.usecases

import mx.diossa.cashbackapp.data.entity.UserEntity
import mx.diossa.cashbackapp.data.repository.UserRepository
import javax.inject.Inject

class ValidateLoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(username: String, password: String): Result<UserEntity>{
        if(username.isBlank() || password.isBlank()){
            return Result.failure(Exception("Campos vacios"))
        }
        val user = userRepository.validateUser(username, password)
        return if (user != null){
            Result.success(user)
        } else{
            Result.failure(Exception("Credenciales invalidas"))
        }
    }
}