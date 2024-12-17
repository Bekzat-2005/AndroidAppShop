package kz.baymukach.fragmenttask


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class ProductsFragment : Fragment(R.layout.fragment_products) {
    private lateinit var cartViewModel: CartViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация ViewModel
        cartViewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]

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
                recyclerView.adapter = ProductAdapter(productList, { product ->
                    // Добавление товара в корзину через ViewModel
                    cartViewModel.addToCart(product)
                    Toast.makeText(context, "${product.name} добавлен в корзину", Toast.LENGTH_SHORT).show()
                }, { product ->
                    // Открытие ProductDetailsActivity
                    val intent = Intent(requireContext(), ProductDetailsFragment::class.java).apply {
                        putExtra("name", product.name)
                        putExtra("price", product.price)
                        putExtra("imageUrl", product.imageUrl)
                    }
                    startActivity(intent)
                })
            }
            .addOnFailureListener {
                Toast.makeText(context, "Ошибка загрузки продуктов", Toast.LENGTH_SHORT).show()
            }
    }
}




