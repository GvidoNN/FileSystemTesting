package my.lovely.filesystemtesting.presentation.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
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
    private var count = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
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
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        adapter = FilesAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val path = arguments?.getString("path") ?: "/storage/emulated/0/"
        checkPermissionsAndShow(path = path)

        adapter.setOnDirectoryClickListener(object: FilesAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                if(adapter.filesList[position].type == "directory"){
                    bundle = Bundle()
                    bundle.putString("path", adapter.filesList[position].path)
                    findNavController().navigate(R.id.action_mainFragment_to_secondFragment, bundle)
                } else{
                startActivity(mainViewModel.openFile(path = adapter.filesList[position].path, context = requireContext(), type = adapter.filesList[position].type))
            }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val path = arguments?.getString("path") ?: "/storage/emulated/0/"
        return when (item.itemId) {
            R.id.action_sort -> {
                if(count == 4){
                    count = 0
                }
                Toast.makeText(requireContext(),"Sort!",Toast.LENGTH_SHORT).show()
                count += 1
                mainViewModel.getMainFiles(path = path, sorted = count)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun checkPermissionsAndShow(path: String){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                mainViewModel.getMainFiles(path = path, sorted = 1)
                mainViewModel.files.observe(viewLifecycleOwner){
                    adapter.setFileList(it)
                }
            } else {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                binding.textView.isVisible = true
                startActivity(intent)
            }
        } else {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            }
            binding.textView.isVisible = false
            mainViewModel.getMainFiles(path = path, sorted = 1)
            mainViewModel.files.observe(viewLifecycleOwner){
                adapter.setFileList(it)
            }
        }
    }


}