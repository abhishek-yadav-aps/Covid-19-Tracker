package com.example.covid

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_first.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class FirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        if (!isNetworkAvailable()) {
            alertForInternet()
        }
        else{
            Toast.makeText(this, "Good, you are connected to internet", Toast.LENGTH_SHORT).show()
        }

        centerImage.setOnClickListener {
            content.startRippleAnimation()
            fetchData()
        }
    }


    private fun fetchData() {
        GlobalScope.launch {
            val okHttpClient= OkHttpClient()
            val request= Request.Builder().url("https://api.covid19india.org/data.json").build()
            val response = withContext(Dispatchers.IO){okHttpClient.newCall(request).execute()}
            if (response.isSuccessful) {
                val gsonBuilder = GsonBuilder()
                gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                val gson = gsonBuilder.create()
                val data = gson.fromJson(response.body?.string(), TotalResponse::class.java)

                launch(Dispatchers.Main) {

                     val intent = Intent(this@FirstActivity, MainActivity::class.java)
                     intent.putExtra("DataAsJson", gson.toJson(data))
                     startActivity(intent)
                }
            }
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        if (activeNetwork != null) {
            // connected to the internet
            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                return true
            } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                return true
            }
            return true
        }
        return false
    }

    private fun alertForInternet() {
        lateinit var alertDialog: AlertDialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle("No Internet!!")
        builder.setMessage("Turn On Internet!!")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Retry") { dialogInterface, which ->
            if (isNetworkAvailable()) {
                alertDialog.cancel()
            }
            else
            {
                alertForInternet()
            }
        }
        builder.setNegativeButton("Quit") { dialogInterface, which ->
            finish();
            System.exit(0);
        }

        alertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

}
