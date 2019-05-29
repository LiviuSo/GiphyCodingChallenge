package com.example.giphycodingchallenge.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.giphycodingchallenge.R
import com.example.giphycodingchallenge.db.GifEntity
import com.example.giphycodingchallenge.db.scaleHeightPx
import com.example.giphycodingchallenge.ui.GifDetailActivity

class GifViewHolder(
    view: View,
    private val allowedWidthPx: Int,
    private val isTablet: Boolean,
    private val onClickPhone: (GifEntity) -> Unit,
    private val onClickTablet: (GifEntity) -> Unit
) : RecyclerView.ViewHolder(view) {

    private val image = view.findViewById<ImageView>(R.id.image)

    init {
        Log.d("GifViewHolder", "$allowedWidthPx")
        image.setOnClickListener {
            val intent = Intent(view.context, GifDetailActivity::class.java)
            view.context.startActivity(intent)
        }
    }

    fun bind(item: GifEntity?) {
        if (item == null) {
            image.visibility = View.GONE
        } else {
            showGifData(item)
        }
    }

    private fun showGifData(item: GifEntity) {
        if (image.visibility == View.GONE) {
            image.visibility = View.VISIBLE
        }
        Glide.with(image.context)
            .load(item.url)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.placeholder_image)
            .override(allowedWidthPx, item.scaleHeightPx(allowedWidthPx))
            .into(image)
        image.setOnClickListener {
            if (isTablet) {
                onClickTablet.invoke(item)
            } else {
                onClickPhone.invoke(item)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup,
                   quotaParentWidth: Int,
                   isTablet: Boolean,
                   onClickPhone: (GifEntity) -> Unit,
                   onClickTablet: (GifEntity) -> Unit): GifViewHolder {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.giphy, parent, false)
            return GifViewHolder(view,parent.width / quotaParentWidth, isTablet, onClickPhone, onClickTablet)
        }
    }
}