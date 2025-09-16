package com.taskverse.app.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
        clearError()
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        clearError()
    }

    fun login(onSuccess: () -> Unit) {
        if (_email.value.isBlank() || _password.value.isBlank()) {
            _errorMessage.value = "Please fill in all fields"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = ""

            try {
                // Firebase login
                auth.signInWithEmailAndPassword(_email.value, _password.value).await()
                onSuccess()
            } catch (e: Exception) {
                _errorMessage.value = when (e) {
                    is FirebaseAuthInvalidUserException -> "User not found"
                    is FirebaseAuthInvalidCredentialsException -> "Invalid password"
                    is FirebaseAuthException -> "Authentication error: ${e.message}"
                    else -> "Login failed: ${e.message}"
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = ""
    }
    fun resetLoginState() {
        _isLoading.value = false
        _errorMessage.value = ""
    }

}
