package com.example.movies_ch6_binar.ui.DetailMovie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.movies_ch6_binar.Models.model.Detail
import com.example.movies_ch6_binar.Models.model.Favorite
import com.example.movies_ch6_binar.R
import com.example.movies_ch6_binar.databinding.FragmentDetailMovieBinding
import com.example.movies_ch6_binar.utils.Constant.BASE_IMAGE_URL
import com.example.movies_ch6_binar.utils.Constant.TAG
import com.example.movies_ch6_binar.utils.Extension.loadImage
import com.example.movies_ch6_binar.utils.Extension.showLongToast
import dagger.hilt.android.AndroidEntryPoint

data class DetailMovieNotification (
    val isLoading: Boolean = false,
    val movie: Detail? = null,
    val error: String = "",
    val favoriteMovie: Unit? = null,
    val favoriteMovies: Favorite? = null
)

@AndroidEntryPoint
class DetailMovieFragment : Fragment() {
    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!

    private val args: DetailMovieFragmentArgs by navArgs()

    private val viewModel: DetailMovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailMovieBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "Movie id args -> ${args.Mov.id}")
        val movieId = args.Mov.id
        val userId = args.UserId
        observeFavoriteMovie(userId, movieId)
        getMovieByID(movieId)
        moveToMovieList()
        addToFavoriteMovie()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeFavoriteMovie(userId: Int, movieId: Int) {
        viewModel.observeFavoriteMovie(userId, movieId)
    }

    private fun getMovieByID(movieId: Int) {
        viewModel.getMovie(movieId)
        observeQueryResult()
    }

    private fun observeQueryResult() {
        viewModel.state.observe(viewLifecycleOwner) { result ->
            showLoading(result.isLoading)
            showMovie(result.movie)
            showErrorLog(result.error)
            Log.d(TAG, "Detail Fragment -> ${result.movie}")
        }
    }

    private fun showErrorLog(message: String) {
        Log.d(TAG, "Detail Error -> $message")
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressCircular.visibility = View.VISIBLE
        } else {
            binding.progressCircular.visibility = View.GONE
        }
    }

    private fun showMovie(movie: Detail?) {
        binding.apply {
            val ratingText = getString(R.string.get_rating, movie?.voteAverage.toString())
            val durationText = getString(R.string.get_duration, movie?.runtime.toString())
            titleTextView.text = movie?.title
            ratingTextView.text = ratingText
            movieDurationTextView.text = durationText
            movieLanguageTextView.text = movie?.originalLanguage
            movieReleaseDateTextView.text = movie?.releaseDate
            overviewTextView.text = movie?.overview
            requireContext().loadImage(BASE_IMAGE_URL + movie?.posterPath, binding.posterImageView)
        }
    }

    private fun addToFavoriteMovie() {
        viewModel.favoriteMovie.observe(viewLifecycleOwner) { result ->
            val isFavorite = result?.movieId == args.Mov.id
            binding.toggleFavorite.setOnClickListener {
                if (result == null) {
                    args.Mov.userId = args.UserId
                    viewModel.addFavoriteMovie(args.Mov)
                    requireContext().showLongToast("Success Add To Fvaorite")
                } else {
                    viewModel.deleteFavoriteMovie(args.UserId, result.id)
                    requireContext().showLongToast("Success Delete From Fvaorite")
                }
            }
            binding.toggleFavorite.isChecked = isFavorite
        }
    }

    private fun moveToMovieList() {
        binding.toolbarId.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}