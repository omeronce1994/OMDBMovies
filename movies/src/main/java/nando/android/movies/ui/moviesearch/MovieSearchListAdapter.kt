package nando.android.movies.ui.moviesearch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_movie.view.*
import nando.android.movies.R
import nando.android.movies.model.moviesearch.MovieThumbnailModel

class MovieSearchListAdapter: PagedListAdapter<MovieThumbnailModel, MovieSearchListAdapter.MovieViewHolder>(
    MovieThumbnailModel.diffUtil
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(item: MovieThumbnailModel) = with(itemView) {
            text_year.text = item.released
            val favIconRes = if (item.isFavourite) {
                R.drawable.ic_star_blue
            } else {
                R.drawable.ic_star_grey
            }
            image_favourite.setImageResource(favIconRes)
            Picasso.get().load(item.imagePath).fit().into(image_favourite)
        }
    }
}