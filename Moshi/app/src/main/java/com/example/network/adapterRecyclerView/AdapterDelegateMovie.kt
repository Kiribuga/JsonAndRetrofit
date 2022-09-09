package com.example.network.adapterRecyclerView

import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.network.MainFragmentDirections
import com.example.network.R
import com.example.network.data.MovieRating
import com.example.network.data.RemoteMovie
import com.example.network.utils.inflate
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movie.*

class AdapterDelegateMovie(
    private val onClickButton: (dialog: NavDirections) -> Unit
) :
    AbsListItemAdapterDelegate<RemoteMovie, RemoteMovie, AdapterDelegateMovie.MovieHolder>() {

    override fun isForViewType(
        item: RemoteMovie,
        items: MutableList<RemoteMovie>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): MovieHolder {
        return MovieHolder(parent.inflate(R.layout.item_movie), onClickButton)
    }

    override fun onBindViewHolder(
        item: RemoteMovie,
        holder: MovieHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    abstract class BaseHolder(
        final override val containerView: View,
        onClickButton: (dialog: NavDirections) -> Unit
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            addScore.setOnClickListener {
                onClickButton(
                    MainFragmentDirections.actionMainFragmentToDialogAddScore()
                )
            }
        }

        protected fun bindMainInfo(
            title: String,
            year: Int,
            poster: String,
            genre: String,
            rating: MovieRating,
            scores: Map<String, String>
        ) {
            titleMovie.text = title
            genreMovie.text = genre
            yearMovie.text = year.toString()
            ratMovie.text = rating.toString()
            scoreMovie.text = scores.toString()

            Glide.with(itemView)
                .load(poster)
                .placeholder(R.drawable.ic_movies)
                .into(posterMovie)
        }
    }

    class MovieHolder(
        containerView: View,
        onClickButton: (dialog: NavDirections) -> Unit
    ) : BaseHolder(containerView, onClickButton) {

        fun bind(movie: RemoteMovie) {
            bindMainInfo(
                movie.title,
                movie.year,
                movie.poster,
                movie.genre,
                movie.rating,
                movie.scores
            )
        }
    }
}