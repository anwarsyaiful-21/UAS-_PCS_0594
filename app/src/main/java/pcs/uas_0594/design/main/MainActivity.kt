package pcs.uas_0594.design.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import android.widget.FrameLayout
import pcs.uas_0594.R
import pcs.uas_0594.design.event.EventFragment
import pcs.uas_0594.design.favorite.FavoriteFragmentt

class MainActivity : AppCompatActivity() {
    private var content: FrameLayout? = null
    lateinit var bottomNav: BottomNavigationView

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_previous -> {
                    val fragment = EventFragment.newInstance("eventspastleague")
                    addFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_next -> {
                    val fragment = EventFragment.newInstance("eventsnextleague")
                    addFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_favorite -> {
                    val fragment = FavoriteFragmentt()
                    addFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.design_bottom_sheet_slide_in,
                R.anim.design_bottom_sheet_slide_out
            )
            .replace(R.id.content, fragment, fragment.javaClass.getSimpleName())
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNav = findViewById(R.id.navigation)
        bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val fragment = EventFragment.newInstance("eventspastleague")
        addFragment(fragment)

    }
}

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        val inflater = menuInflater
//        inflater.inflate(R.menu.navigation, menu)
//        return true
//    }
//
//    // actions on click menu items
//    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
//        R.id.navigation_favorite -> {
//            true
//        }
//        else -> {
//            super.onOptionsItemSelected(item)
//        }
//    }
//}
