package com.example.giphycodingchallenge.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.giphycodingchallenge.db.GifEntity

class GifsPagedAdapter(
    private val quotaOfItemWidth: Int,
    private val tablet: Boolean,
    private val onClickPhone: (GifEntity) -> Unit,
    private val onClickTablet: (GifEntity) -> Unit
) : PagedListAdapter<GifEntity, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GifViewHolder.create(
            parent,
            quotaOfItemWidth,
            tablet,
            onClickPhone,
            onClickTablet
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            (holder as GifViewHolder).bind(item)
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<GifEntity>() {
            override fun areItemsTheSame(oldItem: GifEntity, newItem: GifEntity): Boolean =
                oldItem.title == newItem.title && oldItem.url == newItem.url

            override fun areContentsTheSame(oldItem: GifEntity, newItem: GifEntity): Boolean =
                oldItem == newItem
        }
    }
}
