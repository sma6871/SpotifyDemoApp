package com.spotify.demo.ui.details

import android.os.Bundle
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson
import com.spotify.demo.R
import com.spotify.demo.constants.ApplicationConstants
import com.spotify.demo.constants.BundleExtraKeys
import com.spotify.demo.data.models.MovieItem
import com.spotify.demo.extensions.GlideScaleType
import com.spotify.demo.extensions.fromJson
import com.spotify.demo.extensions.loadImageWithGlide
import com.spotify.demo.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.list_item.txtTitle

class DetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
    }

    override fun onStart() {
        super.onStart()
        loadInfo()
    }

    /**
     * This method loads movie info from intent
     * */
    private fun loadInfo() {
        intent?.let {
            val movie: MovieItem? =
                Gson().fromJson(it.getStringExtra(BundleExtraKeys.MovieData) ?: "")
            movie?.let { item ->
                txtTitle.text = item.title
                txtDescription.text = item.overview
                if (!item.backdropPath.isNullOrBlank())
                    imgBackDrop.loadImageWithGlide(
                        ApplicationConstants.BACKDROP_BASE_URL + item.backdropPath,
                        diskCacheStrategy = DiskCacheStrategy.ALL,
                        scaleType = GlideScaleType.CenterCrop
                    )
                if (!item.posterPath.isNullOrBlank())
                    imgPoster.loadImageWithGlide(
                        ApplicationConstants.POSTER_BASE_URL + item.posterPath,
                        diskCacheStrategy = DiskCacheStrategy.ALL,
                        scaleType = GlideScaleType.CenterCrop
                    )
            }


        }
    }
}
