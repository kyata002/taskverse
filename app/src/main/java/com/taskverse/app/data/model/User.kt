package com.taskverse.app.data.model

data class User(
    val id: String = "",                      // uid từ Firebase Auth
    val name: String = "",
    val email: String = "",
    val level: Int = 1,
    val exp: Int = 0,
    val photoUrl: String? = null,
    val joinedAt: Long = System.currentTimeMillis()
)
