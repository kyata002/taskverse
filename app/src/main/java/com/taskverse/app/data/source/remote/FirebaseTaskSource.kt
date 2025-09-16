package com.taskverse.app.data.source.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.taskverse.app.data.model.Task
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseTaskSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val taskCollection = firestore.collection("tasks")

    suspend fun addTask(task: Task) {
        taskCollection.document(task.id).set(task).await()
    }

    suspend fun getTasks(userId: String): List<Task> {
        val snapshot = taskCollection
            .whereEqualTo("userId", userId)
            .get()
            .await()
        return snapshot.toObjects(Task::class.java)
    }

    suspend fun updateTask(task: Task) {
        taskCollection.document(task.id).set(task).await()
    }

    suspend fun deleteTask(taskId: String) {
        taskCollection.document(taskId).delete().await()
    }
}
