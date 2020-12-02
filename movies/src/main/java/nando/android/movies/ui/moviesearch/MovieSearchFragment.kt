package nando.android.movies.ui.moviesearch

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_movie_search.*
import nando.android.core.util.extensions.hideKeyboard
import nando.android.core.util.extensions.showKeyboard
import nando.android.movies.R
import nando.android.movies.model.moviesearch.MovieThumbnailModel
import nando.android.movies.viewmodel.MovieSearchViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MovieSearchFragment: Fragment(R.layout.fragment_movie_search) {

    private val viewModel: MovieSearchViewModel by viewModel()
    private val adapter: PagedListAdapter<MovieThumbnailModel, MovieSearchListAdapter.MovieViewHolder>
        by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        setTextChangeListeners()
        initRecycler()
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
        input_search.doOnTextChanged { text, start, before, count ->
            viewModel.search(text.toString())
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
            adapter.submitList(it)
        })
    }
}