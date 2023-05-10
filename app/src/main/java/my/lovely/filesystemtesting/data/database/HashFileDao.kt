package my.lovely.filesystemtesting.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import my.lovely.filesystemtesting.domain.model.FileHash

@Dao
interface HashFileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHashFile(file: FileHash)

    @Update
    suspend fun updateHashFile(file: FileHash)

    @Delete
    suspend fun deleteHashFile(file: FileHash)

    @Query("SELECT * FROM hash_file_data_table")
    fun getAllHashFiles(): LiveData<List<FileHash>>
}