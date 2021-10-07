package com.rx.ui.landing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.clonebeamin.base.BaseViewModel
import com.rx.model.Movie
import com.rx.model.Resource
import com.rx.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    movieRepository: MovieRepository
) : BaseViewModel() {
    private val _trendingMovies = MutableLiveData<Resource<List<Movie>>>()
    val trendingMovies: LiveData<Resource<List<Movie>>>
        get() = _trendingMovies

    init {
        compositeDisposable.add(
            movieRepository.getTrendingMovie()
                .doOnSubscribe{ _trendingMovies.value = Resource.Loading(null) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ movies ->
                    _trendingMovies.value = Resource.Success(movies.results)
                }, { error ->
                    Timber.e(error)
                    _trendingMovies.value = Resource.Error(error.message!!, null)
                })
        )
    }
}