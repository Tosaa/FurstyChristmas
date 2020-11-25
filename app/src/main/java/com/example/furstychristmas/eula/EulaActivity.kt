package com.example.furstychristmas.eula


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Spanned
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import com.example.furstychristmas.R
import com.example.furstychristmas.databinding.ActivityEulaBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import java.io.InputStream


class EulaActivity : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences
    private lateinit var binding: ActivityEulaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_eula)

        val injectedPreferences: SharedPreferences by inject()
        preferences = injectedPreferences

        initializeUI()
    }

    private fun initializeUI() {
        if (!preferences.getBoolean("eulaAccepted", false)) {
            binding.eulaCancel.setOnClickListener { cancelEULA() }
            binding.eulaConfirm.setOnClickListener {
                confirmEULA()
            }
        } else {
            binding.header.visibility = View.GONE
        }
        setEula()
    }

    private fun setEula() {
        val eula = intent.extras!!.getInt("eula")
        val inputStream: InputStream = resources.openRawResource(eula)
        val bytes = ByteArray(inputStream.available())
        inputStream.read(bytes)
        val htmlAsSpanned: Spanned =
            HtmlCompat.fromHtml(String(bytes), HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.eulaContent.text = htmlAsSpanned
    }

    private fun cancelEULA() {
        Snackbar.make(
            binding.eulaContent,
            "Du musst die End Nutzer Vereinbarung akzeptieren um die App nutzen zu können.",
            Snackbar.LENGTH_LONG
        ).show()
    }

    // NOTE: Here you would call your api to save the status
    private fun confirmEULA() {
        preferences.edit().putBoolean("eulaAccepted", true).apply()
        val intent = Intent()
        setResult(RESULT_OK, intent)
        finish()
    }
}