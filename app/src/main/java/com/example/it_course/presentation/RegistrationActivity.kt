package com.example.it_course.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.it_course.R
import com.example.it_course.databinding.ActivityRegistrationBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegistrationActivity : AppCompatActivity() {
    private lateinit var viewModel: RegistrationViewModel
    private val TAG = "RegistrationTEST"
    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = Firebase.auth
        viewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]
    }

    private fun launchRegistration() {
        with(binding) {
            btnRegistration.setOnClickListener {
                viewModel.validateRegistration(
                    auth,
                    etEmailInput.text?.toString(),
                    etPasswordInput.text?.toString(),
                    etPasswordRepeatInput.text?.toString()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.errorInputMail.observe(this) {
            val message = if (it) {
                "Почта не найдена"
            } else {
                null
            }
            binding.tilEmailInput.error = message
        }
        viewModel.errorInputPassword.observe(this) {
            val message = if (it) {
                "Неправильный пароль"
            }
            else {
                null
            }
            binding.tilPasswordInput.error = message
        }
        viewModel.errorInputRepeatPassword.observe(this) {
            val message = if (it) {
                "Пароль не соответствует"
            }
            else {
                null
            }
            binding.tilPasswordRepeatInput.error = message
        }
    }
    private fun textChangeListener() {
        binding.etEmailInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputMail()
            }
        })
        binding.etPasswordInput.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputPassword()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        binding.etPasswordRepeatInput.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputPasswordRepeat()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    companion object {
        fun newIntent(context: Context): Intent{
            val intent = Intent(context, RegistrationActivity::class.java)
            return intent
        }
    }
}