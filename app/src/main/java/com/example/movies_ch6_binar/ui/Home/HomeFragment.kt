package com.example.movies_ch6_binar.ui.Home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.movies_ch6_binar.Models.model.Movie
import com.example.movies_ch6_binar.Models.model.User
import com.example.movies_ch6_binar.R
import com.example.movies_ch6_binar.adapter.AppAdapter
import com.example.movies_ch6_binar.databinding.FragmentHomeBinding
import com.example.movies_ch6_binar.utils.Constant.TAG
import dagger.hilt.android.AndroidEntryPoint

data class MovieListState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: String = "",
    val user: User? = null
)

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private val adapter: AppAdapter by lazy { AppAdapter(::onClicked) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeQueryResult()
        getUserEmail()
        moveToProfile()
        moveToFavorite()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeQueryResult() {
        viewModel.state.observe(viewLifecycleOwner) { result ->
            showLoading(result.isLoading)
            showMovieList(result.movies)
            Log.d(TAG, "Fragment -> ${result.movies}")
        }
    }

    private fun getUserEmail() {
        viewModel.email.observe(viewLifecycleOwner) { email ->
            viewModel.getUser(email)
        }
        getUserData()
    }

    private fun getUserData() {
        viewModel.userData.observe(viewLifecycleOwner) { result ->
            showUserData(result.user)
        }
    }

    private fun showUserData(user: User?) {
        binding.usernameTextView.text =
            getString(R.string.get_username, user?.username ?: "null")
        binding.profileButton.load(user?.profilePhoto)
    }

    private fun onClicked(movie: Movie) {
        viewModel.userData.observe(viewLifecycleOwner) { result ->
            val direction =
                HomeFragmentDirections.actionHomeFragmentToDetailMovieFragment(result.user?.id ?: 0,movie)
            findNavController().navigate(direction)

            Log.d(TAG, "userId -> $result.user?.id")
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

    private fun moveToProfile() {
        binding.profileButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
    }

    private fun moveToFavorite() {
        viewModel.userData.observe(viewLifecycleOwner) { result ->
            binding.favoriteButton.setOnClickListener {
                val direction =
                    HomeFragmentDirections.actionHomeFragmentToFavoriteMovieFragment(
                        result.user?.id ?: 0
                    )
                findNavController().navigate(direction)
            }
        }
    }
}