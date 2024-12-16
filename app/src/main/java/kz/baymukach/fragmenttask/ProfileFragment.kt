package kz.baymukach.fragmenttask
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.view.View
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val userNameTextView = view.findViewById<TextView>(R.id.textViewUserName)
        val userEmailTextView = view.findViewById<TextView>(R.id.textViewUserEmail)
        val userPhoneTextView = view.findViewById<TextView>(R.id.textViewUserPhone)
        val buttonLogout = view.findViewById<Button>(R.id.buttonLogout)

        val currentUser = firebaseAuth.currentUser

        if (currentUser != null) {
            // Отображение email из FirebaseAuth
            userEmailTextView.text = currentUser.email ?: "Нет email"

            // Загрузка имени и телефона из Firestore
            firestore.collection("users").document(currentUser.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val name = document.getString("display_name") ?: "Без имени"
                        val phone = document.getString("phone_number") ?: "Нет телефона"

                        userNameTextView.text = name
                        userPhoneTextView.text = phone
                    }
                }
                .addOnFailureListener {
                    userNameTextView.text = "Ошибка загрузки данных"
                    userPhoneTextView.text = ""
                }
        } else {
            Toast.makeText(context, "Пользователь не авторизован", Toast.LENGTH_SHORT).show()
        }

        // Логика выхода из аккаунта
        buttonLogout.setOnClickListener {
            firebaseAuth.signOut()
            Toast.makeText(context, "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show()
            startActivity(Intent(context, LoginActivity::class.java))
            activity?.finish()
        }
        val buttonOrderHistory = view.findViewById<Button>(R.id.buttonOrderHistory)
        buttonOrderHistory.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame, OrderHistoryFragment())
                .addToBackStack(null)
                .commit()
        }

    }
}

