package com.dev.vapeshop

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.util.TypedValue
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.core.view.children
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dev.vapeshop.databinding.ActivityMainBinding
import com.dev.vapeshop.features.preferences_manager.PreferencesManager
import com.dev.vapeshop.features.preferences_manager.Theme
import com.google.android.material.navigation.NavigationView
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var preferencesManager: PreferencesManager

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initTheme()
        bindActionBar()

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host)

        bindNavTitle()

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.aboutFragment,
            R.id.devicesFragment,
            R.id.cartridgesFragment,
            R.id.liquidsFragment,
            R.id.tobaccoFragment,
            R.id.settingsFragment), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu)

        findNavController(R.id.nav_host).addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                in listOf(R.id.aboutFragment,
                    R.id.devicesFragment,
                    R.id.cartridgesFragment,
                    R.id.liquidsFragment,
                    R.id.tobaccoFragment,
                    R.id.settingsFragment),
                -> {
                    mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun initTheme() {
        lifecycleScope.launch(Dispatchers.IO) {
            val themeMode = preferencesManager.themePreferencesFlow.first().theme
            withContext(Dispatchers.Main) {
                when (themeMode) {
                    Theme.THEME_DARK -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                    Theme.THEME_LIGHT -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                    Theme.THEME_SYSTEM -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    }
                    Theme.THEME_UNDEFINED -> {
                    }
                }
            }
        }
    }

    private fun bindNavTitle() {
        val view = binding.navView.getHeaderView(0).findViewById<TextView>(R.id.tvTitle)

        val paint = view.paint
        val width = paint.measureText(view.text.toString())
        val textShader = LinearGradient(
            0F,
            0F,
            width,
            view.textSize,
            intArrayOf(
                Color.parseColor("#FF5F6D"),
                Color.parseColor("#FFC371"),
            ),
            null, Shader.TileMode.CLAMP)
        view.paint.shader = textShader
    }

    private val mDrawerToggle by lazy {
        ActionBarDrawerToggle(this,
            binding.drawerLayout,
            binding.appBarMain.toolbar,
            R.string.menu_about,
            R.string.menu_cartridges)
    }

    private fun bindActionBar() {
        binding.appBarMain.toolbar.title = "Shop"
        binding.appBarMain.toolbar.titleMarginBottom = convertDpToPx(15)
        binding.appBarMain.toolbar.elevation = 0F
        setSupportActionBar(binding.appBarMain.toolbar)

        mDrawerToggle.isDrawerIndicatorEnabled = false
        mDrawerToggle.setToolbarNavigationClickListener {
            if (binding.drawerLayout.isDrawerVisible(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        for (view in binding.appBarMain.toolbar.children) {
            if (view is TextView) {
                val paint = view.paint
                val width = paint.measureText(view.text.toString())
                val textShader = LinearGradient(
                    0F,
                    0F,
                    width,
                    view.textSize,
                    intArrayOf(
                        Color.parseColor("#FF5F6D"),
                        Color.parseColor("#FFC371"),
                    ),
                    null, Shader.TileMode.CLAMP)
                view.paint.shader = textShader
            }
        }
    }

    private fun convertDpToPx(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            binding.root.context.resources.displayMetrics
        ).toInt()
    }
}