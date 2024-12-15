package kz.baymukach.fragmenttask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class ProductsFragment : Fragment(R.layout.fragment_products) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewProducts)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val db = FirebaseFirestore.getInstance()
        db.collection("products").get()
            .addOnSuccessListener { documents ->
                val productList = ArrayList<Product>()
                for (document in documents) {
                    val product = document.toObject(Product::class.java)
                    productList.add(product)
                }
                recyclerView.adapter = ProductAdapter(productList)
            }
            .addOnFailureListener {
                Toast.makeText(context, "Ошибка загрузки продуктов", Toast.LENGTH_SHORT).show()
            }
    }
}