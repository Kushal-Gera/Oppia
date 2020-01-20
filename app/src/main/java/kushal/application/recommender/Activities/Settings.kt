package kushal.application.recommender.Activities

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.TypedValue
import android.webkit.MimeTypeMap
import android.widget.Switch
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.dialog.*
import kushal.application.recommender.R


val CAM_START = "is_on"
val NOTI_RECEIVE = "noti"
val IS_THEME_DARK = "theme"
val CLEAR_ALLOWED = "clear"

@Suppress("DEPRECATION")
class Settings : AppCompatActivity() {


    val WEB_APP_LINK = "http://play.google.com/store/apps/details?id=" + "kushal.application.gym"
    val GMAIL_LINK = "kushalgera1212@gmail.com"

    val diksha = "https://www.linkedin.com/in/diksha-gupta-25d"
    val bhola = "https://www.linkedin.com/in/bhola-yadav-346669190"
    val shri = "https://www.linkedin.com/in/ayush-srivastava-a1015516b"
    val kushal = "https://github.com/Kushal-Gera"
    val kaustav = "https://www.linkedin.com/in/kaustuv-sahu-070144170"
    val garg = "https://www.linkedin.com/in/ayush-garg-b105a1174"

    val sharedPreferences by lazy {
        applicationContext.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (sharedPreferences.getBoolean(IS_THEME_DARK, false))
            setTheme(R.style.MyDarkTheme)
        else
            setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_settings)
        window.statusBarColor = getColorFromAttr(R.attr.backgroundColorDark)


        setting_name.text = sharedPreferences.getString(USER_NAME, "User")
        setting_age.text = sharedPreferences.getString(USER_AGE, "22") + " yrs"

        var TURN_NOTI_ON = sharedPreferences.getBoolean(NOTI_RECEIVE, true)
        setting_noti_switch.isChecked = TURN_NOTI_ON

        var DARK_THEME_ON = sharedPreferences.getBoolean(IS_THEME_DARK, false)
        theme_switch.isChecked = DARK_THEME_ON

        var clearData = sharedPreferences.getBoolean(CLEAR_ALLOWED, false)
        clear.isChecked = clearData


        val dp = sharedPreferences.getString("dp", "none")
        if (!dp.equals("none"))
            Glide.with(this).load(dp).into(setting_photo)


        back.setOnClickListener {
            onBackPressed()
        }
        setting_dev.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            val intent = Intent(Intent.ACTION_VIEW)

            dialog.kushal.setOnClickListener {
                intent.data = Uri.parse(kushal)
                startActivity(intent)
            }
            dialog.kaustav.setOnClickListener {
                intent.data = Uri.parse(kaustav)
                startActivity(intent)
            }
            dialog.shrivastav.setOnClickListener {
                intent.data = Uri.parse(shri)
                startActivity(intent)
            }
            dialog.diksha.setOnClickListener {
                intent.data = Uri.parse(diksha)
                startActivity(intent)
            }
            dialog.garg.setOnClickListener {
                intent.data = Uri.parse(garg)
                startActivity(intent)
            }
            dialog.bhola.setOnClickListener {
                intent.data = Uri.parse(bhola)
                startActivity(intent)
            }


        }
        setting_share.setOnClickListener { shareIT() }
        setting_rate.setOnClickListener { rateUs() }
        setting_suggestions.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse("mailto:$GMAIL_LINK")
            i.putExtra(
                Intent.EXTRA_SUBJECT,
                "Suggestion for the App '${resources.getString(R.string.app_name)}'"
            )
            i.putExtra(Intent.EXTRA_TEXT, "I have a suggestion that: ")
            startActivity(i)
        }
        setting_edit.setOnClickListener {
            val i = Intent(this, DetailsAct::class.java)
            i.putExtra("allowed_back", true)
            startActivity(i)
        }
        setting_noti_switch.setOnClickListener {
            val view = it as Switch
            TURN_NOTI_ON = !TURN_NOTI_ON
            view.isChecked = TURN_NOTI_ON
            sharedPreferences.edit().putBoolean(NOTI_RECEIVE, TURN_NOTI_ON).apply()
        }
        setting_photo.setOnClickListener {
            val i = Intent()
            i.type = "image/*"
            i.action = Intent.ACTION_GET_CONTENT

            startActivityForResult(Intent.createChooser(i, "Choose from :"), 1)
        }
        theme_switch.setOnClickListener {
            val view = it as Switch
            DARK_THEME_ON = !DARK_THEME_ON
            view.isChecked = DARK_THEME_ON
            sharedPreferences.edit().putBoolean(IS_THEME_DARK, DARK_THEME_ON).apply()

            val intent = Intent(this, LoginAct::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
        clear.setOnClickListener {
            val view = it as Switch
            clearData = !clearData
            view.isChecked = clearData
            sharedPreferences.edit().putBoolean(CLEAR_ALLOWED, clearData).apply()
        }


    }

    private fun shareIT() {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        i.putExtra(Intent.EXTRA_TEXT, "${resources.getString(R.string.app_name)}\n\n$WEB_APP_LINK")
        startActivity(Intent.createChooser(i, "Share Via"))
    }

    private fun rateUs() { //        open playStore if present otherwise go to chrome
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$packageName")
                )
            )
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(WEB_APP_LINK)))
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
