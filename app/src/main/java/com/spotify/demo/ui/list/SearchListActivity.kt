package com.spotify.demo.ui.list

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spotify.demo.R
import com.spotify.demo.constants.BundleExtraKeys
import com.spotify.demo.data.models.ArtistItem
import com.spotify.demo.extensions.doAfterQueryTextChange
import com.spotify.demo.extensions.plusAssign
import com.spotify.demo.extensions.search
import com.spotify.demo.extensions.showHide
import com.spotify.demo.ui.base.BaseActivity
import com.spotify.demo.ui.details.DetailsActivity
import com.spotify.demo.utils.EndlessRecyclerOnScrollListener
import kotlinx.android.synthetic.main.activity_search_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchListActivity : BaseActivity() {

    private val viewModel: ListViewModel by viewModel()
    private var adapter: ArtistListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_list)

        observeState()
        initUi()

    }

    private fun initUi() {
        initMoviesList()
        initSearchView()
    }

    private fun observeState() {
        bag += viewModel.listActivityState.subscribe {
            when (it) {
                Init -> showLoading(false)
                is Loading -> showLoading(true)
                is SuccessLoading -> showList(it.artists)
                is ErrorLoading -> showError(it.message)
            }
        }
    }

    private fun initMoviesList() {
        adapter = ArtistListAdapter { artistData, imageView ->
            // Convert data to json and pass to details activity
            // As a better way, we should cache downloaded data into a persistent storage in list activity
            // and just pass movie id to details activity and then load info from storage by that id

            val activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                imageView,
                getString(R.string.imgtransition)
            )
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(BundleExtraKeys.MovieData, artistData)
            startActivity(intent, activityOptionsCompat.toBundle())
        }
        val linearLayoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        listArtists.layoutManager = linearLayoutManager
        listArtists.adapter = adapter

    }

    private fun initSearchView() {
        //use this method to delay search during type query
        searchView.search { query ->
            viewModel.searchArtists(query)
        }
    }


    private fun showError(errorMessage: String) {
        showLoading(false)
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

    private fun showList(movies: List<ArtistItem>) {
        showLoading(false)
        adapter?.updateData(movies)

    }

    private fun showLoading(isShow: Boolean) {
        loading.showHide(isShow)
        listArtists.showHide(!isShow)
    }

}
