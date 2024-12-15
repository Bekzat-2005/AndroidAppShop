package kz.baymukach.fragmenttask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartFragment : Fragment(R.layout.fragment_cart) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewCart)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // TODO: Load cart items from local database or API

        view.findViewById<Button>(R.id.btnCheckout).setOnClickListener {
            Toast.makeText(context, "Оформление заказа", Toast.LENGTH_SHORT).show()
        }
    }
}