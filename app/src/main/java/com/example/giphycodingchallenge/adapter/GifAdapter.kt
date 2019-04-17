package com.example.giphycodingchallenge.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.giphycodingchallenge.R
import com.example.giphycodingchallenge.model.GifTest
import kotlinx.android.synthetic.main.giphy.view.*


class GifAdapter(
    private val context: Context,
    private val data: ArrayList<GifTest>,
    private val isTablet: Boolean,
    val onClickPhone: (GifTest) -> Unit,
    val onClickTablet: (GifTest) -> Unit) : RecyclerView.Adapter<GifAdapter.ImageHolder>() {

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        val item = data[position]
        holder.inject(item, context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        return ImageHolder(View.inflate(context, R.layout.giphy, null))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ImageHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        fun inject(item: GifTest, context: Context) {
            val imageView = v.image as ImageView
            Glide.with(context)
                .load(item.url)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error)
                .into(imageView)
            v.name.text = item.title
            v.setOnClickListener {
                if (isTablet) {
                    onClickTablet(item)
                } else {
                    onClickPhone(item)
                }
            }
        }
    }
}