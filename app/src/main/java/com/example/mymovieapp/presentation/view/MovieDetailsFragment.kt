package com.example.mymovieapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.mymovieapp.Constants.IMAGE_PATH
import com.example.mymovieapp.R
import com.example.mymovieapp.databinding.FragmentMovieDetailsBinding
import com.example.mymovieapp.presentation.viewModel.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private val viewModel: MovieDetailsViewModel by viewModels()
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMovieGenreStateFlow.flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
            .onEach {
                binding.genres.text = viewModel.getMovieGenreString(args.movie)
            }.launchIn(lifecycleScope)
        binding.movieTitle.text = args.movie.title.toString()
        binding.movieRate.text = args.movie.vote_average.toString()
        binding.movieVoteCount.text = args.movie.vote_count.toString()
        binding.overview.text = args.movie.overview
        Glide.with(binding.moviePoster.rootView).load(IMAGE_PATH + args.movie.poster_path)
            .placeholder(R.drawable.loading1).into(binding.moviePoster)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}