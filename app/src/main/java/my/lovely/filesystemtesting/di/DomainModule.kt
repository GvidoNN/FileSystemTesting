package my.lovely.filesystemtesting.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import my.lovely.filesystemtesting.domain.repository.FilesRepository
import my.lovely.filesystemtesting.domain.usecase.GetFilesUseCase


@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetFilesUseCase(filesRepository: FilesRepository): GetFilesUseCase{
        return GetFilesUseCase(filesRepository = filesRepository)
    }
}