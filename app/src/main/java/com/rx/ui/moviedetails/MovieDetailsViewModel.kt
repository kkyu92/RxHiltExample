package com.rx.ui.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.rx.base.BaseViewModel
import com.rx.model.Movie
import com.rx.model.Resource
import com.rx.repository.MovieRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    movieRepository: MovieRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    private val _movie = MutableLiveData<Resource<Movie>>()
    val movie: LiveData<Resource<Movie>>
        get() = _movie

    init {
        if (savedStateHandle.contains("movieId")) {
            val movieId = savedStateHandle.get<Long>("movieId")
            compositeDisposable.add(
                movieRepository.getMovieDetails(movieId!!)
                    .doOnSubscribe{_movie.value = Resource.Loading()}
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ movie ->
                        _movie.value = Resource.Success(movie)
                    }, { error ->
                        _movie.value = Resource.Error(error.message!!)
                    })
            )
        } else {
            _movie.value = Resource.Error("OMG, unable to get the argument!!")
            Timber.i("OMG, unable to get the argument!!")
        }
    }
}