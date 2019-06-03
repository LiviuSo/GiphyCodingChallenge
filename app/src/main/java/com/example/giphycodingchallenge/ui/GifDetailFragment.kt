package com.example.giphycodingchallenge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.giphycodingchallenge.R
import com.example.giphycodingchallenge.db.GifEntity
import kotlinx.android.synthetic.main.fragment_detail.view.*

private const val ARG_ITEM = "item"

/**
 * Gif details
 */
class GifDetailFragment : Fragment() {
    private var param: GifEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param = it.getParcelable(ARG_ITEM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false).apply {
            if(param != null) {
                testTx.text = param?.title
                this@GifDetailFragment.activity?.let {
                    Glide
                        .with(it)
                        .load(param?.url)
                        .into(imageDetails)
                }
            }
        }
    }

    companion object {

        @JvmStatic
        fun instance(item: GifEntity?) =
            GifDetailFragment().apply {
                arguments = Bundle().apply {
                    retainInstance = true
                    putParcelable(ARG_ITEM, item)
                }
            }
    }
}
