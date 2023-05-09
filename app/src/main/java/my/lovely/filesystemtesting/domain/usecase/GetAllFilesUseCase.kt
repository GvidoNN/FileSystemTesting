package my.lovely.filesystemtesting.domain.usecase

import my.lovely.filesystemtesting.domain.model.FileModel
import my.lovely.filesystemtesting.domain.repository.FilesRepository
import javax.inject.Inject

class GetAllFilesUseCase @Inject constructor(private val filesRepository: FilesRepository) {

    suspend fun getAllFiles(): List<FileModel> {
        return filesRepository.getAllFiles()
    }
}