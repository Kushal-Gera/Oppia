package kushal.application.recommender.Activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kushal.application.recommender.R

class SplashScreen : AppCompatActivity() {

    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (sharedPreferences.getBoolean(IS_THEME_DARK, false))
            setTheme(R.style.MyDarkTheme)
        else
            setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_splash_screen)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        Handler().postDelayed({

            animation_view_splash.animate()
                .scaleY(40f)
                .scaleX(30f)
                .duration = 400

        }, 1600)

        Handler().postDelayed({
            startActivity(Intent(this, LoginAct::class.java))
            finish()
        }, 1900)

    }
}
