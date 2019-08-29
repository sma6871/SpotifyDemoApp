package com.spotify.demo.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spotify.demo.R
import com.spotify.demo.constants.BundleExtraKeys
import com.spotify.demo.data.models.MovieItem
import com.spotify.demo.extensions.doAfterQueryTextChange
import com.spotify.demo.extensions.plusAssign
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

        if (savedInstanceState == null) {
            viewModel.initMovies()
        }
    }

    private fun initUi() {
        initMoviesList()
        initSearchView()
    }

    private fun observeState() {
        bag += viewModel.listActivityState.subscribe {
            when (it) {
                Init -> showLoading(false)
                is Loading -> showLoading(true, it.isMore)
                is SuccessLoading -> showList(it.movies)
                is ErrorLoading -> showError(it.message)
                is SuccessLoadingMore -> appendList(it.movies)
            }
        }
    }

    private fun initMoviesList() {
        adapter = ArtistListAdapter { movieData ->
            // Convert data to json and pass to details activity
            // As a better way, we should cache downloaded data into a persistent storage in list activity
            // and just pass movie id to details activity and then load info from storage by that id
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(BundleExtraKeys.MovieData, movieData)
            startActivity(intent)
        }
        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        listArtists.layoutManager = linearLayoutManager
        listArtists.adapter = adapter

    }

    private fun initSearchView() {
        searchView.doAfterQueryTextChange { query ->
            viewModel.searchArtists(query)
            return@doAfterQueryTextChange false
        }
    }


    private fun showError(errorMessage: String) {
        showLoading(false)
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

    private fun showList(movies: List<MovieItem>) {
        listArtists.clearOnScrollListeners()
        showLoading(false)
        adapter?.updateData(movies)
        listArtists.smoothScrollToPosition(0)
        listArtists.addOnScrollListener(object :
            EndlessRecyclerOnScrollListener(listArtists.layoutManager as LinearLayoutManager) {
            override fun onLoadMore(current_page: Int) {
                viewModel.loadMore(current_page)
            }

        })
    }

    private fun appendList(movies: List<MovieItem>) {
        showLoading(false, true)
        adapter?.appendData(movies)
    }

    private fun showLoading(isShow: Boolean, isMore: Boolean = false) {
        loading.showHide(isShow)
        if (!isMore)
            listArtists.showHide(!isShow)
    }

}
