package com.example.recordkeeper

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.fragment.app.commit
import com.example.recordkeeper.cycling.CyclingFragment
import com.example.recordkeeper.databinding.ActivityMainBinding
import com.example.recordkeeper.running.RunningFragment
import com.google.android.material.navigation.NavigationBarView


class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonNav.setOnItemSelectedListener(this)

        // This callback will only be called when MyFragment is at least Started.
        onBackPressedDispatcher.addCallback(this) {
            back()
        }
    }

    private fun back() {
        AlertDialog.Builder(this)
            .setTitle("Alert")
            .setMessage("Are you sure to leave the app?")
            .setPositiveButton("Yes") { _, _ ->
                finish()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .setView(R.layout.dialog_warning)
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val runningPreferences = getSharedPreferences("Running", Context.MODE_PRIVATE)
        val cyclingPreferences = getSharedPreferences("Cycling", Context.MODE_PRIVATE)

        when (item.itemId) {
            R.id.reset_running -> {
                runningPreferences.edit {
                    remove("5km record")
                    remove("5km date")
                    remove("10km record")
                    remove("10km date")
                    remove("Half Marathon record")
                    remove("Half Marathon date")
                    remove("Marathon record")
                    remove("Marathon date")
                }
                return true
            }

            R.id.reset_cycling -> {
                cyclingPreferences.edit {
                    remove("Longest Ride record")
                    remove("Longest Ride date")
                    remove("Biggest Climb record")
                    remove("Biggest Climb date")
                    remove("Best Average Speed record")
                    remove("Best Average Speed date")
                }
                return true
            }

            R.id.reset_all -> {
                runningPreferences.edit {
                    remove("5km record")
                    remove("5km date")
                    remove("10km record")
                    remove("10km date")
                    remove("Half Marathon record")
                    remove("Half Marathon date")
                    remove("Marathon record")
                    remove("Marathon date")
                }
                cyclingPreferences.edit {
                    remove("Longest Ride record")
                    remove("Longest Ride date")
                    remove("Biggest Climb record")
                    remove("Biggest Climb date")
                    remove("Best Average Speed record")
                    remove("Best Average Speed date")
                }
                return true
            }
        }
        return false
    }

    private fun onCyclingClicked() {
        supportFragmentManager.commit {
            replace(R.id.frame_content, CyclingFragment())
        }
    }

    private fun onRunningClicked() {
        supportFragmentManager.commit {
            replace(R.id.frame_content, RunningFragment())
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_running -> {
                onRunningClicked()
                true;
            }

            R.id.nav_cycling -> {
                onCyclingClicked()
                true;
            }

            else -> {
                false;
            }
        }
    }
}