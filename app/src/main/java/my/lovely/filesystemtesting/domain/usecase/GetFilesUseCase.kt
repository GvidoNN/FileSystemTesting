package my.lovely.filesystemtesting.domain.usecase

import my.lovely.filesystemtesting.domain.model.FileModel
import my.lovely.filesystemtesting.domain.repository.FilesRepository
import javax.inject.Inject

class GetFilesUseCase @Inject constructor(private val filesRepository: FilesRepository) {

    suspend fun getMainFiles(): List<FileModel> {
        val result = filesRepository.getMainFiles()
        return result
    }
}