package my.lovely.filesystemtesting

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import my.lovely.filesystemtesting.databinding.ActivityMainBinding
import my.lovely.filesystemtesting.domain.model.FileModel
import my.lovely.filesystemtesting.presentation.FilesAdapter
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var adapter: FilesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        adapter = FilesAdapter()
        setContentView(view)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                Log.d("MyLog", "Есть разрешение")
                val directory = File(Environment.getExternalStorageDirectory().toString())
                val files = directory.listFiles()
                for (file in files) {
                    if (file.isDirectory) {
                        Log.d("MyLog", "Directory: ${file.name}")
                        val directory = file.listFiles()
                        for (txt in directory) {
                            if (txt.isFile) {
                                Log.d("MyLog", "file: ${txt.name}")
                            }
                        }
                    } else {
                        Log.d("MyLog", "File: ${file.name}")
                    }
                }
            } else {
                Log.d("MyLog", "Разрешения нет")
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                startActivity(intent)
            }
        } else {
            Log.d("MyLog", "<R")
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            }
            val directory = File(Environment.getExternalStorageDirectory().toString())
            val files = directory.listFiles()
            for (file in files) {
                if (file.isDirectory) {
                    Log.d("MyLog", "Directory: ${file.name}")
                    val directory = file.listFiles()
                    for (txt in directory) {
                        if (txt.isFile) {
                            Log.d("MyLog", "File: ${txt.name}")
                            Log.d("MyLog", "Last modified: ${SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(
                                Date(txt.lastModified())
                            )}")
                            Log.d("MyLog", "Size: ${txt.length()} bytes")
                            Log.d("MyLog", "Readable: ${txt.canRead()}")
                            Log.d("MyLog", "Writable: ${txt.canWrite()}")
                        }
                    }
                } else {
                    Log.d("MyLog", "File: ${file.name}")
                }
            }
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        var fileList = arrayListOf<FileModel>(
            FileModel("ligma.txt",123123,"12.02.2023","144kb"),
            FileModel("ligma.txt",123123,"12.02.2023","144kb")
        )

        adapter.setFileList(fileList)



    }


}