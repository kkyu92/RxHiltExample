package com.rx.ui.landing

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.rx.R
import com.rx.base.BaseFragment
import com.rx.databinding.FragmentLandingBinding
import com.rx.model.Status
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LandingFragment : BaseFragment<FragmentLandingBinding>(R.layout.fragment_landing) {

    private lateinit var movieAdapter: MovieAdapter
    private val landingViewModel by viewModels<LandingViewModel>()

    // onViewCreated
    override fun init() {
        movieAdapter = MovieAdapter()
        binding.rvMovie.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMovie.adapter = movieAdapter

        landingViewModel.trendingMovies.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    showLoading(false, binding.pbLoading)
                    movieAdapter.setMovies(it.data!!)
                }
                Status.ERROR -> {
                    showLoading(false, binding.pbLoading)
                    Snackbar.make(requireView(), it.message!!, Snackbar.LENGTH_SHORT).show()
                }
                Status.LOADING -> showLoading(true, binding.pbLoading)
            }
        })
    }
}