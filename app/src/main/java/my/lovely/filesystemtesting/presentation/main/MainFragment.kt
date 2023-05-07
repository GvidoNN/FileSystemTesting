package my.lovely.filesystemtesting.presentation.main

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import my.lovely.filesystemtesting.R
import my.lovely.filesystemtesting.databinding.FragmentMainBinding


@AndroidEntryPoint
class MainFragment: Fragment(R.layout.fragment_main) {

    lateinit var binding: FragmentMainBinding
    private lateinit var adapter: FilesAdapter
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var bundle: Bundle

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
        Log.d("MyLog","В первом фрагменте")
        adapter = FilesAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val path = arguments?.getString("path") ?: "/storage/emulated/0/"
        checkPermissionsAndShow(path = path.toString())

        adapter.setOnDirectoryClickListener(object: FilesAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.d("MyLog", "Нажали на ${adapter.filesList[position].name}")
                Log.d("MyLog", "Путь к файлу ${adapter.filesList[position].path}")
                bundle = Bundle()
                bundle.putString("path", adapter.filesList[position].path)
                Log.d("MyLog","Передали ${adapter.filesList[position].path}")
                findNavController().navigate(R.id.action_mainFragment_to_secondFragment, bundle)
            }
        })


    }

    private fun checkPermissionsAndShow(path: String){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                mainViewModel.getMainFiles(path = path)
                mainViewModel.files.observe(viewLifecycleOwner){
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
            mainViewModel.getMainFiles(path)
            mainViewModel.files.observe(viewLifecycleOwner){
                adapter.setFileList(it)
            }
        }
    }


}