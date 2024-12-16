package kz.baymukach.fragmenttask

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class OrderHistoryFragment : Fragment(R.layout.activity_order_history_fragment) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OrderHistoryAdapter
    private val orderList = mutableListOf<Pair<String, Order>>() // ID и данные заказа

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = OrderHistoryAdapter(orderList) { orderId ->
            deleteOrderFromFirebase(orderId)
        }

        recyclerView = view.findViewById(R.id.recyclerViewOrderHistory)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val currentUser = FirebaseAuth.getInstance().currentUser
        val db = FirebaseFirestore.getInstance()

        if (currentUser != null) {
            db.collection("orders")
                .whereEqualTo("userId", currentUser.uid)
                .get()
                .addOnSuccessListener { documents ->
                    orderList.clear()
                    for (document in documents) {
                        val order = document.toObject(Order::class.java)
                        orderList.add(Pair(document.id, order)) // Сохраняем ID и данные заказа
                    }
                    adapter = OrderHistoryAdapter(orderList) { orderId ->
                        deleteOrderFromFirebase(orderId)
                    }
                    recyclerView.adapter = adapter
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Ошибка загрузки заказов", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(context, "Пользователь не авторизован", Toast.LENGTH_SHORT).show()
        }
    }

    // Удаление заказа из Firestore
    private fun deleteOrderFromFirebase(orderId: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("orders").document(orderId).delete()
            .addOnSuccessListener {
                Toast.makeText(context, "Заказ удален", Toast.LENGTH_SHORT).show()
                refreshOrderList()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Ошибка удаления заказа", Toast.LENGTH_SHORT).show()
            }
    }

    // Обновляем список заказов после удаления
    private fun refreshOrderList() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val db = FirebaseFirestore.getInstance()

        currentUser?.let {
            db.collection("orders")
                .whereEqualTo("userId", it.uid)
                .get()
                .addOnSuccessListener { documents ->
                    orderList.clear()
                    for (document in documents) {
                        val order = document.toObject(Order::class.java)
                        orderList.add(Pair(document.id, order))
                    }
                    adapter.notifyDataSetChanged()
                }
        }
    }
}
