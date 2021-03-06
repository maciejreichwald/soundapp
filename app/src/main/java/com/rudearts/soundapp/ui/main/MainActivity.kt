package com.rudearts.soundapp.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.rudearts.soundapp.R
import com.rudearts.soundapp.SongApplication
import com.rudearts.soundapp.di.BasicModule
import com.rudearts.soundapp.extentions.bind
import com.rudearts.soundapp.extentions.bindFragment
import com.rudearts.soundapp.extentions.visible
import com.rudearts.soundapp.model.local.Track
import com.rudearts.soundapp.ui.ToolbarActivity
import com.rudearts.soundapp.ui.details.DetailsActivity
import com.rudearts.soundapp.ui.main.filter.FilterContract
import com.rudearts.soundapp.ui.main.filter.FilterFragment
import com.rudearts.soundapp.util.animation.CircularRevealAnimator
import com.rudearts.soundapp.util.animation.RotatedFadeAnimator
import space.traversal.kapsule.Injects
import space.traversal.kapsule.inject
import space.traversal.kapsule.required


class MainActivity : ToolbarActivity(), MainContract.View, Injects<BasicModule> {

    private val refreshLayout: SwipeRefreshLayout by bind(R.id.swipe_container)
    private val progressBar: View by bind(R.id.progress_bar)
    private val listItems: RecyclerView by bind(R.id.items_list)
    private val emptyView: View by bind(R.id.empty_view)
    private val btnFilterPrimary: FloatingActionButton by bind(R.id.primary_filter_btn)
    private val btnFilterSecondary: FloatingActionButton by bind(R.id.secondary_filter_btn)
    private val filterBackground: View by bind(R.id.filter_background)
    private val filterView:FilterContract.View by bindFragment<FilterFragment>(R.id.filter_view)

    internal val circularAnimator by required { circularAnimator }
    internal val fadeAnimator by required { fadeAnimator }

    private lateinit var presenter: MainContract.Presenter
    private lateinit var adapter: TrackAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(SongApplication.module(this))

        setTitle(getString(R.string.so_loader))
        setup()
    }

    private fun setup() {
        setupPresenter()
        showSearchView()
        setupFilterRelatedViews()
        setupList()
        setupRefresh()
        setupSearchView()
        loadItems()
    }

    private fun setupPresenter() {
        presenter = MainPresenter(this, this)
    }

    private fun setupRefresh() = refreshLayout.setOnRefreshListener {
        loadItems()
        refreshLayout.isRefreshing = false
    }

    private fun setupSearchView() = with(searchView) {
        setOnQueryTextListener(createOnQueryTextListener())
        setOnCloseListener {
            loadItems()
            false
        }
    }

    private fun createOnQueryTextListener() = object : OnQueryTextListener() {
        override fun onQueryTextSubmit(query: String?): Boolean {
            loadItems()
            if (filterView.isShown()) animateHideFilter()
            return super.onQueryTextSubmit(query)
        }
    }

    private fun loadItems() {
        val filter = filterView.getFilter(searchView.query.toString())
        presenter.loadTracks(filter)
    }

    private fun setupList() {
        adapter = TrackAdapter(this, ArrayList(), { onQuestionClick(it) })
        listItems.adapter = adapter
        listItems.layoutManager = LinearLayoutManager(this)
    }

    override fun provideSubContentViewId() = R.layout.activity_main

    override fun updateLoadingState(isLoading: Boolean) {
        progressBar.visible = isLoading
        listItems.visible = !isLoading

        if (isLoading) emptyView.visible = false
    }

    override fun updateTracks(tracks: List<Track>) {
        adapter.updateItems(tracks)
        emptyView.visible = tracks.isEmpty()
    }

    override fun showMessage(message: String) {
        showSnackMessage(message)
    }

    private fun onQuestionClick(track: Track) {
        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra(DetailsActivity.TITLE, track.name)
            putExtra(DetailsActivity.LINK, track.trackPreviewUrl)
        }
        startActivity(intent)
    }

    override fun onBackPressed() = when(filterView.isShown()) {
        true -> animateHideFilter()
        else -> onBackPressedWithSearchView()
    }

    private fun onBackPressedWithSearchView() = with(searchView) {
        when (isIconified) {
            true -> super.onBackPressed()
            false -> isIconified = true
        }
    }

    private fun setupFilterRelatedViews() {
        btnFilterPrimary.setOnClickListener { animateShowFilter() }
        btnFilterSecondary.setOnClickListener { onSecondaryClick() }

        filterBackground.setOnClickListener { onFilterBackgroundClick() }
        filterBackground.visible = false

        filterView.hide()
    }

    private fun onFilterBackgroundClick() {
        filterView.restoreFilter()
        animateHideFilter()
    }

    private fun onSecondaryClick() {
        animateHideFilter()
        hideKeyboard()
        hideSearchQueryIfEmpty()

        if(hasFilterChanged()) loadItems()
    }

    private fun hasFilterChanged() = filterView.hasFilterChanged(searchView.query.toString())

    private fun hideSearchQueryIfEmpty() = with(searchView) {
        if (isIconified or !TextUtils.isEmpty(query)) return

        isIconified = true
    }

    private fun animateShowFilter() = animateFilterRelatedViews(true)

    private fun animateHideFilter() = animateFilterRelatedViews(false)

    private fun animateFilterRelatedViews(showFilter: Boolean) {
        if (fadeAnimator.hasStartedAnyFade()) return

        btnFilterPrimary.fade(showFilter)
        btnFilterSecondary.fade(!showFilter)

        animateFilterBackground(showFilter)
        animateFilterView(showFilter)
    }

    private fun animateFilterView(showFilter: Boolean) = when(showFilter) {
        true -> filterView.activatedAnimated(searchView.query.toString())
        false -> filterView.hideAnimated()
    }

    private fun animateFilterBackground(showFilter: Boolean) =  when (showFilter) {
        true -> circularAnimator.circularReveal(filterBackground, btnFilterPrimary, coordinatorLayout)
        false -> circularAnimator.circularHide(filterBackground, btnFilterPrimary, coordinatorLayout)
    }

    private fun FloatingActionButton.fade(showFilter: Boolean) = when (showFilter) {
        true -> fadeAnimator.startFadeOut(this)
        false -> fadeAnimator.startFadeIn(this)
    }

    private fun hideKeyboard() = currentFocus?.let {
        val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(it.windowToken, 0)
    }

}
