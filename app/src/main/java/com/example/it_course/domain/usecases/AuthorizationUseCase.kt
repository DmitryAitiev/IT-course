package com.example.it_course.domain.usecases

import com.example.it_course.domain.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth

class AuthorizationUseCase(
    private val repository: FirebaseRepository
) {
    fun authorization(auth: FirebaseAuth, mail: String, password: String): Boolean {
        return repository.authorization(auth, mail, password)
    }
}