package com.example.covid

import android.app.AlertDialog
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_middle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class MiddleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_middle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isNetworkAvailable()) {
          alertForInternet()
        }
        else{
            Toast.makeText(context, "Good, you are connected to internet", Toast.LENGTH_SHORT).show()
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
                    //Find a new Way for this to happen.
                    //Use Bundle and pass by Fragment Manager
                   /* val intent = Intent(activity, MiddleFragment::class.java)
                    intent.putExtra("DataAsJson", gson.toJson(data))
                    startActivity(intent)  */
                }
            }
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val cm = activity?.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
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
        val builder = AlertDialog.Builder(activity)
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
            activity?.finish();
            System.exit(0);
        }

        alertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

}
