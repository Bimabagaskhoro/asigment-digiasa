package com.bimabagaskhoro.asigmentdigiasa.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bimabagaskhoro.asigmentdigiasa.adapter.MovieAdapter
import com.bimabagaskhoro.asigmentdigiasa.databinding.FragmentFavoriteBinding
import com.bimabagaskhoro.asigmentdigiasa.ui.ViewModelFactory

class FavoriteFragment : Fragment() {
    private val TAG = FavoriteFragment::class.java.simpleName
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]
            movieAdapter = MovieAdapter()
            viewModel.getFavListMovie().observe(viewLifecycleOwner, { movie ->
                movieAdapter.submitList(movie)

            })
            setRecyclerView()
        }

    }

    private fun setRecyclerView() {
        binding.rvItems.apply {
            layoutManager =
                GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}