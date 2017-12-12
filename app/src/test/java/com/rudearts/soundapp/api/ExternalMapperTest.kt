package com.rudearts.soundapp.api

import com.nhaarman.mockitokotlin2.*
import com.rudearts.soundapp.SongApplication
import com.rudearts.soundapp.TestApplication
import com.rudearts.soundapp.di.BasicModule
import com.rudearts.soundapp.model.external.TrackAsset
import com.rudearts.soundapp.model.external.TrackNumber
import com.rudearts.soundapp.model.external.TrackRest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import space.traversal.kapsule.Kapsule
import java.util.*

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class, manifest = Config.NONE)
class ExternalMapperTest {

    private lateinit var mapper:ExternalMapper

    @Before
    fun setup() {
        mapper = Mockito.spy(ExternalMapper(RuntimeEnvironment.application))
    }

    @Test
    fun track2local_WithRest() {
        val track = mock<TrackRest> {
            on { releaseDate } doReturn "nodate"
        }
        whenever(mapper.dateUtil.string2date("nodate")).thenReturn(Date())

        mapper.track2local(track)

        verify(mapper.dateUtil).string2date("nodate")
        verify(mapper, times(2)).text2unknown(anyOrNull())
    }

    @Test
    fun track2local_WithAsset() {
        val track = mock<TrackAsset> {
            on { releaseDate } doReturn TrackNumber(12)
        }
        whenever(mapper.dateUtil.year2date(12)).thenReturn(Date())

        mapper.track2local(track)

        verify(mapper.dateUtil).year2date(12)
        verify(mapper, times(2)).text2unknown(anyOrNull())
    }
}