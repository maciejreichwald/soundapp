package com.rudearts.soloader.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.Animation
import com.rudearts.soloader.R
import com.rudearts.soloader.extentions.bind
import com.rudearts.soloader.extentions.visible
import com.rudearts.soloader.model.filter.SearchType
import com.rudearts.soloader.model.filter.SortType
import com.rudearts.soloader.model.filter.SourceType
import com.rudearts.soloader.model.filter.TrackFilter
import com.rudearts.soloader.model.local.Track
import com.rudearts.soloader.ui.ToolbarActivity
import com.rudearts.soloader.ui.details.DetailsActivity
import com.rudearts.soloader.util.animation.BaseAnimationListener
import com.rudearts.soloader.util.animation.CircularRevealAnimator
import com.rudearts.soloader.util.animation.RotatedFadeAnimator


class MainActivity : ToolbarActivity(), MainContract.View {

    private val refreshLayout: SwipeRefreshLayout by bind(R.id.swipe_container)
    private val progressBar: View by bind(R.id.progress_bar)
    private val listItems: RecyclerView by bind(R.id.items_list)
    private val emptyView: View by bind(R.id.empty_view)
    private val btnFilterPrimary: FloatingActionButton by bind(R.id.primary_filter_btn)
    private val btnFilterSecondary: FloatingActionButton by bind(R.id.secondary_filter_btn)
    private val filterBackground: View by bind(R.id.filter_background)

    private val circularAnimator = CircularRevealAnimator(this)
    private val fadeAnimator = RotatedFadeAnimator(this)

    private val presenter: MainContract.Presenter = MainPresenter(this, this)
    private lateinit var adapter: TrackAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(getString(R.string.so_loader))
        setup()
    }

    private fun setup() {
        showSearchView()
        setupFilterRelatedViews()
        setupList()
        setupRefresh()
        setupSearchView()
        loadItems()
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
            return super.onQueryTextSubmit(query)
        }
    }

    private fun loadItems() {
        presenter.loadTracks(TrackFilter(searchView.query.toString(), SearchType.SONG, SourceType.BOTH, SortType.SONG))
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

    override fun onBackPressed() = with(searchView) {
        when (isIconified) {
            true -> super.onBackPressed()
            false -> isIconified = true
        }
    }

    private fun setupFilterRelatedViews() {
        btnFilterPrimary.setOnClickListener { animateFilterRelatedViews(true) }
        btnFilterSecondary.setOnClickListener { animateFilterRelatedViews(false) }

        filterBackground.setOnClickListener { animateFilterRelatedViews(false) }
        filterBackground.visible = false
    }

    private fun animateFilterRelatedViews(animateOut: Boolean) {
        if (fadeAnimator.hasStartedAnyFade()) return

        btnFilterPrimary.fade(animateOut)
        btnFilterSecondary.fade(!animateOut)

        animateFilterBackground(animateOut)
    }

    private fun animateFilterBackground(animateOut: Boolean) =  when (animateOut) {
        true -> circularAnimator.circuralReveal(filterBackground, btnFilterPrimary, coordinatorLayout)
        false -> circularAnimator.circuralHide(filterBackground, btnFilterPrimary, coordinatorLayout)
    }

    private fun FloatingActionButton.fade(animateOut: Boolean) = when (animateOut) {
        true -> fadeAnimator.startFadeOut(this, createFloatingButtonAnimationListener(this, animateOut))
        false -> fadeAnimator.startFadeIn(this, createFloatingButtonAnimationListener(this, !animateOut))
    }

    private fun createFloatingButtonAnimationListener(btn: View, animateIn: Boolean) = object : BaseAnimationListener() {
        override fun onAnimationEnd(animation: Animation?) {
            btn.visible = animateIn
        }
    }

}
