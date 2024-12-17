package kz.baymukach.fragmenttask


import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class ProductDetailsFragment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_product_details)

        // Получаем данные из Intent
        val productName = intent.getStringExtra("name")
        val productPrice = intent.getIntExtra("price", 0)
        val productImageUrl = intent.getStringExtra("imageUrl")

        // Находим элементы интерфейса
        val productNameTextView = findViewById<TextView>(R.id.textViewProductName)
        val productPriceTextView = findViewById<TextView>(R.id.textViewProductPrice)
        val productImageView = findViewById<ImageView>(R.id.imageViewProduct)
//        val buttonAddToCart = findViewById<Button>(R.id.buttonAddToCart)

        // Заполняем данные
        productNameTextView.text = productName
        productPriceTextView.text = "$productPrice тг"
        Glide.with(this).load(productImageUrl).into(productImageView)

    }
}
