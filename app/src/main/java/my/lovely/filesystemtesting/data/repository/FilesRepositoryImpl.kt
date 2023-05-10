package my.lovely.filesystemtesting.data.repository

import my.lovely.filesystemtesting.Const
import my.lovely.filesystemtesting.data.database.HashFileDao
import my.lovely.filesystemtesting.R
import my.lovely.filesystemtesting.domain.model.FileModel
import my.lovely.filesystemtesting.domain.repository.FilesRepository
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class FilesRepositoryImpl @Inject constructor(private val hashFileDao: HashFileDao) : FilesRepository {

    private val fileList = ArrayList<FileModel>()
    override fun getDaoDb(): HashFileDao {
        return hashFileDao
    }
    override suspend fun getMainFiles(path: String): List<FileModel> {
        var filesList = arrayListOf<FileModel>()
        val directory = File(path)
        val files = directory.listFiles()
        if (files != null) {
            for (file in files) {
                if (file.isDirectory) {
                    filesList.add(getInfoFiles(file = file, type = Const.DIRECTORY_TYPE))
                } else if (file.isFile) {
                    filesList.add(getInfoFiles(file = file, type = "ligma"))
                }
            }
        }
        return filesList
    }

    override suspend fun getAllFiles(): List<FileModel>{
        val root = File(Const.NULL_PATH)
        val files = root.listFiles()
        if (files != null) {
            for (file in files) {
                if (file.isDirectory) {
                    getDir(file.path)
                } else {
                    fileList.add(getInfoFiles(file = file, type = "ligma"))
                }
            }
        }
        return fileList
    }

    private fun getDir(dirPath: String) {
        val dir = File(dirPath)
        val files = dir.listFiles()
        if (files != null) {
            for (file in files) {
                if (file.isDirectory) {
                    getDir(file.absolutePath)
                } else {
                    fileList.add(getInfoFiles(file = file, type = "ligma"))
                }
            }
        }
    }

    private fun getInfoFiles(file: File, type: String): FileModel{
        val name = file.name
        val date = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date(file.lastModified())).toString()
        val size = file.length()
        val path = file.path
        val extension = file.extension
        val type = type
        val hash = file.hashCode().toString()
        val file = FileModel(name = name,
            changeDate = date,
            size = size,
            image = R.drawable.txt,
            path = path,
            extension = extension,
            type = type,
            hash = hash)
        return file
    }
}
