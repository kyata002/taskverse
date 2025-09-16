package com.taskverse.app.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Task(
    @DocumentId val id: String = "",          // ID tự sinh từ Firestore
    val title: String = "",
    val description: String = "",
    val deadline: Timestamp? = null,          // Dùng Firebase Timestamp
    val priority: Int = 0,                    // 0 = thấp, 1 = trung bình, 2 = cao
    val isDone: Boolean = false,
    val expReward: Int = 10,                  // Điểm EXP thưởng khi hoàn thành
    val createdAt: Timestamp = Timestamp.now()
)