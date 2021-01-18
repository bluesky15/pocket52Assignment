package com.lkb.assignment.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.lkb.assignment.R
import com.lkb.assignment.databinding.ItemHolderBinding
import com.lkb.assignment.domain.model.PostCount
import kotlin.properties.Delegates

class UserPostsAdapter(private val postClickListener: OnPostClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface OnPostClickListener {
        fun onPostClick(position: Int)
    }

    var mPostList: List<PostCount> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holderPostBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.item_holder, parent, false
        )
        return PostViewHolder(holderPostBinding, postClickListener)
    }

    override fun getItemCount(): Int = if (mPostList.isNullOrEmpty()) 0 else mPostList.size

    private fun getItem(position: Int): PostCount = mPostList[position]

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PostViewHolder).onBind(getItem(position))
    }

    private inner class PostViewHolder(
        private val viewDataBinding: ViewDataBinding,
        private val onPostClickListener: OnPostClickListener
    ) :
        RecyclerView.ViewHolder(viewDataBinding.root), View.OnClickListener {

        fun onBind(post: PostCount) {
            viewDataBinding.root.setOnClickListener(this)
            (viewDataBinding as ItemHolderBinding).postCount = post
        }

        override fun onClick(v: View?) {
            onPostClickListener.onPostClick(adapterPosition)
        }
    }
}