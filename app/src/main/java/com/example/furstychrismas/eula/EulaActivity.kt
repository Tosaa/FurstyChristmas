package com.example.furstychrismas.eula


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Spanned
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import com.example.furstychrismas.R
import com.example.furstychrismas.databinding.ActivityEulaBinding
import com.google.android.material.snackbar.Snackbar
import java.io.InputStream


class EulaActivity : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences
    private lateinit var binding: ActivityEulaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = applicationContext
            .getSharedPreferences("com.example.furstychrismas", Context.MODE_PRIVATE)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_eula)

        initializeUI()
    }

    private fun initializeUI() {
        binding.eulaCancel.setOnClickListener { cancelEULA() }
        binding.eulaConfirm.setOnClickListener {
            confirmEULA()
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
            "You must accept the EULA to continue",
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