package com.example.davidretakeexam.presenter.screen.homescreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.davidretakeexam.R
import com.example.davidretakeexam.databinding.FragmentPersonsListBinding
import com.example.davidretakeexam.presenter.screen.detail.DetailFragment

class HomeScreen : Fragment() {

    private var _binding: FragmentPersonsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeScreenViewModel
    private lateinit var adapter: PersonListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        viewModel = ViewModelProvider(requireActivity())[HomeScreenViewModel::class.java]

        // Setup RecyclerView
        setupRecyclerView()

        // Observe ViewModel LiveData
        observeViewModel()

        // Setup SwipeRefreshLayout
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.setEvent(HomeScreenContract.HomeEvent.OnSwipeRefresh)
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = PersonListAdapter { personId -> showPersonDetail(personId) }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                handleScrollForPagination()
            }
        })
    }

    private fun observeViewModel() {
        viewModel.resultLiveData.observe(viewLifecycleOwner) { data ->
            adapter.submitList(data)
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.state.isLoading = false
            viewModel.state.isRefreshing = false
        }
    }

    private fun handleScrollForPagination() {
        val layoutManager = binding.recyclerView.layoutManager as LinearLayoutManager
        val totalItemCount = layoutManager.itemCount
        val lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()

        if (!viewModel.state.isLoading && totalItemCount <= (lastVisibleItem + 1)) {
            viewModel.setEvent(HomeScreenContract.HomeEvent.OnLoadMore)
            viewModel.state.isLoading = true
            Log.d("onScrolled: ", viewModel.state.isLoading.toString())
        }
    }

    private fun showPersonDetail(id: String) {
        Log.d("Clicked ID:", id)
        val fragment = DetailFragment.newInstance(id)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
