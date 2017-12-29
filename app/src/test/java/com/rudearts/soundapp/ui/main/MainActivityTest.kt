package com.rudearts.soundapp.ui.main

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.rudearts.soundapp.extentions.visible
import com.rudearts.soundapp.model.LoadingState
import com.rudearts.soundapp.model.local.Track
import com.rudearts.soundapp.ui.main.adapters.FilterAdapter
import com.rudearts.soundapp.ui.main.adapters.TrackAdapter
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.junit.MockitoJUnit

class MainActivityTest {

    @Rule
    @JvmField
    val mockitoRule = MockitoJUnit.rule()

    @Mock lateinit var presenter: MainContract.Presenter

    @Mock lateinit var trackAdapter: TrackAdapter

    @Mock lateinit var filterAdapter: FilterAdapter

    @InjectMocks @Spy lateinit var activity:MainActivityMock

    private val refresh = mock<SwipeRefreshLayout> {}
    private val progress = mock<View> {}
    private val listItems = mock<RecyclerView> {}
    private val emptyView = mock<View> {}

    @Before
    fun setup() {
        whenever(activity.refreshLayout).thenReturn(refresh)
        whenever(activity.progressBar).thenReturn(progress)
        whenever(activity.listItems).thenReturn(listItems)
        whenever(activity.emptyView).thenReturn(emptyView)
    }

    @Test
    fun onRefresh() {
        activity.onRefresh()

        verify(presenter, times(1)).loadTracks()
        verify(refresh, times(1)).isRefreshing = false
    }

    @Test
    fun updateTracks() {
        val items:List<Track> = mock {}
        activity.updateTracks(items)

        verify(trackAdapter, times(1)).updateItems(items)
    }

    @Test
    fun updateLoadingState_WhenLoading() {
        activity.updateLoadingState(LoadingState.LOADING)

        verify(progress, times(1)).visible = true
        verify(emptyView, times(1)).visible = false
        verify(listItems, times(1)).visible = false
    }

    @Test
    fun updateLoadingState_WhenNoResults() {
        activity.updateLoadingState(LoadingState.NO_RESULTS)

        verify(progress, times(1)).visible = false
        verify(emptyView, times(1)).visible = true
        verify(listItems, times(1)).visible = false
    }

    @Test
    fun updateLoadingState_WhenShowResults() {
        activity.updateLoadingState(LoadingState.SHOW_RESULTS)

        verify(progress, times(1)).visible = false
        verify(emptyView, times(1)).visible = false
        verify(listItems, times(1)).visible = true
    }

    /**
     * Ok, I gave up here - I had some problems with mocking activity
     * it was holiday, it was late and this "temporary" fix worked like a charm...
     */
    class MainActivityMock : MainActivity() {
        override fun <T : View?> findViewById(id: Int): T {
            return null as T
        }
    }
}