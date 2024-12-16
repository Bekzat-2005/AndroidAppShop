package kz.baymukach.fragmenttask

data class Order(
    val userId: String = "",
    val address: String = "",
    val totalPrice: Int = 0,
    val items: List<Map<String, Any>> = emptyList(),
    val timestamp: Long = 0
)
