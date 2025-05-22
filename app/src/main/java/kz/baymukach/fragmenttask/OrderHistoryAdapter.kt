package kz.baymukach.fragmenttask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class OrderHistoryAdapter(
    private val orderList: MutableList<Pair<String, Order>>, // Список с ID заказа и данными
    private val onDelete: (String) -> Unit // Коллбек для удаления
) : RecyclerView.Adapter<OrderHistoryAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_history, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val (orderId, order) = orderList[position]

        holder.orderAddress.text = "Адрес: ${order.address}"
        holder.orderTotal.text = "Сумма: ${order.totalPrice} тг"
        holder.orderItems.text = "Товары: ${
            order.items.joinToString(", ") { "${it["name"]} x${it["quantity"]}" }}"

        // Обработчик кнопки "Удалить" с диалогом подтверждения
        holder.btnDeleteOrder.setOnClickListener {
            showDeleteConfirmationDialog(holder.itemView, orderId)
        }
    }

    override fun getItemCount() = orderList.size

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderAddress: TextView = itemView.findViewById(R.id.orderAddress)
        val orderTotal: TextView = itemView.findViewById(R.id.orderTotal)
        val orderItems: TextView = itemView.findViewById(R.id.orderItems)
        val btnDeleteOrder: Button = itemView.findViewById(R.id.btnDeleteOrder)
    }
    private fun showDeleteConfirmationDialog(view: View, orderId: String) {
        val context = view.context

        AlertDialog.Builder(context)
            .setTitle("Подтверждение")
            .setMessage("Вы уверены, что хотите отменить этот заказ?")
            .setPositiveButton("Да") { _, _ ->
                // Вызываем коллбэк для удаления заказа
                onDelete(orderId)
            }
            .setNegativeButton("Нет", null) // Закрыть диалог
            .show()
    }
}
