package my.lovely.filesystemtesting.domain.repository

import my.lovely.filesystemtesting.data.database.HashFileDao
import my.lovely.filesystemtesting.domain.model.FileModel

interface FilesRepository {

    suspend fun getMainFiles(path: String): List<FileModel>

    fun getDaoDb(): HashFileDao

    suspend fun getAllFiles(): List<FileModel>
}