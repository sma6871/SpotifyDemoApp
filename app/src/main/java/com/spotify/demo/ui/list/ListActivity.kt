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
import com.spotify.demo.extensions.plusAssign
import com.spotify.demo.extensions.showHide
import com.spotify.demo.ui.base.BaseActivity
import com.spotify.demo.ui.details.DetailsActivity
import com.spotify.demo.utils.EndlessRecyclerOnScrollListener
import kotlinx.android.synthetic.main.activity_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ListActivity : BaseActivity() {

    private val viewModel: ListViewModel by viewModel()
    private var adapter: MovieListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        observeState()
        initUi()

        if (savedInstanceState == null) {
            viewModel.initMovies()
        }
    }

    private fun initUi() {
        initMoviesList()
        initYearsSpinner()
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
        adapter = MovieListAdapter { movieData ->
            // Convert data to json and pass to details activity
            // As a better way, we should cache downloaded data into a persistent storage in list activity
            // and just pass movie id to details activity and then load info from storage by that id
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(BundleExtraKeys.MovieData, movieData)
            startActivity(intent)
        }
        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        listMovies.layoutManager = linearLayoutManager
        listMovies.adapter = adapter

    }

    private fun initYearsSpinner() {
        val years = listOf("All") + (2019 downTo 1990).toList().map { it.toString() }
        val dataAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, years
        )
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        yearsSpinner.adapter = dataAdapter

        yearsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, pos: Int, id: Long) {
                val year = parent?.getItemAtPosition(pos).toString().toIntOrNull()
                viewModel.initMovies(year ?: -1)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }


    private fun showError(errorMessage: String) {
        showLoading(false)
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

    private fun showList(movies: List<MovieItem>) {
        listMovies.clearOnScrollListeners()
        showLoading(false)
        adapter?.updateData(movies)
        listMovies.smoothScrollToPosition(0)
        listMovies.addOnScrollListener(object :
            EndlessRecyclerOnScrollListener(listMovies.layoutManager as LinearLayoutManager) {
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
        yearsSpinner.showHide(!isShow)
        yearsSpinnerTitle.showHide(!isShow)
        if (!isMore)
            listMovies.showHide(!isShow)
    }

}
