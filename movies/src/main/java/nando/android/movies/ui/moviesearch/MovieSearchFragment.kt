package nando.android.movies.ui.moviesearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.lottie.LottieDrawable
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.fragment_movie_search.*
import kotlinx.android.synthetic.main.fragment_movie_search.fab
import kotlinx.android.synthetic.main.fragment_movie_search.lottie_progress
import nando.android.core.util.extensions.debounceTextChange
import nando.android.core.util.extensions.hideKeyboard
import nando.android.core.util.extensions.showKeyboard
import nando.android.movies.R
import nando.android.movies.model.moviesearch.MovieThumbnailModel
import nando.android.movies.viewmodel.moviesearch.MovieSearchViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MovieSearchFragment: Fragment() {

    private val viewModel: MovieSearchViewModel by viewModel()
    private val adapter: MovieSearchListAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        setTextChangeListeners()
        initRecycler()
        lottie_progress.repeatCount = LottieDrawable.INFINITE
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observe()
    }

    private fun initRecycler() {
        list_movies.layoutManager = GridLayoutManager(context, 3)
        list_movies.adapter = adapter
    }

    private fun setTextChangeListeners() {
        input_search.debounceTextChange(scope = lifecycleScope) {
            viewModel.search(it)
        }
        adapter.clickListener = {
            findNavController().navigate(MovieSearchFragmentDirections.actionMovieSearchFragmentToMovieDetailsFragment(it.imdbID))
        }
    }

    private fun setClickListeners() {
        fab.setOnClickListener {
            fab.isExpanded = true
            input_search.showKeyboard()
            input_search.requestFocus()
        }
        image_close.setOnClickListener {
            fab.isExpanded = false
            input_search.hideKeyboard()
        }
    }

    private fun observe() {
        viewModel.data.observe(viewLifecycleOwner, Observer {
            hideLoading()
            adapter.submitList(it)
        })
        viewModel.uiState.observe(viewLifecycleOwner, Observer {
            when(it.state) {
                is MovieSearchViewModel.ViewState.Loading -> showLoading()
                is MovieSearchViewModel.ViewState.Error -> {
                    hideLoading()
                    showError(it.errorHandler.errorMessage())
                }
                is MovieSearchViewModel.ViewState.Success -> hideLoading()
            }
        })
    }

    private fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun showLoading() {
        lottie_progress.visibility = View.VISIBLE
        lottie_progress.playAnimation()
    }

    private fun hideLoading() {
        lottie_progress.visibility = View.GONE
        lottie_progress.cancelAnimation()
    }
}