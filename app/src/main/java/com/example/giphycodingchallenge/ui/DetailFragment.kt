package com.example.giphycodingchallenge.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.giphycodingchallenge.R
import com.example.giphycodingchallenge.model.Gif
import com.example.giphycodingchallenge.model.GifTest
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail.view.*

private const val ARG_ITEM = "item"

/**
 * Gif details
 */
class DetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param: GifTest? = null
    private var listener: OnFragmentInteractionListener? = null

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
            Picasso
                .with(this@DetailFragment.activity)
                .load(param?.url)
                .into(imageDetails)
        }
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        @JvmStatic
        fun newInstance(item: GifTest?) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_ITEM, item)
                }
            }
    }
}
