package kz.baymukach.fragmenttask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CartFragment : Fragment(R.layout.fragment_cart) {
    private lateinit var cartViewModel: CartViewModel
    private lateinit var adapter: CartAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartViewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewCart)
        val emptyCartText = view.findViewById<TextView>(R.id.emptyCartText)
        val totalPriceTextView = view.findViewById<TextView>(R.id.totalPrice)
        val buttonCheckout = view.findViewById<Button>(R.id.btnCheckout)
        val buttonClearCart = view.findViewById<Button>(R.id.btnClearCart)

        recyclerView.layoutManager = LinearLayoutManager(context)

        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            if (cartItems.isEmpty()) {
                // Если корзина пуста
                emptyCartText.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
                totalPriceTextView.visibility = View.GONE
                buttonCheckout.visibility = View.GONE
                buttonClearCart.visibility = View.GONE
            } else {
                // Если корзина не пуста
                emptyCartText.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                totalPriceTextView.visibility = View.VISIBLE
                buttonCheckout.visibility = View.VISIBLE
                buttonClearCart.visibility = View.VISIBLE

                adapter = CartAdapter(cartItems) {
                    updateTotalPrice(cartItems, totalPriceTextView)
                }
                recyclerView.adapter = adapter
                updateTotalPrice(cartItems, totalPriceTextView)
            }
        }

        // Очистка корзины
        buttonClearCart.setOnClickListener {
            cartViewModel.clearCart()
        }

        // Покупка товаров
        buttonCheckout.setOnClickListener {
            val total = cartViewModel.cartItems.value?.sumOf { it.price * it.quantity } ?: 0
            Toast.makeText(context, "Покупка на сумму $total тг", Toast.LENGTH_SHORT).show()


            showCheckoutDialog()
        }
    }

    private fun saveOrderToFirebase(address: String) {
        val db = FirebaseFirestore.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            val order = hashMapOf(
                "userId" to currentUser.uid,
                "address" to address,
                "totalPrice" to cartViewModel.cartItems.value?.sumOf { it.price * it.quantity },
                "items" to cartViewModel.cartItems.value?.map { product ->
                    mapOf(
                        "name" to product.name,
                        "price" to product.price,
                        "quantity" to product.quantity
                    )
                },
                "timestamp" to System.currentTimeMillis()
            )

            db.collection("orders").add(order)
                .addOnSuccessListener {
                    Toast.makeText(context, "Заказ успешно сохранен!", Toast.LENGTH_SHORT).show()
                    cartViewModel.clearCart()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Ошибка сохранения заказа", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(context, "Пользователь не авторизован", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateTotalPrice(cartItems: List<Product>, totalPriceTextView: TextView) {
        val totalPrice = cartItems.sumOf { it.price * it.quantity }
        totalPriceTextView.text = "Общая сумма: $totalPrice тг"
    }

    private fun showCheckoutDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_checkout, null)
        val addressEditText = dialogView.findViewById<EditText>(R.id.editTextAddress)

        AlertDialog.Builder(requireContext())
            .setTitle("Оформление заказа")
            .setView(dialogView)
            .setPositiveButton("Подтвердить") { _, _ ->
                val address = addressEditText.text.toString().trim()

                if (address.isNotEmpty()) {
                    saveOrderToFirebase(address)
                } else {
                    Toast.makeText(context, "Введите адрес доставки", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }
}

