package com.example.smartfit.data.repository

import com.example.smartfit.data.local.User
import com.example.smartfit.data.local.UserDao

class UserRepository(private val userDao: UserDao) {
    suspend fun registerUser(username: String, email: String, password: String): Boolean {
        val existing = userDao.getUserByEmail(email)
        if (existing != null) return false
        userDao.insertUser(User(username = username, email = email, password = password))
        return true
    }

    suspend fun login(email: String, password: String): User? {
        val user = userDao.getUserByEmail(email)
        return if (user?.password == password) user else null
    }
}
