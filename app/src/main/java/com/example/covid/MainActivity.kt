package com.example.covid

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_nav.selectedItemId = R.id.middle_menu
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.main_frame, MiddleFragment())
        ft.commit()

        main_nav.setOnNavigationItemSelectedListener{
            when(it.itemId){
                    R.id.left_menu ->{
                        setFragment(LeftFragment())
                    }
                    R.id.middle_menu -> {
                        setFragment(MiddleFragment())
                    }
                    R.id.right_menu -> {
                        setFragment(RightFragment())
                    }
                else -> {
                    setFragment(MiddleFragment())
                }
            }
    }
}
    private fun setFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction().replace(R.id.main_frame, fragment).commit()
        return true
    }
}





