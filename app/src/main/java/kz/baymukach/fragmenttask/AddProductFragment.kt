package kz.baymukach.fragmenttask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup

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

class AddProductFragment : Fragment(R.layout.fragment_add_product) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productName = view.findViewById<EditText>(R.id.editTextProductName)
        val productPrice = view.findViewById<EditText>(R.id.editTextProductPrice)
        val productURL = view.findViewById<EditText>(R.id.editTextProductURL)
        val buttonAdd = view.findViewById<Button>(R.id.buttonAddProduct)

        val db = FirebaseFirestore.getInstance()

        buttonAdd.setOnClickListener {
            val name = productName.text.toString().trim()
            val price = productPrice.text.toString().toIntOrNull()
            val url = productURL.text.toString().trim()

            if (name.isEmpty() || price == null || url.isEmpty()) {
                Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val product = Product(name, url, price)
            db.collection("products").add(product)
                .addOnSuccessListener {
                    Toast.makeText(context, "Товар добавлен", Toast.LENGTH_SHORT).show()
                    productName.text.clear()
                    productPrice.text.clear()
                    productURL.text.clear()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Ошибка добавления товара", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
