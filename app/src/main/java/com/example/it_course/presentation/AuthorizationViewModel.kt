package com.example.it_course.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.it_course.data.FirebaseRepositoryImpl
import com.example.it_course.domain.usecases.AuthorizationUseCase
import com.example.it_course.domain.usecases.RegistrationUseCase
import com.google.firebase.auth.FirebaseAuth

class AuthorizationViewModel: ViewModel() {
    private val repository = FirebaseRepositoryImpl

    private val authorization = AuthorizationUseCase(repository)
    private val _errorInputMail = MutableLiveData<Boolean>()
    private val _errorInputPassword = MutableLiveData<Boolean>()
    private val _isSuccess = MutableLiveData<Boolean>()

    val errorInputMail: LiveData<Boolean>
        get() = _errorInputMail
    val errorInputPassword: LiveData<Boolean>
        get() = _errorInputPassword
    val isSuccess: LiveData<Boolean>
        get() = _isSuccess


    private fun parseMail(inputMail: String?): String {
        return inputMail?.trim() ?: ""
    }
    private fun parsePassword(inputPassword: String?): String {
        return inputPassword?.trim() ?: ""
    }
    private fun validateInput(mail: String, password: String): Boolean{
        var result = true
        val inputMail = parseMail(mail)
        val inputPassword = parsePassword(password)
        if (inputMail.isBlank()){
            _errorInputMail.value = true
            result = false
        }
        if (inputPassword.isBlank() || inputPassword.length < 8) {
            _errorInputPassword.value = true
            result = false
        }
        return result
    }
    private fun validateRegistration(auth: FirebaseAuth, mail: String, password: String) {
        if (!validateInput(mail, password)) return
        val check =  authorization.authorization(auth, mail, password)
        _isSuccess.value = check
    }
}