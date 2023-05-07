package my.lovely.filesystemtesting.data

import android.util.Log
import my.lovely.filesystemtesting.R
import my.lovely.filesystemtesting.domain.model.FileModel
import my.lovely.filesystemtesting.domain.repository.FilesRepository
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class FilesRepositoryImpl: FilesRepository {



    override suspend fun getMainFiles(path: String): List<FileModel>{
        var filesList = arrayListOf<FileModel>()
        val directory = File(path)
        val files = directory.listFiles()
        if(files != null){
            for (file in files) {
                if (file.isDirectory) {
                    var name = file.name
                    var date = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date(file.lastModified())).toString()
                    var size = 1
                    var path = file.path
                    var extension = file.extension
                    filesList.add(FileModel(
                        name = name,
                        changeDate = date,
                        size = size.toLong(),
                        image = R.drawable.directory,
                        path = path,
                        extension = extension,
                        type = "directory"))



                } else if(file.isFile){
                    var name = file.name
                    var date = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date(file.lastModified())).toString()
                    var size = file.length()
                    var path = file.path
                    var extension = file.extension
                    filesList.add(FileModel(
                        name = name,
                        changeDate = date,
                        size = size,
                        image = R.drawable.txt,
                        path = path,
                        extension = extension,
                        type = "txt"))
                }
                Log.d("MyLog","${file.extension}")
            }
        }


        return filesList
    }
}
