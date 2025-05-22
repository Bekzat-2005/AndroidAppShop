package kz.baymukach.fragmenttask



import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnRegis: Button = findViewById(R.id.btnRegis);

        val txtRegister: TextView = findViewById(R.id.txtRegister);

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()

        btnRegis.setOnClickListener {
            registerUser()
        }
        txtRegister.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun registerUser() {

        val edtRegisName: EditText = findViewById(R.id.edtRegisName);
        val edtRegisPhoneNum: EditText = findViewById(R.id.edtRegisPhoneNum);
        val edtRegisEmail: EditText = findViewById(R.id.edtRegisEmail);
        val edtRegisPassword: EditText = findViewById(R.id.edtRegisPassword);
        val edtRegisPasswordConfirm: EditText = findViewById(R.id.edtRegisPasswordConfirm);


        val name = edtRegisName.text.toString().trim()
        val phone = edtRegisPhoneNum.text.toString().trim()
        val email = edtRegisEmail.text.toString().trim()
        val password = edtRegisPassword.text.toString().trim()
        val confirmPassword = edtRegisPasswordConfirm.text.toString().trim()

        if (name.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty() &&
            password.isNotEmpty() && password == confirmPassword
        ) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = firebaseAuth.currentUser
                        val userData = hashMapOf(
                            "display_name" to name,
                            "phone_number" to phone,
                            "email" to email,
                            "password" to password
                        )

                        firebaseFirestore.collection("users").document(user!!.uid)
                            .set(userData)
                            .addOnCompleteListener { firestoreTask ->
                                if (firestoreTask.isSuccessful) {
                                    startActivity(Intent(this, LoginActivity::class.java))
                                    finish()

                                } else {
                                    Toast.makeText(this, "Error ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                    } else {
                        Toast.makeText(this, "Error ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Please fill all fields correctly", Toast.LENGTH_SHORT).show()
        }
    }
}