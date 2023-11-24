package com.example.movies_ch6_binar.ui.Home

import android.util.Log
import androidx.lifecycle.*
import com.example.movies_ch6_binar.Models.usecase.TopRated.GetTopRatedMovieUsecase
import com.example.movies_ch6_binar.Models.usecase.Upcoming.GetUpcomingMovieUseCase
import com.example.movies_ch6_binar.Models.usecase.getuserdata.GetUserDataUseCase
import com.example.movies_ch6_binar.Models.usecase.movielist.GetMovieListUseCase
import com.example.movies_ch6_binar.data.UserPreferences
import com.example.movies_ch6_binar.utils.Constant
import com.example.movies_ch6_binar.utils.Constant.TAG
import com.example.movies_ch6_binar.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieUseCase: GetMovieListUseCase,
    private val Upcoming : GetUpcomingMovieUseCase,
    private val TopRated : GetTopRatedMovieUsecase,
    private val getUserDataUseCase: GetUserDataUseCase,
    preferences: UserPreferences
) : ViewModel() {

    private val _state = MutableLiveData(MovieListState())
    val state: LiveData<MovieListState> = _state

    private val _userData = MutableLiveData(MovieListState())
    val userData: LiveData<MovieListState> = _userData

    val email = preferences.getEmail().asLiveData()

    init {
        getMovies()
//        getUpcoming()
//        getTopRated()
    }
//adding fun to get upcoming and top rated movies

    private fun getTopRated() {
        TopRated().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.postValue(MovieListState(movies = result.data ?: emptyList()))
                    Log.d(TAG, "ViewModel -> ${result.data}")
                }
                is Resource.Error -> {
                    _state.postValue(
                        MovieListState(
                            error = result.message ?: "An unexpected error occured"
                        )
                    )
                }
                is Resource.Loading -> {
                    _state.postValue(MovieListState(isLoading = true))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getUpcoming() {
        Upcoming().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.postValue(MovieListState(movies = result.data ?: emptyList()))
                    Log.d(TAG, "ViewModel -> ${result.data}")
                }
                is Resource.Error -> {
                    _state.postValue(
                        MovieListState(
                            error = result.message ?: "An unexpected error occured"
                        )
                    )
                }
                is Resource.Loading -> {
                    _state.postValue(MovieListState(isLoading = true))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getMovies() {
        movieUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.postValue(MovieListState(movies = result.data ?: emptyList()))
                    Log.d(TAG, "ViewModel -> ${result.data}")
                }
                is Resource.Error -> {
                    _state.postValue(
                        MovieListState(
                            error = result.message ?: "An unexpected error occured"
                        )
                    )
                }
                is Resource.Loading -> {
                    _state.postValue(MovieListState(isLoading = true))
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getUser(email: String) {
        getUserDataUseCase(email).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _userData.postValue(MovieListState(user = result.data))
                    Log.d(Constant.TAG, "Login ViewModel -> ${result.data}")
                }
                is Resource.Error -> {
                    _userData.postValue(
                        MovieListState(
                            error = result.message ?: "An unexpected error occured"
                        )
                    )
                    Log.d(Constant.TAG, "Error ViewModel -> ${result.message}")
                }
                is Resource.Loading -> {
                    _userData.postValue(MovieListState(isLoading = true))
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }
}