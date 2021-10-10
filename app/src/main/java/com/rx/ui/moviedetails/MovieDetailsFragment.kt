package com.rx.ui.moviedetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.rx.R
import com.rx.base.BaseFragment
import com.rx.databinding.FragmentMovieDetailsBinding
import com.rx.glide.GlideApp
import com.rx.model.Status
import com.rx.ui.landing.LandingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment :
    BaseFragment<FragmentMovieDetailsBinding>(R.layout.fragment_movie_details) {

    //        private val viewModel by viewModels<MovieDetailsViewModel>()
    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun init() {
        binding.ibBack.setOnClickListener {
            it.findNavController().popBackStack()
        }

        viewModel.movie.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    val movieData = it.data
                    showLoading(false, binding.pbLoading)

                    GlideApp.with(binding.ivBackdrop)
                        .load("https://image.tmdb.org/t/p/original${movieData?.backdropPath}")
                        .into(binding.ivBackdrop)

                    GlideApp.with(binding.ivPoster)
                        .load("https://image.tmdb.org/t/p/w500${movieData?.posterPath}")
                        .into(binding.ivPoster)

                    binding.tvTitle.text = movieData?.title

                    if (movieData?.genres != null && movieData.genres.isNotEmpty()) {
                        val genres = movieData.genres.joinToString(
                            separator = " | ",
                            transform = { genre -> genre.name })
                        binding.tvGenres.text = genres
                    } else {
                        binding.tvGenres.visibility = View.GONE
                    }

                    if (movieData?.runtime != null) {
                        binding.tvRuntime.text =
                            getString(R.string.format_runtime, movieData.runtime)
                    } else {
                        binding.tvRuntime.visibility = View.GONE
                    }

                    if (movieData?.releaseDate != null && movieData.releaseDate.isNullOrBlank()) {
                        binding.tvReleaseDate.text = movieData.releaseDate
                    } else {
                        binding.tvReleaseDate.visibility = View.GONE
                    }

                    movieData?.voteAverage?.let { rating ->
                        binding.rbRating.rating = (rating / 2).toFloat()
                    }

                    binding.tvVoteCount.text = movieData?.voteCount.toString()
                    binding.tvOverview.text = movieData?.overview
                }
                Status.LOADING -> {
                    showLoading(false, binding.pbLoading)
                }
                Status.ERROR -> {
                    showLoading(true, binding.pbLoading)
                }
            }
        })
    }

}