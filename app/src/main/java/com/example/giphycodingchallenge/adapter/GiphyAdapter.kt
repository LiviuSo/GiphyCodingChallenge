package com.example.giphycodingchallenge.adapter

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.giphycodingchallenge.R
import com.example.giphycodingchallenge.model.Giphy
import com.example.giphycodingchallenge.ui.DetailActivity
import com.example.giphycodingchallenge.util.Constants.EXTRA_ITEM
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.giphy.view.*


class GiphyAdapter(private val context: Context, private val data: ArrayList<Giphy>)
    : RecyclerView.Adapter<GiphyAdapter.ImageHolder>() {

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        val item = data[position]
        holder.inject(item, context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiphyAdapter.ImageHolder {
        return ImageHolder(View.inflate(context, R.layout.giphy, null))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ImageHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        fun inject(item: Giphy, context: Context) {
            val imageView = v.image as ImageView
            Picasso
                .with(context).isLoggingEnabled = true
            Picasso.with(context)
                .load(item.imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error)
                .into(imageView)
            v.name.text = item.name
            v.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.flags = FLAG_ACTIVITY_NEW_TASK
                intent.putExtra(EXTRA_ITEM, item)
                context.startActivity(intent)
            }
        }
    }
}