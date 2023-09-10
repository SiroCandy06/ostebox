package com.ostebox.osteup

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Home()).commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Home()).commit()
            R.id.nav_settings -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Settings()).commit()
            R.id.nav_info -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Info()).commit()
            R.id.nav_tool -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Tools()).commit()
            R.id.nav_exit -> {
                Toast.makeText(this, "Exit Completed", Toast.LENGTH_SHORT).show()
                finishAffinity()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    class InfoActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.fragment_info)

            val discordButton = findViewById<Button>(R.id.severDiscord)
            val fanpageButton = findViewById<Button>(R.id.fanpage)
            val youtubeButton = findViewById<Button>(R.id.youtube)
            val facebookButton = findViewById<Button>(R.id.facebook)

            discordButton.setOnClickListener {
                val discordUrl = getString(R.string.discord_link)
                openWebPage(discordUrl)
            }

            fanpageButton.setOnClickListener {
                val fanpageUrl = getString(R.string.fanpage_link)
                openWebPage(fanpageUrl)
            }

            youtubeButton.setOnClickListener {
                val youtubeUrl = getString(R.string.youtube_link)
                openWebPage(youtubeUrl)
            }

            facebookButton.setOnClickListener {
                val facebookUrl = getString(R.string.facebook_link)
                openWebPage(facebookUrl)
            }
        }

        @SuppressLint("QueryPermissionsNeeded")
        private fun openWebPage(url: String) {
            val webpage = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, webpage)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }
    }

}






