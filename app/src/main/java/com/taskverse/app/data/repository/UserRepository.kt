package com.taskverse.app.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.taskverse.app.data.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    private val userCollection = firestore.collection("users")

    fun getCurrentUser() = auth.currentUser

    suspend fun loginWithEmail(email: String, password: String) =
        auth.signInWithEmailAndPassword(email, password).await()

    suspend fun registerWithEmail(email: String, password: String) =
        auth.createUserWithEmailAndPassword(email, password).await()

    suspend fun getUserData(userId: String): User? {
        val snapshot = userCollection.document(userId).get().await()
        return snapshot.toObject(User::class.java)
    }

    suspend fun updateExpAndLevel(userId: String, exp: Int, level: Int) {
        userCollection.document(userId).update(
            mapOf("exp" to exp, "level" to level)
        ).await()
    }
}
