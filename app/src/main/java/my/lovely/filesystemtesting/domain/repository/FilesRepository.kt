package my.lovely.filesystemtesting.domain.repository

import my.lovely.filesystemtesting.domain.model.FileModel

interface FilesRepository {

    suspend fun getMainFiles(path: String): List<FileModel>
}