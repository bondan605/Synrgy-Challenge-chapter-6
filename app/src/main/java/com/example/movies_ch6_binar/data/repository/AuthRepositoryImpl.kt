package com.example.movies_ch6_binar.data.repository

import com.example.movies_ch6_binar.data.local.entity.UserEntity
import com.example.movies_ch6_binar.data.local.room.dao.UserDao
import com.example.movies_ch6_binar.Models.repository.AuthRepository
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : AuthRepository {
    override suspend fun login(email: String, password: String): UserEntity {
        return userDao.login(email, password)
    }

    override suspend fun register(user: UserEntity): Long {
        return userDao.register(user)
    }

    override suspend fun getUserData(email: String): UserEntity {
        return userDao.getUserData(email)
    }

    override suspend fun updateUser(user: UserEntity): Int {
        return userDao.update(user)
    }
}