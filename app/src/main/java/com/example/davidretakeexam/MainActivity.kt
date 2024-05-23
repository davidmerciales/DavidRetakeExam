package com.example.davidretakeexam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.davidretakeexam.databinding.ActivityMainBinding
import com.example.davidretakeexam.presenter.screen.homescreen.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val personListFragment = HomeScreen()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, personListFragment)
            .commit()
    }
}