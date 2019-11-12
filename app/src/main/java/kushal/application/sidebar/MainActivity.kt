package kushal.application.sidebar

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kushal.application.sidebar.Fragments.GalleryFrag
import kushal.application.sidebar.Fragments.HomeFrag

class MainActivity : AppCompatActivity() {

    private var IS_SHORT = false
    private var ON_HOME = true

    val fManager by lazy {
        supportFragmentManager
    }

    val menuItemList by lazy {
        arrayListOf<TextView>(
            home_tv, membership_tv,
            perform_tv, exercise_tv,
            diet_tv, gallery_tv
        )
    }

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        fab.setOnClickListener {

            menu.visibility = View.VISIBLE

            layout
                .animate().translationX(350f)
                .translationY(0f)
                .scaleX(0.6f)
                .scaleY(0.6f)
                .duration = 1000

            window.statusBarColor = resources.getColor(R.color.backgroundDark)
            container.setBackgroundColor(resources.getColor(R.color.backgroundDark))

            fab.visibility = View.INVISIBLE
            IS_SHORT = true

        }
        settings.setOnClickListener {
            startActivity(Intent(this, Settings::class.java))
        }
        // color set up for menu -> home
        setAllGray()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            home_tv.compoundDrawableTintList =
                resources.getColorStateList(R.color.white)
        }
        home_tv.setTextColor(resources.getColor(R.color.white))

        setUpMenuItems()
        fManager.beginTransaction().replace(R.id.layout, HomeFrag()).commit()


    }

    private fun setUpMenuItems() {

        home_tv.setOnClickListener {
            header_text.setText("Sidebar App")
            colorToWhite(it)
            fManager.beginTransaction().replace(R.id.layout, HomeFrag()).commit()
            ON_HOME = true

            onBackPressed()
        }
        membership_tv.setOnClickListener {
            colorToWhite(it)
            header_text.setText("Membership Plans")
            // further changes specific to membership plans
            fManager.beginTransaction().replace(R.id.layout, GalleryFrag()).commit()
            ON_HOME = false

            onBackPressed()
        }
        perform_tv.setOnClickListener {
            colorToWhite(it)
            header_text.setText("My Performance")
            fManager.beginTransaction().replace(R.id.layout, GalleryFrag()).commit()
            ON_HOME = false

            onBackPressed()
        }
        exercise_tv.setOnClickListener {
            colorToWhite(it)
            header_text.setText("Exercise Routines")
            fManager.beginTransaction().replace(R.id.layout, GalleryFrag()).commit()
            ON_HOME = false

            onBackPressed()
        }
        diet_tv.setOnClickListener {
            colorToWhite(it)
            header_text.setText("Diet Guide")
            fManager.beginTransaction().replace(R.id.layout, GalleryFrag()).commit()
            ON_HOME = false

            onBackPressed()
        }
        gallery_tv.setOnClickListener {
            colorToWhite(it)
            header_text.setText("Gallery")
            fManager.beginTransaction().replace(R.id.layout, GalleryFrag()).commit()
            ON_HOME = false

            onBackPressed()
        }

    }

    private fun colorToWhite(view: View) {
        setAllGray()
        val it = view as TextView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            it.compoundDrawableTintList =
                resources.getColorStateList(R.color.white)
        }
        it.setTextColor(resources.getColor(R.color.white))

    }

    private fun setAllGray() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            menuItemList.forEach {
                it.compoundDrawableTintList =
                    resources.getColorStateList(R.color.gray)

                it.setTextColor(resources.getColor(R.color.gray))
            }
        }


    }

    @SuppressLint("RestrictedApi")
    override fun onBackPressed() {
        if (IS_SHORT) {
            layout.animate().translationX(0f)
                .translationY(0f)
                .scaleX(1f)
                .scaleY(1f)
                .duration = 600

            Handler().postDelayed({
                fab.visibility = View.VISIBLE
                menu.visibility = View.INVISIBLE
                window.statusBarColor = resources.getColor(R.color.background)
                container.setBackgroundColor(resources.getColor(R.color.background))
            }, 600)

            IS_SHORT = !IS_SHORT

        } else if (!ON_HOME) {
            fManager.beginTransaction().replace(R.id.layout, HomeFrag()).commit()
            header_text.setText("SideBar App")
            colorToWhite(home_tv)
            ON_HOME = !ON_HOME
        } else
            super.onBackPressed()

    }

}
