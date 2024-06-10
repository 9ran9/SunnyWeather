package com.example.sunnyweather

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.sunnyweather.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView  //得到导航视图

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        //得到导航控制器，并绑定对应的导航界面

        val appBarConfiguration = AppBarConfiguration(
            //设置导航栏的内容
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
                //绑定对应的导航所在界面
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)  //绑定对应的导航栏
    }
}