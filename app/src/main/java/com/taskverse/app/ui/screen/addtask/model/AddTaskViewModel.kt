package com.taskverse.app.ui.screen.addtask.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.taskverse.app.data.model.Task
import com.taskverse.app.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val repository: TaskRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()

    private val _priority = MutableStateFlow(0)
    val priority = _priority.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    fun onTitleChange(newTitle: String) { _title.value = newTitle }
    fun onDescriptionChange(newDesc: String) { _description.value = newDesc }
    fun onPriorityChange(newPriority: Int) { _priority.value = newPriority }

    fun addTask(onSuccess: () -> Unit) {
        val userId = auth.currentUser?.uid
        if (_title.value.isBlank()) {
            _errorMessage.value = "Title cannot be empty"
            return
        }

        // For testing without Firebase, use a mock userId
        val effectiveUserId = userId ?: "mock_user_id"

        val task = Task(
            title = _title.value,
            description = _description.value,
            priority = _priority.value,
            deadline = null,
            isDone = false,
            expReward = 10,
            createdAt = Timestamp.now()
        )

        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = ""
                repository.addTask(effectiveUserId, task)
                onSuccess()
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Unknown error"
            } finally {
                _isLoading.value = false
            }
        }
    }
}