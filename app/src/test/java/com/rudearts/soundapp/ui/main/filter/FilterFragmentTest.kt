package com.rudearts.soundapp.ui.main.filter

import android.view.View
import com.nhaarman.mockitokotlin2.*
import com.rudearts.soundapp.TestApplication
import com.rudearts.soundapp.extentions.visible
import com.rudearts.soundapp.model.filter.SearchType
import com.rudearts.soundapp.model.filter.SortType
import com.rudearts.soundapp.model.filter.SourceType
import com.rudearts.soundapp.model.filter.TrackFilter
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import space.traversal.kapsule.inject

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class, manifest = Config.NONE)
class FilterFragmentTest {

    private lateinit var filterFragment:FilterFragment

    @Before
    fun setup() {
        filterFragment = Mockito.spy(FilterFragment())
        filterFragment.performInject(RuntimeEnvironment.application)
    }

    @Test
    fun hide_WithView() {
        val view = mock<View> {}
        whenever(filterFragment.view).thenReturn(view)

        filterFragment.hide()

        verify(filterFragment.view)?.visible = false
    }

}