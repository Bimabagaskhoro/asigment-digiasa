package com.bimabagaskhoro.asigmentdigiasa.ui.popular

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bimabagaskhoro.asigmentdigiasa.R
import com.bimabagaskhoro.asigmentdigiasa.adapter.MovieAdapter
import com.bimabagaskhoro.asigmentdigiasa.databinding.FragmentPopularBinding
import com.bimabagaskhoro.asigmentdigiasa.ui.ViewModelFactory
import com.bimabagaskhoro.asigmentdigiasa.ui.detail.DetailsActivity
import com.bimabagaskhoro.asigmentdigiasa.vo.Status

class PopularFragment : Fragment() {
    private lateinit var binding: FragmentPopularBinding
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[PopularViewModel::class.java]
            movieAdapter = MovieAdapter()
            viewModel.getPopularMovie().observe(viewLifecycleOwner, { movie ->
                if (movie != null) {
                    when (movie.status) {
                        Status.LOADING -> showLoading(true)
                        Status.SUCCESS -> {
                            showLoading(false)
                            movieAdapter.apply {
                                submitList(movie.data)
                            }
                        }
                        Status.ERROR -> {
                            showLoading(true)
                        }
                    }
                }

            })
            setRecyclerView()

        }
    }

    private fun setRecyclerView() {
        binding.rvItems.apply {
            layoutManager =
                GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.apply {
                progressbar.visibility = View.VISIBLE
                rvItems.visibility = View.INVISIBLE
            }
        } else {
            binding.apply {
                progressbar.visibility = View.INVISIBLE
                rvItems.visibility = View.VISIBLE
            }
        }
    }
}