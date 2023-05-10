package my.lovely.filesystemtesting.presentation.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import my.lovely.filesystemtesting.Const
import my.lovely.filesystemtesting.R
import my.lovely.filesystemtesting.databinding.FragmentMainBinding


@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    lateinit var binding: FragmentMainBinding
    private lateinit var adapter: FilesAdapter
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var bundle: Bundle
    private var count = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        adapter = FilesAdapter()
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

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val path = arguments?.getString(Const.PATH) ?: Const.NULL_PATH
        checkPermissionsAndShow(path = path)

        (activity as AppCompatActivity).supportActionBar?.title = if(path == Const.NULL_PATH) "Home" else path.substring(19)

        mainViewModel.files.observe(viewLifecycleOwner) {
            binding.tvMainEmpty.visibility = if(it.isEmpty()) View.VISIBLE else View.GONE
            adapter.setFileList(it)
        }

        adapter.setOnDirectoryClickListener(object : FilesAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                if (adapter.filesList[position].type ==  Const.DIRECTORY_TYPE) {
                    bundle = Bundle()
                    bundle.putString(Const.PATH, adapter.filesList[position].path)
                    findNavController().navigate(R.id.action_mainFragment_to_secondFragment, bundle)
                } else {
                    startActivity(
                        mainViewModel.openFile(
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
                startActivity(
                    Intent.createChooser(
                        mainViewModel.shareFile(
                            path = adapter.filesList[position].path,
                            context = requireContext()
                        ), "Share via"
                    )
                )
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val path = arguments?.getString(Const.PATH) ?: Const.NULL_PATH
        return when (item.itemId) {
            R.id.action_sort -> {
                if (count == 5) {
                    count = 0
                }
                count += 1
                mainViewModel.getMainFiles(path = path, sorted = count)
                true
            }
            R.id.action_change -> {
                findNavController().navigate(R.id.action_mainFragment_to_changeFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun checkPermissionsAndShow(path: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                mainViewModel.getMainFiles(path = path, sorted = count)
            } else {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                startActivity(intent)
                mainViewModel.getMainFiles(path = path, sorted = count)
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
            binding.tvPermission.isVisible = false
            mainViewModel.getMainFiles(path = path, sorted = count)
        }
    }
}