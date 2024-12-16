package kz.baymukach.fragmenttask

data class Product(
    var name: String = "",
    var imageUrl: String = "", // URL изображения товара
    var price: Int = 0,
    var quantity: Int = 1 // Количество товара
)
