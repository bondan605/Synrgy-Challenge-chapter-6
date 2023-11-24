package com.example.movies_ch6_binar.ui.SpalashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.movies_ch6_binar.data.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    preferences: UserPreferences
) : ViewModel() {
    val email = preferences.getEmail().asLiveData()
}