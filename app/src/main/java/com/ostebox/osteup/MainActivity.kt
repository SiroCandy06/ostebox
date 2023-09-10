package com.ostebox.osteup

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
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
            R.id.nav_convertsetedit -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, convertsetedit()).commit()
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

    class InfoActivity : AppCompatActivity() { // Đây là hoạt động khi bấm vào nút của info

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.fragment_info)

            val discordTextView = findViewById<TextView>(R.id.severDiscord)
            val fanpageTextView = findViewById<TextView>(R.id.fanpage)
            val youtubeTextView = findViewById<TextView>(R.id.youtube)
            val facebookTextView = findViewById<TextView>(R.id.facebook)

            val discordSpan = createClickableSpan(getString(R.string.discord_link))
            val fanpageSpan = createClickableSpan(getString(R.string.fanpage_link))
            val youtubeSpan = createClickableSpan(getString(R.string.youtube_link))
            val facebookSpan = createClickableSpan(getString(R.string.facebook_link))

            discordTextView.text = setClickableSpanText("Discord", discordSpan)
            fanpageTextView.text = setClickableSpanText("Fanpage", fanpageSpan)
            youtubeTextView.text = setClickableSpanText("YouTube", youtubeSpan)
            facebookTextView.text = setClickableSpanText("Facebook", facebookSpan)

            discordTextView.movementMethod = LinkMovementMethod.getInstance()
            fanpageTextView.movementMethod = LinkMovementMethod.getInstance()
            youtubeTextView.movementMethod = LinkMovementMethod.getInstance()
            facebookTextView.movementMethod = LinkMovementMethod.getInstance()
        }

        private fun createClickableSpan(url: String): ClickableSpan {
            return object : ClickableSpan() {
                override fun onClick(view: View) {
                    openWebPage(url)
                }
            }
        }

        private fun setClickableSpanText(text: String, span: ClickableSpan): Spannable {
            val spannable = Spannable.Factory.getInstance().newSpannable(text)
            spannable.setSpan(span, 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            return spannable
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

// Code Setedit






