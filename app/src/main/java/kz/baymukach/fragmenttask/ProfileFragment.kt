package kz.baymukach.fragmenttask
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

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userName = view.findViewById<TextView>(R.id.textViewUserName)
        val userEmail = view.findViewById<TextView>(R.id.textViewUserEmail)
        val buttonLogout = view.findViewById<Button>(R.id.buttonLogout)

        // TODO: Load user information from FirebaseAuth or database

        buttonLogout.setOnClickListener {
            Toast.makeText(context, "Выход из аккаунта", Toast.LENGTH_SHORT).show()
            // TODO: Implement logout logic
        }
    }
}
