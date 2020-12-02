package nando.android.movies.model.moviesearch

import androidx.recyclerview.widget.DiffUtil

data class MovieThumbnailModel(
    val released: String,
    val imagePath: String,
    val isFavourite: Boolean
) {
    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<MovieThumbnailModel>() {
            override fun areItemsTheSame(
                oldItem: MovieThumbnailModel,
                newItem: MovieThumbnailModel
            ): Boolean
                = oldItem === newItem


            override fun areContentsTheSame(
                oldItem: MovieThumbnailModel,
                newItem: MovieThumbnailModel
            ): Boolean
               = oldItem == newItem

        }
    }
}