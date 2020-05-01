package com.example.covid

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (isNetworkAvailable() == false) {
            alertForInternet()
        }

        centerImage.setOnClickListener {
            content.startRippleAnimation()
        }
    }

    private fun alertForInternet() {
        lateinit var alertDialog: AlertDialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle("No Internet!!")
        builder.setMessage("Turn On Internet!!")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Retry") { dialogInterface, which ->
            if (isNetworkAvailable() == true) {
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
}





