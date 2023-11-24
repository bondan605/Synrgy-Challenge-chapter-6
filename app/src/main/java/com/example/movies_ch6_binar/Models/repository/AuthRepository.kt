package com.example.movies_ch6_binar.Models.repository

import com.example.movies_ch6_binar.data.local.entity.UserEntity


interface AuthRepository {

    suspend fun login(email: String, password: String): UserEntity

    suspend fun register(user: UserEntity): Long

    suspend fun getUserData(email: String): UserEntity

    suspend fun updateUser(user: UserEntity): Int
}