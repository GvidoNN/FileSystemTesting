package my.lovely.filesystemtesting.presentation.second

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import my.lovely.filesystemtesting.R
import my.lovely.filesystemtesting.databinding.FragmentSecondBinding
import my.lovely.filesystemtesting.presentation.main.FilesAdapter
import java.io.File

@AndroidEntryPoint
class SecondFragment: Fragment(R.layout.fragment_second) {

    lateinit var binding: FragmentSecondBinding
    private lateinit var adapter: FilesAdapter
    private val secondViewModel: SecondViewModel by viewModels()
    private lateinit var bundle: Bundle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("MyLog","Второй фрагмент")
        adapter = FilesAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val path = arguments?.getString("path")
        checkPermissionsAndShow(path = path.toString())

        adapter.setOnDirectoryClickListener(object: FilesAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.d("MyLog","Нажали на ${adapter.filesList[position].name}")
                Log.d("MyLog","Путь к файлу ${adapter.filesList[position].path}")
                if(adapter.filesList[position].type == "directory"){
                    bundle = Bundle()
                    bundle.putString("path", adapter.filesList[position].path)
                    Log.d("MyLog","Передали ${adapter.filesList[position].path}")
                    findNavController().navigate(R.id.action_secondFragment_to_mainFragment, bundle)
                } else{
                    val file = File(adapter.filesList[position].path)
                    val uri = FileProvider.getUriForFile(requireContext(), "my.lovely.filesystemtesting.fileprovider", file)
                    val intent = Intent(Intent.ACTION_VIEW)
                    val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(file.extension)
                    intent.setDataAndType(uri, mimeType)
                    intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }

            }
        })


    }

    private fun checkPermissionsAndShow(path: String){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                secondViewModel.getSecondFiles(path = path)
                secondViewModel.filesSecond.observe(viewLifecycleOwner){
                    adapter.setFileList(it)
                }
            } else {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                startActivity(intent)
            }
        } else {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            }
            secondViewModel.getSecondFiles(path = path)
            secondViewModel.filesSecond.observe(viewLifecycleOwner){
                adapter.setFileList(it)
            }
        }
    }


}