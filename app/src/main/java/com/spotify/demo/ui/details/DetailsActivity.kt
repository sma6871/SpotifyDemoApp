package com.spotify.demo.ui.details

import android.os.Bundle
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson
import com.spotify.demo.R
import com.spotify.demo.constants.ApplicationConstants
import com.spotify.demo.constants.BundleExtraKeys
import com.spotify.demo.data.models.ArtistItem
import com.spotify.demo.extensions.GlideScaleType
import com.spotify.demo.extensions.fromJson
import com.spotify.demo.extensions.loadImageWithGlide
import com.spotify.demo.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_details.*


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
            val movie: ArtistItem? =
                Gson().fromJson(it.getStringExtra(BundleExtraKeys.MovieData) ?: "")
            movie?.let { item ->
                txtName.text = item.name
                txtGenre.text = item.genres?.joinToString()
                txtFollowers.text = getString(R.string.followers).format(item.followers?.total)
                if (item.images?.isNotEmpty() == true)
                    imgCover.loadImageWithGlide(
                        item.images[0].url ?: "",
                        diskCacheStrategy = DiskCacheStrategy.ALL,
                        scaleType = GlideScaleType.CenterCrop
                    )

            }


        }
    }
}
