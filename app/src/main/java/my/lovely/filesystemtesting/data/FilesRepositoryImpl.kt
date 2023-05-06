package my.lovely.filesystemtesting.data

import android.os.Environment
import android.util.Log
import my.lovely.filesystemtesting.domain.model.FileModel
import my.lovely.filesystemtesting.domain.repository.FilesRepository
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class FilesRepositoryImpl: FilesRepository {

    private var filesList = arrayListOf<FileModel>()

    override suspend fun getMainFiles(): List<FileModel>{
        val directory = File(Environment.getExternalStorageDirectory().toString())
        val files = directory.listFiles()
        for (file in files) {
            if (file.isDirectory) {
                Log.d("MyLog", "Directory: ${file.name}")
                val directory = file.listFiles()
                for (txt in directory) {
                    if (txt.isFile) {
                        var name = txt.name
                        var date = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date(txt.lastModified())).toString()
                        var size = "${txt.length()} bytes"
                        filesList.add(FileModel(
                            name = name,
                            changeDate = date,
                            size = size,
                            image = 123123
                        ))

                        Log.d("MyLog", "File: ${txt.name}")
                        Log.d("MyLog", "Last modified: ${
                            SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date(txt.lastModified()))}")
                        Log.d("MyLog", "Size: ${txt.length()} bytes")
                        Log.d("MyLog", "Readable: ${txt.canRead()}")
                        Log.d("MyLog", "Writable: ${txt.canWrite()}")
                    }
                }
            } else {
                Log.d("MyLog", "File: ${file.name}")
            }
        }

        return filesList
    }
}