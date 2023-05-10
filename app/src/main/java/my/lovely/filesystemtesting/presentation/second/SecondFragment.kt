package my.lovely.filesystemtesting.presentation.second

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
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
import my.lovely.filesystemtesting.databinding.FragmentSecondBinding
import my.lovely.filesystemtesting.presentation.main.FilesAdapter

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
    }

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

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val path = arguments?.getString(Const.PATH)
        if (path != null) {
            secondViewModel.getSecondFiles(path = path, typeSorted = count)
        }
        (activity as AppCompatActivity).supportActionBar?.title = if(path == Const.NULL_PATH) "Home" else path!!.substring(19)

        secondViewModel.filesSecond.observe(viewLifecycleOwner) {
            binding.tvSecondEmpty.visibility = if(it.size == 0) View.VISIBLE else View.GONE
            Log.d("MyLog",it.size.toString())
            adapter.setFileList(it)
        }

        adapter.setOnDirectoryClickListener(object : FilesAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                if (adapter.filesList[position].type ==  Const.DIRECTORY_TYPE) {
                    bundle = Bundle()
                    bundle.putString(Const.PATH, adapter.filesList[position].path)
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
        val path = arguments?.getString(Const.PATH) ?: Const.NULL_PATH
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


}