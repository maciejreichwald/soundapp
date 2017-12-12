package com.rudearts.soundapp.loader

import com.nhaarman.mockitokotlin2.*
import com.rudearts.soundapp.SongApplication
import com.rudearts.soundapp.TestApplication
import com.rudearts.soundapp.api.RestAPI
import com.rudearts.soundapp.di.BasicModule
import com.rudearts.soundapp.model.filter.SearchType
import com.rudearts.soundapp.model.filter.SortType
import com.rudearts.soundapp.model.filter.SourceType
import com.rudearts.soundapp.model.filter.TrackFilter
import com.rudearts.soundapp.util.loader.TrackLoader
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class, manifest = Config.NONE)
class TrackLoaderTest {

    private val defaultFilter = TrackFilter("", SearchType.SONG, SourceType.REST, SortType.RELEASE_DATE)
    private lateinit var trackLoader:TrackLoader

    @Before
    fun setup() {
        trackLoader = TrackLoader(RuntimeEnvironment.application)
        whenever(trackLoader.restController.restApi).thenReturn(mock {})
    }

    @Test
    fun createAssetRequest_NotCalledWithRest() {
        val filter = defaultFilter.copy()

        trackLoader.createAssetRequest(filter.source)

        verify(trackLoader.assetLoader, never()).loadAsset(any())
    }

    @Test
    fun createAssetRequest_NotCalledWithAsset() {
        val filter = defaultFilter.copy(source = SourceType.ASSET)
        whenever(trackLoader.assetController.loadedTracks).thenReturn(ArrayList())

        trackLoader.createAssetRequest(filter.source)

        verify(trackLoader.assetLoader, never()).loadAsset(any())
    }

    @Test
    fun createAssetRequest_NotCalledWithBoth() {
        val filter = defaultFilter.copy(source = SourceType.BOTH)
        whenever(trackLoader.assetController.loadedTracks).thenReturn(ArrayList())

        trackLoader.createAssetRequest(filter.source)

        verify(trackLoader.assetLoader, never()).loadAsset(any())
    }

    @Test
    fun createAssetRequest_WithAsset() {
        val filter = defaultFilter.copy(source = SourceType.ASSET)
        whenever(trackLoader.assetController.loadedTracks).thenReturn(null)

        trackLoader.createAssetRequest(filter.source)

        verify(trackLoader.assetLoader).loadAsset(any())
    }

    @Test
    fun createAssetRequest_WithBoth() {
        val filter = defaultFilter.copy(source = SourceType.BOTH)
        whenever(trackLoader.assetController.loadedTracks).thenReturn(null)

        trackLoader.createAssetRequest(filter.source)

        verify(trackLoader.assetLoader).loadAsset(any())
    }

    @Test
    fun createSearchRequest_NotCalledWithAsset() {
        val filter = defaultFilter.copy(source = SourceType.ASSET)

        trackLoader.createSearchRequest(filter)

        verify(trackLoader.restController.restApi, never()).searchForSong(anyOrNull())
    }

    @Test
    fun createSearchRequest_WithBothAndSong() {
        val filter = defaultFilter.copy(source = SourceType.BOTH, type = SearchType.SONG)

        trackLoader.createSearchRequest(filter)

        verify(trackLoader.restController.restApi).searchForSong(anyOrNull())
        verify(trackLoader.restController.restApi, never()).searchForArtist(anyOrNull())
    }

    @Test
    fun createSearchRequest_WithRestAndSong() {
        val filter = defaultFilter.copy(source = SourceType.REST, type = SearchType.SONG)

        trackLoader.createSearchRequest(filter)

        verify(trackLoader.restController.restApi).searchForSong(anyOrNull())
        verify(trackLoader.restController.restApi, never()).searchForArtist(anyOrNull())
    }

    @Test
    fun createSearchRequest_WithBothAndArtist() {
        val filter = defaultFilter.copy(source = SourceType.BOTH, type = SearchType.ARTIST)

        trackLoader.createSearchRequest(filter)

        verify(trackLoader.restController.restApi, never()).searchForSong(anyOrNull())
        verify(trackLoader.restController.restApi).searchForArtist(anyOrNull())
    }

    @Test
    fun createSearchRequest_WithRestAndArtist() {
        val filter = defaultFilter.copy(source = SourceType.REST, type = SearchType.ARTIST)

        trackLoader.createSearchRequest(filter)

        verify(trackLoader.restController.restApi, never()).searchForSong(anyOrNull())
        verify(trackLoader.restController.restApi).searchForArtist(anyOrNull())
    }

}
