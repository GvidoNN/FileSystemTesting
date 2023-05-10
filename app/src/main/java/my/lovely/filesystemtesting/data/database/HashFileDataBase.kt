package my.lovely.filesystemtesting.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import my.lovely.filesystemtesting.domain.model.FileHash

@Database(entities = [FileHash::class], version = 1, exportSchema = false)
abstract class HashFileDataBase : RoomDatabase() {

    abstract fun HashFileDao(): HashFileDao

}