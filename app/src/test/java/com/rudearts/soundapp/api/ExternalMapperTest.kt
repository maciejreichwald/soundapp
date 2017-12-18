package com.rudearts.soundapp.api

import android.content.Context
import com.nhaarman.mockitokotlin2.*
import com.rudearts.soundapp.model.external.TrackAsset
import com.rudearts.soundapp.model.external.TrackNumber
import com.rudearts.soundapp.model.external.TrackRest
import com.rudearts.soundapp.util.DateUtil
import com.rudearts.soundapp.util.loader.ExternalMapper
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.junit.MockitoJUnit
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class ExternalMapperTest {

    @Rule
    @JvmField
    val mockitoRule = MockitoJUnit.rule()

    @Mock lateinit var context:Context

    @Mock lateinit var dateUtil:DateUtil

    @InjectMocks @Spy lateinit var mapper: ExternalMapper

    @Test
    fun track2local_WithRest() {
        val track = mock<TrackRest> {
            on { trackName } doReturn "none"
            on { artistName } doReturn "none"
            on { releaseDate } doReturn "nodate"
        }
        whenever(dateUtil.string2date("nodate")).thenReturn(Date())


        mapper.track2local(track)

        verify(dateUtil).string2date("nodate")
        verify(mapper, times(2)).text2unknown(anyOrNull())
    }

    @Test
    fun track2local_WithAsset() {
        val track = mock<TrackAsset> {
            on { name } doReturn "none"
            on { artist } doReturn "none"
            on { releaseDate } doReturn TrackNumber(12)
        }
        whenever(dateUtil.year2date(eq(12))).thenReturn(Date())

        mapper.track2local(track)

        verify(dateUtil).year2date(12)
        verify(mapper, times(2)).text2unknown(anyOrNull())
    }
}