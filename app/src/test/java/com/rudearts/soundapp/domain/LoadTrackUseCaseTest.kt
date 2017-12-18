package com.rudearts.soundapp.domain

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.rudearts.soundapp.model.filter.SourceType
import com.rudearts.soundapp.model.filter.TrackFilter
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit


class LoadTrackUseCaseTest {

    @Rule
    @JvmField
    val mockitoRule = MockitoJUnit.rule()

    @Mock lateinit var loadRest:TrackLoadable.Rest
    @Mock lateinit var loadAsset:TrackLoadable.Asset

    @InjectMocks lateinit var loader:LoadTracksUseCase

    @Test
    fun loadTracks_WithRest() {
        val filter = TrackFilter("", SourceType.REST)
        whenever(loadAsset.loadTracks(filter)).thenReturn(Single.create({}))
        whenever(loadRest.loadTracks(filter)).thenReturn(Single.create({}))

        loader.loadTracks(filter)

        verify(loadAsset).loadTracks(filter)
        verify(loadRest).loadTracks(filter)
    }
}
