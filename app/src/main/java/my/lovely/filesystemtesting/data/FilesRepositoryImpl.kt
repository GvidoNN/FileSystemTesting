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

    private var filesList = arrayListOf<FileModel>()

    override suspend fun getMainFiles(path: String): List<FileModel>{
        //Environment.getExternalStorageDirectory().toString()
        val directory = File(path)
        val files = directory.listFiles()
        for (file in files) {
            if (file.isDirectory) {
                Log.d("MyLog", "Directory: ${file.name}")
                var name = file.name
                var date = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date(file.lastModified())).toString()
                var size = ""
                var path = file.path
                filesList.add(FileModel(
                    name = name,
                    changeDate = date,
                    size = size,
                    image = R.drawable.directory,
                    path = path))
                /*Log.d("MyLog", "Directory: ${file.name}")
                Log.d("MyLog", "Last modified: ${
                    SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date(file.lastModified()))}")
                Log.d("MyLog", "Size: ${file.length()} bytes")
                Log.d("MyLog", "Readable: ${file.canRead()}")
                Log.d("MyLog","Path: ${file.path}")
                Log.d("MyLog", "Writable: ${file.canWrite()}")*/


            } else {
                Log.d("MyLog", "File: ${file.name}")
                var name = file.name
                var date = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date(file.lastModified())).toString()
                var size = "${file.length()} bytes"
                var path = file.path
                filesList.add(FileModel(
                    name = name,
                    changeDate = date,
                    size = size,
                    image = R.drawable.txt,
                    path = path))
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

//if (txt.isFile) {
//    var name = txt.name
//    var date = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date(txt.lastModified())).toString()
//    var size = "${txt.length()} bytes"
//    filesList.add(FileModel(
//        name = name,
//        changeDate = date,
//        size = size,
//        image = 123123
//    ))
//
//    Log.d("MyLog", "File: ${txt.name}")
//    Log.d("MyLog", "Last modified: ${
//        SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date(txt.lastModified()))}")
//    Log.d("MyLog", "Size: ${txt.length()} bytes")
//    Log.d("MyLog", "Readable: ${txt.canRead()}")
//    Log.d("MyLog", "Writable: ${txt.canWrite()}")
//}

/*val directory = file.listFiles()
for (txt in directory) {
    var name = txt.name
    var date = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date(txt.lastModified())).toString()
    var size = "${txt.length()} bytes"
    filesList.add(FileModel(
        name = name,
        changeDate = date,
        size = size,
        image = 123123))
    Log.d("MyLog", "File: ${txt.name}")
    Log.d("MyLog", "Last modified: ${
        SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date(txt.lastModified()))}")
    Log.d("MyLog", "Size: ${txt.length()} bytes")
    Log.d("MyLog", "Readable: ${txt.canRead()}")
    Log.d("MyLog", "Writable: ${txt.canWrite()}")
}*/