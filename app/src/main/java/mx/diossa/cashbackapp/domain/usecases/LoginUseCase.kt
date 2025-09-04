package mx.diossa.cashbackapp.domain.usecases

import mx.diossa.cashbackapp.data.remote.dto.LoginData
import mx.diossa.cashbackapp.data.repository.UserRepository
import mx.diossa.cashbackapp.domain.model.UserModel
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: UserRepository
){
    suspend operator fun invoke(username: String, password: String): Result<LoginData> {
        return repository.login(username, password)
    }
}