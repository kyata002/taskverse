package com.taskverse.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.taskverse.app.data.model.Task
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun addTask(userId: String, task: Task) {
        firestore.collection("users")
            .document(userId)
            .collection("tasks")
            .add(task)
            .await()  // thêm kotlinx-coroutines-play-services nếu dùng await()
    }

    // TODO: getTask, updateTask, deleteTask...
}
