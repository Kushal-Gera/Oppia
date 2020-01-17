package kushal.application.recommender.Activities

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.TypedValue
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_details.*
import kushal.application.recommender.R

class DetailsAct : AppCompatActivity() {

    var IS_MALE = true

    val auth = FirebaseAuth.getInstance().currentUser

    val sharedPreferences by lazy {
        getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (sharedPreferences.getBoolean(IS_THEME_DARK, false))
            setTheme(R.style.MyDarkTheme)
        else
            setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_details)
        window.statusBarColor = getColorFromAttr(R.attr.statusBarColorL)


        val adapter1 = ArrayAdapter.createFromResource(
            this,
            R.array.spinner1,
            android.R.layout.simple_spinner_item
        )
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner1.adapter = adapter1

        val adapter2 = ArrayAdapter.createFromResource(
            this,
            R.array.spinner2,
            android.R.layout.simple_spinner_item
        )
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2.adapter = adapter2

        val adapter3 = ArrayAdapter.createFromResource(
            this,
            R.array.spinner3,
            android.R.layout.simple_spinner_item
        )
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner3.adapter = adapter3


        save.setOnClickListener(View.OnClickListener {
            if (TextUtils.isEmpty(name?.editText!!.text)) {
                name?.error = "Required"
                return@OnClickListener
            }
            if (TextUtils.isEmpty(age.editText?.text)) {
                age.error = "Required"
                return@OnClickListener
            }

            saveData()

            Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show()
            detail_container.visibility = View.GONE
            animation_view_detail.visibility = View.VISIBLE
            Handler().postDelayed({
                finish()
            }, 1300)
        })
        female.setOnClickListener {
            female.setBackgroundResource(R.drawable.save_now_bg)
            male.setBackgroundResource(R.drawable.save_now_bg_2)
            IS_MALE = false
            female.setTextColor(Color.WHITE)
            male?.setTextColor(getColorFromAttr(R.attr.hintColor))
        }
        male.setOnClickListener {
            male.setBackgroundResource(R.drawable.save_now_bg)
            female.setBackgroundResource(R.drawable.save_now_bg_2)
            IS_MALE = true
            male?.setTextColor(Color.WHITE)
            female.setTextColor(getColorFromAttr(R.attr.hintColor))
        }

    }

    private fun saveData() {
        val shared_pref = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val editor = shared_pref.edit()


        editor.putString("name", name.editText?.text.toString()).apply()
        editor.putString("age", age.editText?.text.toString()).apply()
        if (IS_MALE)
            editor.putString("gender", "male").apply()
        else
            editor.putString("gender", "female").apply()

        editor.putBoolean("is_prev", true).apply()

    }

    override fun onBackPressed() {
        if (intent.getBooleanExtra("allowed_back", false))
            super.onBackPressed()
    }

    fun getColorFromAttr(
        @AttrRes attrColor: Int,
        typedValue: TypedValue = TypedValue(),
        resolveRefs: Boolean = true
    ): Int {
        theme.resolveAttribute(attrColor, typedValue, resolveRefs)
        return typedValue.data
    }


}
