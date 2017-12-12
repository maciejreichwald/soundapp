package com.rudearts.soundapp.controller

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class AssetControllerTest {

    private lateinit var assetController:AssetController

    @Before
    fun setup() {
        assetController = AssetController.instance
    }

    @Test
    fun areTracksLoaded_WithDefaultMarker() {
        val result = assetController.areTracksLoaded("TracksAreLoaded")
        assertEquals(true, result)
    }

    @Test
    fun areTracksLoaded_WithDifferentMarker() {
        val result = assetController.areTracksLoaded("SomethingElse")
        assertEquals(false, result)
    }

    @Test
    fun getTracksLoadedMarker_givesDefaultMarker() {
        val result = assetController.getTracksLoadedMarker()
        assertEquals("TracksAreLoaded", result)
    }
}