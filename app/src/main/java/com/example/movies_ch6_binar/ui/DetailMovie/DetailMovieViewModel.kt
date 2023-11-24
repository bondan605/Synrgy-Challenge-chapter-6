package com.example.movies_ch6_binar.ui.DetailMovie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies_ch6_binar.Models.model.Favorite
import com.example.movies_ch6_binar.Models.model.Movie
import com.example.movies_ch6_binar.Models.usecase.addfavorite.AddFavoriteUseCase
import com.example.movies_ch6_binar.Models.usecase.deletefavorite.DeleteFavoriteUseCase
import com.example.movies_ch6_binar.Models.usecase.detailmovie.GetDetailMovieUseCase
import com.example.movies_ch6_binar.Models.usecase.observefavorite.ObserveFavoriteUseCase
import com.example.movies_ch6_binar.utils.Constant
import com.example.movies_ch6_binar.utils.Constant.TAG
import com.example.movies_ch6_binar.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val detailUseCase: GetDetailMovieUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val observeFavoriteUseCase: ObserveFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase
) : ViewModel() {

    private var _state = MutableLiveData(DetailMovieNotification())
    val state: LiveData<DetailMovieNotification> = _state

    private val _favoriteMovie = MutableLiveData<Favorite?>()
    val favoriteMovie: LiveData<Favorite?> = _favoriteMovie

    fun getMovie(movieId: Int) {
        Log.d(TAG, "DetailviewModel -> getMovie executed")
        Log.d(TAG, "DetailviewModel -> $movieId")
        detailUseCase(movieId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = DetailMovieNotification(movie = result.data)
                    Log.d(TAG, "Detail ViewModel -> ${result.data}")
                }
                is Resource.Error -> {
                    _state.value =
                        DetailMovieNotification(
                            error = result.message ?: "An unexpected error occured"
                        )

                    Log.d(TAG, "Error ViewModel -> ${result.message}")
                }
                is Resource.Loading -> {
                    _state.value = DetailMovieNotification(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun addFavoriteMovie(movie: Movie) {
        addFavoriteUseCase(movie).launchIn(viewModelScope)
    }

    fun observeFavoriteMovie(userId: Int, movieId: Int) {
        observeFavoriteUseCase(userId, movieId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _favoriteMovie.value = result.data
                    Log.d(Constant.TAG, "Observe Favorite ViewModel -> ${result.data}")
                }
                is Resource.Error -> {
                    _state.value =
                        DetailMovieNotification(
                            error = result.message ?: "An unexpected error occured"
                        )

                    Log.d(Constant.TAG, "Error ViewModel -> ${result.message}")
                }
                is Resource.Loading -> {
                    _state.value = DetailMovieNotification(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun deleteFavoriteMovie(userId: Int, movieId: Int?) {
        deleteFavoriteUseCase(userId, movieId).launchIn(viewModelScope)
    }
}