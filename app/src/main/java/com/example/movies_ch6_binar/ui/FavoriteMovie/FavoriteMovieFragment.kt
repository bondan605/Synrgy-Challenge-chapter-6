package com.example.movies_ch6_binar.ui.FavoriteMovie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies_ch6_binar.Models.model.Movie
import com.example.movies_ch6_binar.adapter.AppAdapter
import com.example.movies_ch6_binar.databinding.FragmentFavoriteMovieBinding
import com.example.movies_ch6_binar.utils.Constant
import com.example.movies_ch6_binar.utils.Constant.TAG
import dagger.hilt.android.AndroidEntryPoint

data class FavoriteState(
    val isLoading: Boolean = false,
    val error: String = "",
    val movies: List<Movie>? = null
)

@AndroidEntryPoint
class FavoriteMovieFragment : Fragment() {
    private var _binding: FragmentFavoriteMovieBinding? = null
    private val binding get() = _binding!!

    private val adapter: AppAdapter by lazy { AppAdapter(::onClicked) }

    private val viewModel: FavMovieVM by viewModels()

    private val args: FavoriteMovieFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteMovieBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeQueryResult()
        moveToMovieList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeQueryResult() {
        viewModel.getMovie(args.UserId)
        Log.d(TAG, "Favorite UserId -> ${args.UserId}")
        viewModel.state.observe(viewLifecycleOwner) { result ->
            showLoading(result.isLoading)
            showMovieList(result.movies ?: emptyList())
            Log.d(Constant.TAG, "Fragment -> ${result.movies}")
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressCircular.visibility = View.VISIBLE
        } else {
            binding.progressCircular.visibility = View.GONE
        }
    }

    private fun showMovieList(movies: List<Movie>) {
        adapter.submitList(movies)
        binding.movieRecyclerView.adapter = adapter
        binding.movieRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun onClicked(movie: Movie) {
        val direction =
            FavoriteMovieFragmentDirections.actionFavoriteMovieFragmentToDetailMovieFragment(
                args.UserId,
                movie
            )
        findNavController().navigate(direction)

        Log.d(Constant.TAG, "userId -> ${args.UserId}")

    }

    private fun moveToMovieList() {
        binding.toolbarId.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}