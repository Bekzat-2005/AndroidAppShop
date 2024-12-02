package kz.baymukach.fragmenttask


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, HomeFragment())
            .commit()


        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, HomeFragment())
                        .commit()
                    true
                }
                R.id.menu_profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, ProfileFragment())
                        .commit()
                    true
                }
                R.id.menu_cart -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, CartFragment())
                        .commit()
                    true
                }
                R.id.menu_favorites -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, FavoritesFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}