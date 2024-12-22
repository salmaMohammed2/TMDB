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
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapp.R
import com.example.mymovieapp.databinding.FragmentMoviesMainBinding
import com.example.mymovieapp.domain.entities.Movie
import com.example.mymovieapp.domain.enum.MovieType
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
        binding.searchBar.setIconifiedByDefault(false)
        binding.searchBar.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.searchBar.setIconified(false)
            }
        }
        binding.searchBar.queryHint = getString(R.string.search)
        initAdapter()
        setupAdapter(viewModel.getNowPlayingMoviesStateFlow)
        setupPaginationListener()
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewModel.resetCurrentPage()
                getMoviesAccordingToMovieType(tab.position, resetAdapter = true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.tabLayout.visibility = View.GONE
                binding.moviesRv.visibility = View.VISIBLE
                viewModel.resetCurrentPage()
                viewModel.searchMovieName.value = query ?: ""
                getMoviesAccordingToMovieType(MovieType.SEARCHED.type, true, query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    binding.tabLayout.visibility = View.VISIBLE
                    binding.moviesRv.visibility = View.VISIBLE
                    setupAdapter(
                        viewModel.checkTabLayoutPosition(viewModel.tabPosition.value),
                        resetAdapter = true
                    )
                } else {
                    binding.tabLayout.visibility = View.GONE
                    binding.moviesRv.visibility = View.GONE
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
            viewModel.resetCurrentPage()
            viewModel.fetchData()
            setupAdapter(
                viewModel.checkTabLayoutPosition(viewModel.tabPosition.value),
                resetAdapter = true
            )
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun getMoviesAccordingToMovieType(
        type: Int,
        resetAdapter: Boolean,
        searchMovie: String = viewModel.searchMovieName.value
    ) {
        when (type) {
            MovieType.NOW_PLAYING.type -> {
                viewModel.tabPosition.value = MovieType.NOW_PLAYING.type
                viewModel.getNowPlayingMovies()
                setupAdapter(viewModel.getNowPlayingMoviesStateFlow, resetAdapter)
            }

            MovieType.TOP_RATED.type -> {
                viewModel.tabPosition.value = MovieType.TOP_RATED.type
                viewModel.getTopRatedMovies()
                setupAdapter(viewModel.getTopRatedMoviesStateFlow, resetAdapter)
            }

            MovieType.POPULAR.type -> {
                viewModel.tabPosition.value = MovieType.POPULAR.type
                viewModel.getPopularMovies()
                setupAdapter(viewModel.getPopularMoviesStateFlow, resetAdapter)
            }

            MovieType.FAVORITES.type -> {
                viewModel.tabPosition.value = MovieType.FAVORITES.type
                setupAdapter(viewModel.getAllMoviesFromDatabaseStateFlow, resetAdapter = true)
            }

            MovieType.SEARCHED.type -> {
                viewModel.tabPosition.value = MovieType.SEARCHED.type
                viewModel.searchMovie(searchMovie)
                setupAdapter(viewModel.searchOnAMovieStateFlow, resetAdapter = resetAdapter)
            }
        }
        viewModel.getAllMoviesFromDatabase()
    }

    private fun initAdapter() {
        moviesAdapter = MoviesAdapter(
            requireContext(),
            mutableListOf(),
            onItemClickListener = { movie ->
                findNavController().navigate(
                    MoviesMainFragmentDirections.actionMoviesMainFragmentToMovieDetailsFragment(
                        movie
                    )
                )
            },
            onFavoriteClickListener = { movie ->
                if (viewModel.tabPosition.value != MovieType.FAVORITES.type) {
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
    }

    private fun setupAdapter(
        flow: StateFlow<CommonViewState<List<Movie>>>,
        resetAdapter: Boolean = false
    ) {
        checkNetworkAndShowBanner()
        flow.flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED).onEach { state ->
            val movies = state.data ?: emptyList()
            when {
                state.isLoading -> {
                    if (moviesAdapter.isLoading) {
                        showRecyclerView()
                    } else {
                        binding.loadingIndicator.visibility = View.VISIBLE
                        binding.moviesRv.visibility = View.GONE
                        binding.emptyBanner.visibility = View.GONE
                    }

                }

                movies.isEmpty() -> {
                    binding.loadingIndicator.visibility = View.GONE
                    binding.moviesRv.visibility = View.GONE
                    binding.emptyBanner.visibility = View.VISIBLE
                }

                else -> {
                    showRecyclerView()
                    if (resetAdapter)
                        moviesAdapter.resetMovies(
                            viewModel.updateMovieListWithFavorites(
                                movies,
                                viewModel.getAllMoviesFromDatabaseStateFlow.value.data
                                    ?: emptyList()
                            )
                        )
                    else
                        moviesAdapter.addMovies(
                            viewModel.updateMovieListWithFavorites(
                                movies,
                                viewModel.getAllMoviesFromDatabaseStateFlow.value.data
                                    ?: emptyList()
                            )
                        )
                    moviesAdapter.showLoading(false)
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun showRecyclerView() {
        binding.loadingIndicator.visibility = View.GONE
        binding.moviesRv.visibility = View.VISIBLE
        binding.emptyBanner.visibility = View.GONE
    }

    private fun setupPaginationListener() {
        binding.moviesRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                if (!moviesAdapter.isLoading && !viewModel.stopPagination() && totalItemCount <= (lastVisibleItemPosition + 2)) {
                    moviesAdapter.showLoading(true)
                    viewModel.loadNextPage()
                    getMoviesAccordingToMovieType(
                        viewModel.tabPosition.value,
                        resetAdapter = false
                    )
                }
            }
        })
    }

    private fun checkNetworkAndShowBanner() {
        if (!requireContext().isNetworkStable()) {
            binding.banner.message.text = getString(R.string.no_internet)
            binding.loadingIndicator.visibility = View.GONE
            binding.moviesRv.visibility = View.GONE
        } else {
            binding.banner.message.text = getString(R.string.empty_text)
            binding.emptyBanner.visibility = View.GONE
            binding.moviesRv.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}