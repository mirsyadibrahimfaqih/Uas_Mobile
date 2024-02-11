package com.irsyad.filmku.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.irsyad.filmku.R
import com.irsyad.filmku.databinding.ActivityRegisterBinding
import com.jakewharton.rxbinding2.widget.RxTextView

@SuppressLint("CheckResult")
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Full Name Validation
        val nameStream = RxTextView.textChanges(binding.etFullname)
            .skipInitialValue()
            .map { name ->
                name.isEmpty()
            }
        nameStream.subscribe {
            showNameExistAlert(it)
        }

        // Email Validation
        val emailStream = RxTextView.textChanges(binding.etEmail)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe {
            showEmailValidation(it)
        }

        // Username Validation
        val usernameStream = RxTextView.textChanges(binding.etUsername)
            .skipInitialValue()
            .map { username ->
                username.length < 6
            }
        usernameStream.subscribe {
            showTextMinimalAlert(it, "Username")
        }

        // Password Validation
        val passwordStream = RxTextView.textChanges(binding.etPassword)
            .skipInitialValue()
            .map { password ->
                password.length < 8 // Adjust password length as needed
            }
        passwordStream.subscribe {
            showTextMinimalAlert(it, "Password")
        }

        // Confirm Password Validation
        val passwordConfirmStream = RxTextView.textChanges(binding.etConfirmPassword)
            .skipInitialValue()
            .map { confirmPassword ->
                confirmPassword.toString() != binding.etPassword.text.toString()
            }
        passwordConfirmStream.subscribe {
            showPasswordConfirmAlert(it)
        }

        // Button enable/disable
        val invalidFieldsStream = io.reactivex.Observable.combineLatest(
            nameStream,
            emailStream,
            usernameStream,
            passwordStream,
            passwordConfirmStream,
            { nameInvalid: Boolean, emailInvalid: Boolean, usernameInvalid: Boolean, passwordInvalid: Boolean, passwordConfirmInvalid: Boolean ->
                !nameInvalid && !emailInvalid && !usernameInvalid && !passwordInvalid && !passwordConfirmInvalid
            })
        invalidFieldsStream.subscribe { isValid ->
            binding.btnRegister.isEnabled = isValid
            binding.btnRegister.backgroundTintList =
                ContextCompat.getColorStateList(this, if (isValid) R.color.primary_color else android.R.color.darker_gray)
        }

        // Button Register
        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            registerUser(email, password)
        }

        // Button to Login Activity
        binding.tvHaventAccount.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun showNameExistAlert(isNotValid: Boolean) {
        binding.etFullname.error = if (isNotValid) "Nama tidak boleh kosong" else null
    }

    private fun showTextMinimalAlert(isNotValid: Boolean, text: String) {
        if (text == "Username") {
            binding.etUsername.error = if (isNotValid) "$text harus lebih dari 6 huruf" else null
        } else if (text == "Password") {
            binding.etPassword.error = if (isNotValid) "$text harus lebih dari 8 huruf" else null
        }
    }

    private fun showEmailValidation(isNotValid: Boolean) {
        binding.etEmail.error = if (isNotValid) "Email tidak valid" else null
    }

    private fun showPasswordConfirmAlert(isNotValid: Boolean) {
        binding.etConfirmPassword.error = if (isNotValid) "Password tidak sama!" else null
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    startActivity(Intent(this, LoginActivity::class.java))
                    Toast.makeText(this, "Registrasi berhasil", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }
}
