package my.lovely.filesystemtesting.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import my.lovely.filesystemtesting.data.database.HashFileDao
import my.lovely.filesystemtesting.data.database.HashFileDataBase
import my.lovely.filesystemtesting.data.repository.FilesRepositoryImpl
import my.lovely.filesystemtesting.domain.repository.FilesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideFilesRepositoryImpl(hashFileDao: HashFileDao) : FilesRepository {
        return FilesRepositoryImpl(hashFileDao = hashFileDao)
    }

    @Provides
    fun provideHashFileDao(appDatabase: HashFileDataBase): HashFileDao {
        return appDatabase.HashFileDao()
    }
}