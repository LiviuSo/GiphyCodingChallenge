package com.example.giphycodingchallenge.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.giphycodingchallenge.R
import com.example.giphycodingchallenge.model.GifTest
import kotlinx.android.synthetic.main.fragment_detail.view.*

private const val ARG_ITEM = "item"

/**
 * Gif details
 */
class GifDetailFragment : Fragment() {
    private var param: GifTest? = null
//    private var listener: OnFragmentInteractionListener? = null

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
            testTx.text = param?.title ?: ""
            this@GifDetailFragment.activity?.let {
                Glide
                    .with(it)
                    .load(param?.url)
                    .into(imageDetails)
            }
        }
    }

//    fun onButtonPressed(uri: Uri) {
//        listener?.onFragmentInteraction(uri)
//    }
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException("$context must implement OnFragmentInteractionListener")
//        }
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        listener = null
//    }
//
//
//    interface OnFragmentInteractionListener {
//        fun onFragmentInteraction(uri: Uri)
//    }

    companion object {

        @JvmStatic
        fun instance(item: GifTest?) =
            GifDetailFragment().apply {
                arguments = Bundle().apply {
                    retainInstance = true
                    putParcelable(ARG_ITEM, item)
                }
            }
    }
}
