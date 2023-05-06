package my.lovely.filesystemtesting.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import my.lovely.filesystemtesting.data.FilesRepositoryImpl
import my.lovely.filesystemtesting.domain.repository.FilesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideFilesRepositoryImpl() : FilesRepository {
        return FilesRepositoryImpl()
    }
}