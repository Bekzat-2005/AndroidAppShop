package kz.baymukach.fragmenttask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CartViewModel : ViewModel() {
    private val _cartItems = MutableLiveData<MutableList<Product>>(mutableListOf())
    val cartItems: LiveData<MutableList<Product>> get() = _cartItems

//    Бұл себеттің ішіндегі барлық тауарларды сақтайды.
    fun addToCart(product: Product) {
        _cartItems.value?.add(product)
        _cartItems.value = _cartItems.value // Уведомляем об изменении
    }

    fun clearCart() {
        _cartItems.value?.clear()
        _cartItems.value = _cartItems.value // Уведомляем об изменении
    }
}

