package nando.android.movies.ui.moviedetails

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.airbnb.lottie.LottieDrawable
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.layout_movie_details.*
import nando.android.movies.R
import nando.android.movies.model.moviedetails.MovieItemModel
import nando.android.movies.viewmodel.moviedetails.MovieDetailsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MovieDetailsFragment: Fragment(R.layout.fragment_movie_details) {

    private val viewModel: MovieDetailsViewModel by viewModel()
    private val progressDialog: ProgressDialog by lazy {
        ProgressDialog(context).apply { setCancelable(false) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = getIdFromArgs()
        viewModel.getMovie(id)
        attachToolbarToNavigation()
        setClickListener()
        lottie_progress.repeatCount = LottieDrawable.INFINITE
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observe()
    }

    private fun attachToolbarToNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar_collapsed.setupWithNavController(toolbar, navController, appBarConfiguration)
    }

    private fun getIdFromArgs() =
        arguments?.let { MovieDetailsFragmentArgs.fromBundle(it).movieId } ?: "0"

    private fun setClickListener() {
        fab.setOnClickListener {
            viewModel.decideFavourite()
        }
    }

    private fun observe() {
        viewModel.liveData.observe(viewLifecycleOwner, Observer {
            when(it.state) {
                is MovieDetailsViewModel.ViewState.ShowDetails -> renderDetails(it.data)
                is MovieDetailsViewModel.ViewState.Error -> showErrorToast(it.errorHandler.errorMessage(context))
                is MovieDetailsViewModel.ViewState.Loading -> {
                    showLoading()
                }
            }
        })
    }

    private fun renderDetails(item: MovieItemModel) {
        Picasso.get().load(item.imagePath).placeholder(R.drawable.movie_placeholder).into(image_movie)
        toolbar.title = item.title
        text_runtime_release_and_genre.text =
            String.format("%1s, %2s, %3s", item.runtime, item.releaseDate, item.genre)
        text_director.text = item.director
        text_actors.text = item.actors
        text_plot.text = item.plot
        text_metascore.text = item.metaScore
        val favResId = if (item.isFavourite) {
            R.drawable.ic_star_blue
        }
        else {
            R.drawable.ic_star_grey
        }
        fab.setImageResource(favResId)
        hideLoading()
    }

    private fun showErrorToast(message: String) {
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