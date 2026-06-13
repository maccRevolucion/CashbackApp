package mx.diossa.cashbackapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mx.diossa.cashbackapp.data.local.entity.UserEntity
import mx.diossa.cashbackapp.data.remote.dto.LoginRequest

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE username = :username and password = :password")
    suspend fun getUser(username: String, password: String): LoginRequest

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserEntity>)

    @Query("SELECT COUNT(*) FROM users")
    suspend fun getCount(): Int
}