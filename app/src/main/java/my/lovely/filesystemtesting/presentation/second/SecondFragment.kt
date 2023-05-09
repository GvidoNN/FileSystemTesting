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
import android.view.*
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
class SecondFragment : Fragment(R.layout.fragment_second) {

    lateinit var binding: FragmentSecondBinding
    private lateinit var adapter: FilesAdapter
    private val secondViewModel: SecondViewModel by viewModels()
    private lateinit var bundle: Bundle
    private var count = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = FilesAdapter()
        setHasOptionsMenu(true)
        Log.d("MyLog","OnCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater)
        return binding.root
        Log.d("MyLog","OnCreateView")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("MyLog",count.toString())
        Log.d("MyLog","OnViewCreated")

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val path = arguments?.getString("path")
        checkPermissionsAndShow(path = path.toString())

        secondViewModel.filesSecond.observe(viewLifecycleOwner) {
            adapter.setFileList(it)
        }

        adapter.setOnDirectoryClickListener(object : FilesAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                if (adapter.filesList[position].type == "directory") {
                    bundle = Bundle()
                    bundle.putString("path", adapter.filesList[position].path)
                    findNavController().navigate(R.id.action_secondFragment_to_mainFragment, bundle)
                } else {
                    startActivity(
                        secondViewModel.openFile(
                            path = adapter.filesList[position].path,
                            context = requireContext(),
                            type = adapter.filesList[position].type
                        )
                    )
                }

            }
        })

        adapter.setOnShareFileListener(object : FilesAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                startActivity(Intent.createChooser(secondViewModel.shareFile(path = adapter.filesList[position].path, context = requireContext()), "Share via"))
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
                if (count == 5) {
                    count = 0
                }
                count += 1
                secondViewModel.getSecondFiles(path = path, typeSorted = count)
                true
            }
            R.id.action_change -> {
                findNavController().navigate(R.id.action_secondFragment_to_changeFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun checkPermissionsAndShow(path: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                secondViewModel.getSecondFiles(path = path, typeSorted = count)
            } else {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                startActivity(intent)
            }
        } else {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1
                )
            }
            secondViewModel.getSecondFiles(path = path, typeSorted = count)
        }
    }


}