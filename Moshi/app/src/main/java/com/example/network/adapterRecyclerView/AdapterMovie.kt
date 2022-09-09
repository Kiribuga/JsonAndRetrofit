package com.example.network.adapterRecyclerView

import androidx.navigation.NavDirections
import androidx.recyclerview.widget.DiffUtil
import com.example.network.data.RemoteMovie
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class AdapterMovie(
    private val onClickButton: (dialog: NavDirections) -> Unit
): AsyncListDifferDelegationAdapter<RemoteMovie>(MovieDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(AdapterDelegateMovie(onClickButton))
    }

    class MovieDiffUtilCallback : DiffUtil.ItemCallback<RemoteMovie>() {
        override fun areItemsTheSame(oldItem: RemoteMovie, newItem: RemoteMovie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: RemoteMovie, newItem: RemoteMovie): Boolean {
            return oldItem == newItem
        }

    }
}