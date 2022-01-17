package com.bimabagaskhoro.asigmentdigiasa.ui.toprated

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bimabagaskhoro.asigmentdigiasa.adapter.MovieAdapter
import com.bimabagaskhoro.asigmentdigiasa.databinding.FragmentTopRatedBinding
import com.bimabagaskhoro.asigmentdigiasa.ui.ViewModelFactory
import com.bimabagaskhoro.asigmentdigiasa.ui.detail.DetailsActivity
import com.bimabagaskhoro.asigmentdigiasa.vo.Status

class TopRatedFragment : Fragment() {
    private var _binding: FragmentTopRatedBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTopRatedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[TopRatedViewModel::class.java]
            movieAdapter = MovieAdapter()
            viewModel.getTopRatedMovie().observe(viewLifecycleOwner, { movie ->
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
                binding.rvItems.apply {
                    layoutManager =
                        GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
                    setHasFixedSize(true)
                    adapter = movieAdapter
                }
            })

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}