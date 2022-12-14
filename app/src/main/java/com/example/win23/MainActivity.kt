package com.example.win23

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.win23.databinding.ActivityMainBinding
import com.onesignal.OneSignal
import me.ibrahimsn.lib.SmoothBottomBar

class MainActivity : AppCompatActivity(){
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        LoadBackgroundImage.setImage(this, binding.constraintLayoutMain)
        barItemSelected()
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId("714b9f14-381d-4fc4-a93c-28d480557381")
    }

    private fun barItemSelected(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val buttonBar = binding.bottomBar
        buttonBar.onItemSelected = {
            when (it) {
                0 -> {
                    navController.navigate(R.id.homeFragment)
                }
                1 -> {
                    navController.navigate(R.id.statisticsFragment)
                }
                2 -> {
                    navController.navigate(R.id.fragmentSettings)
                }
            }
        }
    }
}