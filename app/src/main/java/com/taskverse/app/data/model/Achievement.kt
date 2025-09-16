package com.taskverse.app.data.model

data class Achievement(
    val id: String = "",                      // Firestore doc id
    val title: String = "",                   // Tên thành tựu
    val description: String = "",             // Điều kiện mở khóa
    val condition: String = "",               // Ví dụ: "Hoàn thành 10 task"
    val isUnlocked: Boolean = false,
    val rewardExp: Int = 50
)
