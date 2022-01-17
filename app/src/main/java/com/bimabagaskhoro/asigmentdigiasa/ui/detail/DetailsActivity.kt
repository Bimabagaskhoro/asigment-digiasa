package com.bimabagaskhoro.asigmentdigiasa.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bimabagaskhoro.asigmentdigiasa.R
import com.bimabagaskhoro.asigmentdigiasa.data.source.local.entity.MovieEntity
import com.bimabagaskhoro.asigmentdigiasa.databinding.ActivityDetailsBinding
import com.bimabagaskhoro.asigmentdigiasa.ui.MainActivity
import com.bimabagaskhoro.asigmentdigiasa.ui.ViewModelFactory
import com.bimabagaskhoro.asigmentdigiasa.vo.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DetailsActivity : AppCompatActivity() {
    private val TAG = DetailsActivity::class.java.simpleName
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(
            this, factory
        )[DetailViewModel::class.java]

        val movieId = intent.getIntExtra(EXTRA_DATA, 0)
        getData(movieId)

        binding.apply {
            imgBack.setOnClickListener {
                val intent = Intent(this@DetailsActivity, MainActivity::class.java)
                startActivity(intent)
            }
            imgShare.setOnClickListener {
                Toast.makeText(this@DetailsActivity, "You share movie", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getData(movieId: Int) {
        viewModel.setDetailMovie(movieId).observe(this, { detail ->
            if (detail != null) {
                when (detail.status) {
                    Status.LOADING -> {
                        Log.e(TAG, "Loading get Detail")
                    }
                    Status.SUCCESS -> {
                        getDataDetail(detail.data)

                    }
                    Status.ERROR -> {
                        Log.e(TAG, "Error get Detail")
                    }
                }
            }
        })
    }

    private fun getDataDetail(data: MovieEntity?) {
        if (data != null) {
            binding.apply {
                tvTittle.text = data.title
                tvCount.text = data.voteCount.toString()
                tvOriginalLanguage.text = data.originalLanguage
                tvReleaseDate.text = data.releaseDate
                tvOverview.text = data.overview
                Glide.with(this@DetailsActivity)
                    .load(EXTRA_LINK + data.posterPath)
                    .apply(RequestOptions.placeholderOf(R.color.grey_add))
                    .into(imgItems)
                Glide.with(this@DetailsActivity)
                    .load(EXTRA_LINK + data.backdropPath)
                    .apply(RequestOptions.placeholderOf(R.color.grey_add))
                    .into(imgBackdrop)

                var state = data.addFav
                setStatus(state)
                toggleFav.setOnClickListener {
                    state = !state
                    viewModel.setMovieFavorite()
                    setStatus(state)
                }
            }
        }
    }

    fun setStatus(state: Boolean) {
        if (state) {
            binding.toggleFav.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorited
                )
            )
        } else {
            binding.toggleFav.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_favorite
                )
            )
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_LINK = "https://image.tmdb.org/t/p/w500"
    }
}