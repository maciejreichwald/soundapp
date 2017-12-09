package com.rudearts.soloader.model.external.response

import com.rudearts.soloader.model.external.TrackRest

data class SearchResponse (
        val resultCount: Int,
        val results: List<TrackRest>?
)