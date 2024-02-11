package com.irsyad.filmku.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.irsyad.filmku.data.response.ResultsItem
import com.irsyad.filmku.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mendapatkan data film dari Intent
        val film = intent.getParcelableExtra<ResultsItem>("film")

        // Menampilkan data film di layout
        film?.let { showFilmDetails(it) }
    }

    private fun showFilmDetails(film: ResultsItem) {
        binding.tvTitle.text = film.title
        binding.tvOverview.text = film.overview
        binding.tvReleaseDate.text = film.releaseDate

        // Load image using Glide for posterPath
        film.posterPath?.let {
            Glide.with(this)
                .load(it)
                .into(binding.ivBackdrop)
        }

        // Load image using Glide for backdropPath
        film.backdropPath?.let {
            Glide.with(this)
                .load(it)
                .into(binding.ivBackdrop)
        }
    }
}
