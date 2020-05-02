package com.example.covid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val dataString=intent.getStringExtra("DataAsJson")

        main_nav.selectedItemId = R.id.middle_menu
        val ft = supportFragmentManager.beginTransaction()
        val middleFragment = MiddleFragment()
        val bundle = Bundle().apply {
            putString("DataAsJson", dataString)
        }
        middleFragment.arguments = bundle
        ft.replace(R.id.main_frame, middleFragment)
        ft.commit()


        main_nav.setOnNavigationItemSelectedListener{
            when(it.itemId){
                    R.id.left_menu ->{
                        val leftFragment = LeftFragment()
                        val bundle = Bundle().apply {
                            putString("DataAsJson", dataString)
                        }
                        leftFragment.arguments = bundle
                        setFragment(leftFragment)

                    }
                    R.id.middle_menu -> {
                        val middleFragment = MiddleFragment()
                        val bundle = Bundle().apply {
                            putString("DataAsJson", dataString)
                        }
                        middleFragment.arguments = bundle
                        setFragment(middleFragment)
                    }
                    R.id.right_menu -> {
                        setFragment(RightFragment())
                    }
                else -> {
                    val middleFragment = MiddleFragment()
                    val bundle = Bundle().apply {
                        putString("DataAsJson", dataString)
                    }
                    middleFragment.arguments = bundle
                    setFragment(middleFragment)
                }
            }
    }
}
    private fun setFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction().replace(R.id.main_frame, fragment).commit()
        return true
    }
}





