package com.example.it_course.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.it_course.data.FirebaseRepositoryImpl
import com.example.it_course.domain.usecases.RegistrationUseCase
import com.google.firebase.auth.FirebaseAuth

class RegistrationViewModel: ViewModel() {
    private val repository = FirebaseRepositoryImpl

    private val registration = RegistrationUseCase(repository)
    private val _errorInputMail = MutableLiveData<Boolean>()
    private val _errorInputPassword = MutableLiveData<Boolean>()
    private val _isSuccess = MutableLiveData<Boolean>()
    private val _errorInputRepeatPassword = MutableLiveData<Boolean>()

    val errorInputMail: LiveData<Boolean>
        get() = _errorInputMail
    val errorInputPassword: LiveData<Boolean>
        get() = _errorInputPassword
    val isSuccess: LiveData<Boolean>
        get() = _isSuccess
    val errorInputRepeatPassword: LiveData<Boolean>
        get() = _errorInputRepeatPassword


    private fun parseMail(inputMail: String?): String {
        return inputMail?.trim() ?: ""
    }
    private fun parsePassword(inputPassword: String?): String {
        return inputPassword?.trim() ?: ""
    }
    private fun validateInput(mail: String?, password: String?, repeatPassword: String?): Boolean{
        var result = true
        val inputMail = parseMail(mail)
        val inputPassword = parsePassword(password)
        val inputRepeatPassword = parsePassword(repeatPassword)
        if (inputMail.isBlank()){
            _errorInputMail.value = true
            result = false
        }
        if (inputPassword.isBlank() || inputPassword.length < 8) {
            _errorInputPassword.value = true
            result = false
        }
        if (inputPassword != inputRepeatPassword) {
            _errorInputRepeatPassword.value = true
            result = false
        }
        return result
    }
    private fun validateRegistration(auth: FirebaseAuth, mail: String, password: String, repeatPassword: String) {
        if (!validateInput(mail, password, repeatPassword)) return
        val check =  registration.registration(auth, mail, password)
        _isSuccess.value = check
    }

    fun resetErrorInputMail() {
        _errorInputMail.value = false
    }
    fun resetErrorInputPassword() {
        _errorInputPassword.value = false
    }
    fun resetErrorInputPasswordRepeat() {
        _errorInputRepeatPassword.value = false
    }
}