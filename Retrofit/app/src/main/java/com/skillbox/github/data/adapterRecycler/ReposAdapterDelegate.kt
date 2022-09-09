package com.skillbox.github.data.adapterRecycler

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.github.R
import com.skillbox.github.data.UserInfo
import com.skillbox.github.utils.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.repo_item.*

class ReposAdapterDelegate(
    private val onItemClick: (position: Int) -> Unit
) : AbsListItemAdapterDelegate<UserInfo.ReposUser, UserInfo, ReposAdapterDelegate.ReposHolder>() {

    override fun isForViewType(
        item: UserInfo,
        items: MutableList<UserInfo>,
        position: Int
    ): Boolean {
        return item is UserInfo.ReposUser
    }

    override fun onCreateViewHolder(parent: ViewGroup): ReposHolder {
        return ReposHolder(
            parent.inflate(R.layout.repo_item),
            onItemClick
        )
    }

    override fun onBindViewHolder(
        item: UserInfo.ReposUser,
        holder: ReposHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    abstract class BaseReposHolder(
        final override val containerView: View,
        onItemClick: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            containerView.setOnClickListener {
                onItemClick(absoluteAdapterPosition)
            }
        }

        @SuppressLint("SetTextI18n")
        protected fun bindMainInfo(
            userName: String,
            idRepos: Long,
            name: String,
            visibility: String
        ) {
            userOwner.text = userName
            idRepo.text = "Id repo: $idRepos"
            nameRepo.text = name
            visibilityRepo.text = "Visibility repo: $visibility"
        }
    }

    class ReposHolder(
        containerView: View,
        onItemClick: (position: Int) -> Unit
    ) : BaseReposHolder(containerView, onItemClick) {
        fun bind(repos: UserInfo.ReposUser) {
            bindMainInfo(repos.userName, repos.idRepo, repos.nameRepo, repos.visibility)
        }
    }
}
