package com.mahia.ribot.view.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.mahia.ribot.databinding.ActivitySignInBinding
import com.mahia.ribot.view.HomeActivity

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.progressBar.visibility = View.GONE
        binding.buttonLogin.setOnClickListener {
            logInRegisteredUser()
        }

        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }
    }

    private fun logInRegisteredUser() {
        val email = binding.etEmailLogin.text.toString().trim()
        val password = binding.etPasswordLogin.text.toString().trim()

        if (email.isEmpty() or password.isEmpty()) {
            Toast.makeText(this@SignInActivity, "Tidak boleh ada data yang kosong", Toast.LENGTH_SHORT).show()
        }else{
            binding.buttonLogin.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this@SignInActivity, HomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                }
                .addOnFailureListener{
                    binding.progressBar.visibility = View.GONE
                    binding.buttonLogin.visibility = View.VISIBLE
                    Toast.makeText(this@SignInActivity, "Anda gagal melakukna login, pastikan password dan email sudah terisi dengan benar", Toast.LENGTH_SHORT).show()
                }
        }


    }
}