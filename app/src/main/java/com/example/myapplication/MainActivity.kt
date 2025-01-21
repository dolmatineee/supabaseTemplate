package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: SupabaseClient
    private lateinit var adapter: SneakerAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(SupabaseClient::class.java)
        adapter = SneakerAdapter(emptyList(), emptyMap())

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter

        viewModel.sneakers.observe(this, { sneakers ->
            adapter.sneakers = sneakers
            adapter.notifyDataSetChanged()
        })

        viewModel.photos.observe(this, { photos ->
            adapter.photos = photos
            adapter.notifyDataSetChanged()
        })

        viewModel.loadSneakers()
    }
}