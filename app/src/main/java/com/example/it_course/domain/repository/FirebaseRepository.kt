package com.example.it_course.domain.repository

import com.google.firebase.auth.FirebaseAuth

interface FirebaseRepository {
    fun authorization(auth: FirebaseAuth, mail: String, password: String): Boolean
    fun registration(auth: FirebaseAuth, mail: String, password: String): Boolean
}