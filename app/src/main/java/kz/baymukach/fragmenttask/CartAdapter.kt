package kz.baymukach.fragmenttask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CartAdapter(
    private val cartItems: MutableList<Product>,
    private val onQuantityChanged: () -> Unit // Коллбек для уведомления об изменении суммы
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = cartItems[position]
        holder.productName.text = product.name
        holder.productPrice.text = "${product.price * product.quantity} тг"
        holder.productQuantity.text = product.quantity.toString()

        // Загрузка изображения с помощью Glide
        Glide.with(holder.itemView.context)
            .load(product.imageUrl) // URL изображения
            .into(holder.productImage) // Установка в ImageView

        holder.buttonIncrease.setOnClickListener {
            product.quantity++
            notifyItemChanged(position)
            onQuantityChanged()
        }

        holder.buttonDecrease.setOnClickListener {
            if (product.quantity > 1) {
                product.quantity--
                notifyItemChanged(position)
                onQuantityChanged()
            }
        }
    }

    override fun getItemCount() = cartItems.size

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.productImage);
        val productName: TextView = itemView.findViewById(R.id.productName)
        val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        val productQuantity: TextView = itemView.findViewById(R.id.productQuantity)
        val buttonIncrease: Button = itemView.findViewById(R.id.buttonIncrease)
        val buttonDecrease: Button = itemView.findViewById(R.id.buttonDecrease)
    }
}
