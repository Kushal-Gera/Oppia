package kushal.application.recommender.Activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.TypedValue
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_settings.*
import kushal.application.recommender.R

class Settings : AppCompatActivity() {


    val sharedPreferences by lazy {
        applicationContext.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        window.statusBarColor = getColorFromAttr(R.attr.backgroundColorDark)


        setting_name.text = sharedPreferences.getString(USER_NAME, "User")
        setting_age.text = sharedPreferences.getString(USER_AGE, "20") + " yrs"

        back.setOnClickListener {
            onBackPressed()
        }

    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        setting_name.text = sharedPreferences.getString(USER_NAME, "User")
        setting_age.text = sharedPreferences.getString(USER_AGE, "22") + " yrs"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == 1 && (data != null)) {
            Toast.makeText(baseContext, "Uploading...", Toast.LENGTH_SHORT).show()
            try {
                val uri = data.data
                Glide.with(baseContext).load(uri).into(setting_photo)

                val storageRef = FirebaseStorage.getInstance().reference
                storageRef.child("pics")
                    .child("${System.currentTimeMillis()}.${getExtention(uri!!)}")
                    .putFile(uri)
                    .addOnSuccessListener {
                        val url = it.storage.downloadUrl

                        url.addOnSuccessListener {
                            sharedPreferences.edit().putString("dp", url.result.toString()).apply()
                            Toast.makeText(baseContext, "Done", Toast.LENGTH_SHORT).show()
                        }

                    }
                    .addOnFailureListener {
                        Toast.makeText(baseContext, "Error", Toast.LENGTH_SHORT).show()
                    }
            } catch (e: Exception) {

                e.printStackTrace()
                Toast.makeText(baseContext, "Try Again", Toast.LENGTH_SHORT).show()
            }
        }


    }

    fun getExtention(uri: Uri): String {

        val resolver = contentResolver
        val mimeType = MimeTypeMap.getSingleton()

        return mimeType.getMimeTypeFromExtension(resolver.getType(uri)).toString()

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
