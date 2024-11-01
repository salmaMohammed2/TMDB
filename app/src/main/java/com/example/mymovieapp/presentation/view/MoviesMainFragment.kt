package com.example.mymovieapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymovieapp.R
import com.example.mymovieapp.databinding.FragmentMoviesMainBinding
import com.example.mymovieapp.domain.entities.Movie
import com.example.mymovieapp.isNetworkStable
import com.example.mymovieapp.presentation.adapter.MoviesAdapter
import com.example.mymovieapp.presentation.viewModel.MoviesViewModel
import com.example.mymovieapp.presentation.viewState.CommonViewState
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MoviesMainFragment : Fragment(R.layout.fragment_movies_main) {

    private val viewModel: MoviesViewModel by viewModels()
    private var _binding: FragmentMoviesMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var moviesAdapter: MoviesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkNetworkAndShowBanner()
        binding.searchBar.setIconifiedByDefault(false)
        binding.searchBar.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.searchBar.setIconified(false)
            }
        }
        binding.searchBar.queryHint = getString(R.string.search)
        setupAdapter(viewModel.getNowPlayingMoviesStateFlow)

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        viewModel.tabPosition.value = 0
                        viewModel.getNowPlayingMovies()
                        setupAdapter(viewModel.getNowPlayingMoviesStateFlow)
                    }

                    1 -> {
                        viewModel.tabPosition.value = 1
                        viewModel.getTopRatedMovies()
                        setupAdapter(viewModel.getTopRatedMoviesStateFlow)
                    }

                    2 -> {
                        viewModel.tabPosition.value = 2
                        viewModel.getPopularMovies()
                        setupAdapter(viewModel.getPopularMoviesStateFlow)
                    }

                    3 -> {
                        viewModel.tabPosition.value = 3
                        setupAdapter(viewModel.getAllMoviesFromDatabaseStateFlow)
                    }
                }
                viewModel.getAllMoviesFromDatabase()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.tabLayout.visibility = View.GONE
                if (query != null) {
                    setupAdapter(viewModel.searchOnAMovieStateFlow)
                }
                viewModel.searchMovie(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    binding.tabLayout.visibility = View.VISIBLE
                    setupAdapter(viewModel.checkTabLayoutPosition(viewModel.tabPosition.value))
                } else {
                    binding.tabLayout.visibility = View.GONE
                    moviesAdapter = MoviesAdapter(
                        requireContext(),
                        emptyList(),
                        onItemClickListener = {},
                        onFavoriteClickListener = {},
                        tabLayoutPosition = viewModel.tabPosition.value
                    )
                    binding.moviesRv.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = moviesAdapter
                    }
                    binding.emptyBanner.visibility = View.GONE
                }
                return true
            }
        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchData()
            setupAdapter(viewModel.checkTabLayoutPosition(viewModel.tabPosition.value))
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setupAdapter(flow: StateFlow<CommonViewState<List<Movie>>>) {
        flow.flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED).onEach { state ->
            val movies = state.data ?: emptyList()
            when {
                state.isLoading -> {
                    binding.loadingIndicator.visibility = View.VISIBLE
                    binding.moviesRv.visibility = View.GONE
                    binding.emptyBanner.visibility = View.GONE
                }

                movies.isEmpty() -> {
                    binding.loadingIndicator.visibility = View.GONE
                    binding.moviesRv.visibility = View.GONE
                    binding.emptyBanner.visibility = View.VISIBLE
                }

                else -> {
                    binding.loadingIndicator.visibility = View.GONE
                    binding.moviesRv.visibility = View.VISIBLE
                    binding.emptyBanner.visibility = View.GONE
                }
            }

            moviesAdapter = MoviesAdapter(
                requireContext(),
                viewModel.updateMovieListWithFavorites(
                    movies, viewModel.getAllMoviesFromDatabaseStateFlow.value.data ?: emptyList()
                ),
                onItemClickListener = { movie ->
                    findNavController().navigate(
                        MoviesMainFragmentDirections.actionMoviesMainFragmentToMovieDetailsFragment(
                            movie
                        )
                    )
                },
                onFavoriteClickListener = { movie ->
                    if (viewModel.tabPosition.value != 3) {
                        if (movie.isFavorite) {
                            viewModel.addAMovieToMyFavorite(movie)
                        } else {
                            viewModel.deleteAMovieFromMyFavorite(movie.id)
                        }
                    } else {
                        viewModel.deleteAMovieFromMyFavorite(movie.id)
                        viewModel.getAllMoviesFromDatabase()
                    }
                },
                tabLayoutPosition = viewModel.tabPosition.value
            )
            binding.moviesRv.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = moviesAdapter
            }
        }.launchIn(lifecycleScope)
    }

    private fun checkNetworkAndShowBanner() {
        if (!requireContext().isNetworkStable()) {
            binding.banner.message.text = getString(R.string.no_internet)
            binding.loadingIndicator.visibility = View.GONE
            binding.moviesRv.visibility = View.GONE
        } else {
            binding.emptyBanner.visibility = View.GONE
            binding.moviesRv.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}