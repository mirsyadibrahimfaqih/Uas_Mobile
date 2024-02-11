package com.irsyad.filmku.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.irsyad.filmku.MainAdapter
import com.irsyad.filmku.data.network.ApiConfig
import com.irsyad.filmku.data.response.ResultsItem
import com.irsyad.filmku.databinding.ActivityMainBinding
import com.irsyad.filmku.data.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Button click listener
        binding.button.setOnClickListener {
            // Call logout method
            logout()
        }

        // Initialize adapter and RecyclerView
        adapter = MainAdapter(emptyList(), this)
        binding.rvCharacter.layoutManager = GridLayoutManager(this, 2)
        binding.rvCharacter.adapter = adapter

        // Set API key before using MainViewModel
        ApiConfig.setApiKey("5553594234f065a59e3fdc260e3d95ed")

        // Initialize MainViewModel with Context parameter
        mainViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(MainViewModel::class.java)

        // Observe the list of popular movies
        mainViewModel.listPopuler.observe(this, Observer { data ->
            data?.let { results ->
                // Update RecyclerView with the list of popular movies
                adapter.setData(results) // Update data in adapter
            }
        })

        // Fetch the list of popular movies
        mainViewModel.getAllPopuler()
    }

    private fun logout() {
        // Navigate to LoginActivity
        Intent(this, LoginActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
            Toast.makeText(this, "Logout Berhasil", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // Call logout method when back button pressed
        logout()
    }
}