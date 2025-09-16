package com.taskverse.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.taskverse.app.data.model.Achievement
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AchievementRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val achievementCollection = firestore.collection("achievements")

    suspend fun unlockAchievement(userId: String, achievement: Achievement) {
        achievementCollection
            .document(userId)
            .collection("userAchievements")
            .document(achievement.id)
            .set(achievement)
            .await()
    }

    suspend fun getUserAchievements(userId: String): List<Achievement> {
        val snapshot = achievementCollection
            .document(userId)
            .collection("userAchievements")
            .get()
            .await()
        return snapshot.toObjects(Achievement::class.java)
    }
}
