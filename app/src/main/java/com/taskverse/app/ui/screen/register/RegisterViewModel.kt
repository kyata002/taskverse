package com.taskverse.app.ui.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.UserProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword.asStateFlow()

    private val _fullName = MutableStateFlow("")
    val fullName = _fullName.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    private val _registrationSuccess = MutableStateFlow(false)
    val registrationSuccess = _registrationSuccess.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
        clearError()
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        clearError()
    }

    fun onConfirmPasswordChange(newPassword: String) {
        _confirmPassword.value = newPassword
        clearError()
    }

    fun onFullNameChange(newFullName: String) {
        _fullName.value = newFullName
        clearError()
    }
    fun resetRegistrationState() {
        _registrationSuccess.value = false
    }

    fun register(onSuccess: () -> Unit) {
        if (_email.value.isBlank() || _password.value.isBlank() ||
            _confirmPassword.value.isBlank() || _fullName.value.isBlank()) {
            _errorMessage.value = "Please fill in all fields"
            return
        }

        if (_password.value != _confirmPassword.value) {
            _errorMessage.value = "Passwords do not match"
            return
        }

        if (_password.value.length < 6) {
            _errorMessage.value = "Password must be at least 6 characters"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = ""

            try {
                // Tạo user với email/password
                val result = auth.createUserWithEmailAndPassword(_email.value, _password.value).await()

                // Cập nhật full name cho user
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(_fullName.value)
                    .build()
                result.user?.updateProfile(profileUpdates)?.await()

                // Thành công
                _registrationSuccess.value = true
                onSuccess()
            } catch (e: Exception) {
                // Firebase trả lỗi
                _errorMessage.value = when (e) {
                    is FirebaseAuthUserCollisionException -> "Email already registered"
                    else -> "Registration failed: ${e.message}"
                }
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun clearError() {
        _errorMessage.value = ""
    }
}