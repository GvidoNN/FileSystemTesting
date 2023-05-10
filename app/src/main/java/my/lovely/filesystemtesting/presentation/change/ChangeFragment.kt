package my.lovely.filesystemtesting.presentation.change

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import my.lovely.filesystemtesting.R
import my.lovely.filesystemtesting.databinding.FragmentChangeBinding


@AndroidEntryPoint
class ChangeFragment: Fragment(R.layout.fragment_change) {

    lateinit var binding: FragmentChangeBinding
    private lateinit var adapter: ChangeFilesAdapter
    private val changeViewModel: ChangeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ChangeFilesAdapter()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangeBinding.inflate(inflater)
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

        changeViewModel.allFiles.observe(viewLifecycleOwner){
            changeViewModel.changedFiles(it)
        }

        changeViewModel.files.observe(viewLifecycleOwner){
            binding.tvChangeEmpty.visibility = if(it.isEmpty()) View.VISIBLE else View.GONE
            adapter.setFileList(it)
        }
    }

}