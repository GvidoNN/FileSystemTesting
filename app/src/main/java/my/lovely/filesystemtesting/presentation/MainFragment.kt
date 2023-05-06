package my.lovely.filesystemtesting.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import my.lovely.filesystemtesting.R
import my.lovely.filesystemtesting.databinding.ActivityMainBinding
import my.lovely.filesystemtesting.databinding.FragmentMainBinding
import my.lovely.filesystemtesting.domain.model.FileModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class MainFragment: Fragment(R.layout.fragment_main) {

    lateinit var binding: FragmentMainBinding
    private lateinit var adapter: FilesAdapter
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = FilesAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        checkPermissionsAndShow()

        adapter.setOnDirectoryClickListener(object: FilesAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                Log.d("MyLog","Нажали на ${adapter.filesList[position].name}")
                Log.d("MyLog","Путь к файлу ${adapter.filesList[position].path}")
                mainViewModel.getMainFiles(adapter.filesList[position].path)
            }
        })


    }

    private fun checkPermissionsAndShow(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                Log.d("MyLog", "Есть разрешение")
                mainViewModel.getMainFiles(path = "/storage/emulated/0/")
                mainViewModel.files.observe(viewLifecycleOwner){
                    adapter.setFileList(it)
                }
            } else {
                Log.d("MyLog", "Разрешения нет")
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                startActivity(intent)
            }
        } else {
            Log.d("MyLog", "<R")
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            }
            mainViewModel.getMainFiles("/storage/emulated/0/")
            mainViewModel.files.observe(viewLifecycleOwner){
                adapter.setFileList(it)
            }
        }
    }


}