package kz.baymukach.fragmenttask

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class ProductDetailsFragment : Fragment(R.layout.fragment_product_details) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productName = view.findViewById<TextView>(R.id.textViewProductName)
        val productPrice = view.findViewById<TextView>(R.id.textViewProductPrice)
        val productImage = view.findViewById<ImageView>(R.id.imageViewProduct)

        val name = arguments?.getString("name")
        val price = arguments?.getInt("price")
        val imageUrl = arguments?.getString("imageUrl")

        productName.text = name
        productPrice.text = "${price ?: 0} тг"
        Glide.with(requireContext()).load(imageUrl).into(productImage)
    }

    companion object {
        fun newInstance(product: Product): ProductDetailsFragment {
            val fragment = ProductDetailsFragment()
            val bundle = Bundle()
            bundle.putString("name", product.name)
            bundle.putInt("price", product.price)
            bundle.putString("imageUrl", product.imageUrl)
            fragment.arguments = bundle
            return fragment
        }
    }
}
