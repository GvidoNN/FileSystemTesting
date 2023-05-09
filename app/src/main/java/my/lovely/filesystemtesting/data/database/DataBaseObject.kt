package my.lovely.filesystemtesting.data.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import my.lovely.booksearcher2.data.database.HashFileDataBase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseObject {

    @Volatile
    private var INSTANCE: HashFileDataBase? = null

    @Provides
    @Singleton
    fun getInstance(@ApplicationContext context: Context): HashFileDataBase {
        synchronized(this) {
            var instance = INSTANCE
            if(instance == null){
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    HashFileDataBase::class.java,
                    "hash_file_database"
                ).build()
            }
            return instance
        }
    }
}