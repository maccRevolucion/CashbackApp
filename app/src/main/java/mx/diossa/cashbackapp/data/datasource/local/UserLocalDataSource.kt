package mx.diossa.cashbackapp.data.datasource.local

import mx.diossa.cashbackapp.data.dao.UserDao
import mx.diossa.cashbackapp.data.entity.UserEntity
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
    private val userDao: UserDao
) {
    suspend fun validateUser(username: String, password: String): UserEntity? {
        return userDao.getUser(username, password)
    }

    suspend fun initializeUserIfNeeded(){
        if (userDao.getCount() == 0){
            val initialUsers = listOf(
                UserEntity("test","password123"),
                UserEntity("mcordero","123"),
                UserEntity("user1","pass1"),
                UserEntity("user2","pass2"),
                UserEntity("admin","adminpass")
            )
            userDao.insertAll(initialUsers)
        }
    }
}