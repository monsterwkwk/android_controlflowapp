package id.co.iconpln.controlflowapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDatabaseDao {

    @Insert
    fun insertUser(user: FavoriteUser)

    @Query("DELETE FROM fav_user_table WHERE userId = :key")
    fun deleteUser(key: Int)

    @Query("SELECT * FROM fav_user_table ORDER BY favUserId DESC")
    fun getAllUsers(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM fav_user_table WHERE userId = :key")
    fun getFavUser(key: Int): LiveData<FavoriteUser>
    
}