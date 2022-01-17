package com.bimabagaskhoro.asigmentdigiasa.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bimabagaskhoro.asigmentdigiasa.R
import com.bimabagaskhoro.asigmentdigiasa.data.source.local.entity.MovieEntity
import com.bimabagaskhoro.asigmentdigiasa.data.source.remote.response.ResultsItem
import com.bimabagaskhoro.asigmentdigiasa.databinding.ItemBinding
import com.bimabagaskhoro.asigmentdigiasa.ui.detail.DetailViewModel
import com.bimabagaskhoro.asigmentdigiasa.ui.detail.DetailsActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MovieAdapter :
    PagedListAdapter<MovieEntity, MovieAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = getItem(position)
        if (items != null) holder.bind(items)
    }

    inner class ViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MovieEntity) {
            binding.apply {
                Glide.with(itemView)
                    .load(EXTRA_LINK + data.posterPath)
                    .apply(
                        RequestOptions.placeholderOf(R.color.grey_add)
                    )
                    .into(imgItems)
                tvTittleItems.text = data.title
                tvDescItems.text = data.overview
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailsActivity::class.java)
                    intent.putExtra(DetailsActivity.EXTRA_DATA, data.id)
                    itemView.context.startActivity(intent)
                }
//                imgItems.setOnClickListener {
//                    var state = data.addFav
//                    state = !state
//                    DetailsActivity().setStatus(state)
//                }

            }

        }
    }

    companion object {
        const val EXTRA_LINK = "https://image.tmdb.org/t/p/w500"
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean =
                oldItem == newItem
        }

    }

}