package my.lovely.booksearcher2.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import my.lovely.filesystemtesting.domain.model.FileHash

@Dao
interface HashFileDao {

    @Insert
    suspend fun insertHashFile(file: FileHash)

    @Update
    suspend fun updateHashFile(file: FileHash)

    @Delete
    suspend fun deleteHashFile(file: FileHash)

    @Query("SELECT * FROM hash_file_data_table")
    fun getAllHashFiles(): LiveData<List<FileHash>>
}