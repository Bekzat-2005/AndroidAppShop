package kz.baymukach.fragmenttask


import android.widget.TextView

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val btnLogin:Button = findViewById(R.id.btnLogin);

        val txtLogin:TextView = findViewById(R.id.txtLogin);

        firebaseAuth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            loginUser()
        }

        txtLogin.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun loginUser() {
        val edtLoginEmail:EditText = findViewById(R.id.edtLoginEmail);
        val edtLoginPassword:EditText = findViewById(R.id.edtLoginPassword);

        val email = edtLoginEmail.text.toString().trim()
        val password = edtLoginPassword.text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Email or password error", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }
}