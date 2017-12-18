package com.rudearts.soundapp.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.rudearts.soundapp.R
import com.rudearts.soundapp.SongApplication
import com.rudearts.soundapp.di.main.DaggerMainComponent
import com.rudearts.soundapp.di.main.MainComponent
import com.rudearts.soundapp.di.main.MainModule
import com.rudearts.soundapp.extentions.bind
import com.rudearts.soundapp.extentions.visible
import com.rudearts.soundapp.model.filter.SourceType
import com.rudearts.soundapp.model.local.Track
import com.rudearts.soundapp.ui.ToolbarActivity
import com.rudearts.soundapp.ui.details.DetailsActivity
import com.rudearts.soundapp.ui.main.adapters.FilterAdapter
import com.rudearts.soundapp.ui.main.adapters.TrackAdapter
import javax.inject.Inject


class MainActivity : ToolbarActivity(), MainContract.View {

    @Inject internal lateinit var presenter: MainContract.Presenter

    internal val refreshLayout: SwipeRefreshLayout by bind(R.id.swipe_container)
    internal val progressBar: View by bind(R.id.progress_bar)
    internal val listItems: RecyclerView by bind(R.id.items_list)
    internal val emptyView: View by bind(R.id.empty_view)
    internal val filterContainer: ViewGroup by bind(R.id.source_filters)

    internal lateinit var mainComponent: MainComponent
    internal lateinit var trackAdapter: TrackAdapter
    internal lateinit var filterAdapater: FilterAdapter

    override fun provideSubContentViewId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    internal fun setup() {
        inject()
        setupTitle()
        setupViews()
        showSearchView()

        presenter.loadTracks()
    }

    internal fun inject() {
        mainComponent = createMainComponent()
        mainComponent.inject(this)
    }

    internal fun createMainComponent() = DaggerMainComponent.builder()
            .appComponent(SongApplication.appComponent)
            .mainModule(MainModule(this))
            .build()

    internal fun setupTitle() {
        title = getString(R.string.so_loader)
    }

    internal fun setupViews() {
        setupList()
        setupRefresh()
        setupSearchView()
        setupFilters()
    }

    internal fun setupList() {
        trackAdapter = TrackAdapter(this, mainComponent, { presenter.onQuestionClick(it) })
        listItems.adapter = trackAdapter
        listItems.layoutManager = LinearLayoutManager(this)
    }

    internal fun setupFilters() {
        filterAdapater = FilterAdapter(this, SourceType.values().asList(), filterContainer, { presenter.onFilterClick() })
    }

    override fun retrieveSearchQuery() = searchView.query.toString()

    override fun retrieveSelectedSourceType() = filterAdapater.retrieveSelectedSourceType()

    internal fun setupRefresh() = refreshLayout.setOnRefreshListener {
        presenter.loadTracks()
        refreshLayout.isRefreshing = false
    }

    internal fun setupSearchView() = with(searchView) {
        setOnQueryTextListener(createOnQueryTextListener())
        setOnCloseListener {
            presenter.loadTracks()
            false
        }
    }

    internal fun createOnQueryTextListener() = object : OnQueryTextListener() {
        override fun onQueryTextSubmit(query: String?): Boolean {
            presenter.loadTracks()
            return super.onQueryTextSubmit(query)
        }
    }

    override fun updateLoadingState(isLoading: Boolean) {
        progressBar.visible = isLoading
        listItems.visible = !isLoading

        if (isLoading) emptyView.visible = false
    }

    override fun updateTracks(tracks: List<Track>) {
        trackAdapter.updateItems(tracks)
        emptyView.visible = tracks.isEmpty()
    }

    override fun showMessage(message: String) {
        showSnackMessage(message)
    }

    override fun startDetailActivity(extras:Bundle) {
        Intent(this, DetailsActivity::class.java).apply {
            putExtras(extras)
            startActivity(this)
        }
    }

    override fun onBackPressed() = with(searchView) {
        when (isIconified) {
            true -> super.onBackPressed()
            false -> isIconified = true
        }
    }

    override fun hideKeyboard() {
        currentFocus?.let {
            val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

}
