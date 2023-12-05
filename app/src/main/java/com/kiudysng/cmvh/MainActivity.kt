package com.kiudysng.cmvh

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.kiudysng.cmvh.databinding.ActivityMainBinding
import com.kiudysng.cmvh.ui.task.AddTaskActivity
import com.kiudysng.cmvh.utils.RxBus

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 在活动的onCreate方法中
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        supportActionBar?.hide() // 隐藏默认的操作栏
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)
        RxBus.observerEvent(this) {
            if (it == "success") {
                Snackbar.make(
                    binding.appBarMain.fab,
                    "dd Task Success _> >",
                    Snackbar.LENGTH_LONG
                ).setAction("Action", null).show()
            }
        }
        binding.appBarMain.fab.setOnClickListener { view ->
            Intent(this@MainActivity, AddTaskActivity::class.java).apply {
                startActivity(this)
            }
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id ){
                R.id.nav_home->{
                    binding.appBarMain.fab.show()
                }
                R.id.nav_gallery,
                R.id.nav_slideshow->{
                    binding.appBarMain.fab.hide()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}