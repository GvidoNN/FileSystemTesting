package my.lovely.filesystemtesting.data

import android.os.Environment
import android.util.Log
import my.lovely.filesystemtesting.R
import my.lovely.filesystemtesting.domain.model.FileModel
import my.lovely.filesystemtesting.domain.repository.FilesRepository
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class FilesRepositoryImpl: FilesRepository {



    override suspend fun getMainFiles(path: String): List<FileModel>{
        //Environment.getExternalStorageDirectory().toString()
        var filesList = arrayListOf<FileModel>()
        val directory = File(path)
        val files = directory.listFiles()
        Log.d("MyLog","В репозитории и тут путь ${path}")
        for (file in files) {
            if (file.isDirectory) {
//                Log.d("MyLog", "Directory: ${file.name}")
                var name = file.name
                var date = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date(file.lastModified())).toString()
                var size = ""
                var path = file.path
                filesList.add(FileModel(
                    name = name,
                    changeDate = date,
                    size = size,
                    image = R.drawable.directory,
                    path = path,
                    type = "directory"))
                /*Log.d("MyLog", "Directory: ${file.name}")
                Log.d("MyLog", "Last modified: ${
                    SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date(file.lastModified()))}")
                Log.d("MyLog", "Size: ${file.length()} bytes")
                Log.d("MyLog", "Readable: ${file.canRead()}")
                Log.d("MyLog","Path: ${file.path}")
                Log.d("MyLog", "Writable: ${file.canWrite()}")*/


            } else {
//                Log.d("MyLog", "File: ${file.name}")
                var name = file.name
                var date = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date(file.lastModified())).toString()
                var size = "${file.length()} bytes"
                var path = file.path
                filesList.add(FileModel(
                    name = name,
                    changeDate = date,
                    size = size,
                    image = R.drawable.txt,
                    path = path,
                    type = "txt"))
//                Log.d("MyLog", "File: ${file.name}")
//                Log.d("MyLog", "Last modified: ${
//                    SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date(file.lastModified()))}")
//                Log.d("MyLog", "Size: ${file.length()} bytes")
//                Log.d("MyLog","Path: ${file.path}")
//                Log.d("MyLog", "Readable: ${file.canRead()}")
//                Log.d("MyLog", "Writable: ${file.canWrite()}")
            }
        }

        return filesList
    }
}
