package mx.diossa.cashbackapp.data.repository

import mx.diossa.cashbackapp.data.datasource.local.UserLocalDataSource
import mx.diossa.cashbackapp.data.entity.UserEntity
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val localDataSource: UserLocalDataSource
){
    suspend fun validateUser(username: String, password: String): UserEntity?{
        localDataSource.initializeUserIfNeeded()
        return localDataSource.validateUser(username, password)
    }
}