package com.example.it_course.domain.usecases

import com.example.it_course.domain.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth

class RegistrationUseCase(
    private val repository: FirebaseRepository
) {
    fun registration(auth: FirebaseAuth, mail: String, password: String): Boolean {
        return repository.registration(auth, mail, password)
    }
}