package com.rudearts.soundapp.model.external.response

import com.rudearts.soundapp.model.external.TrackRest

data class SearchResponse (
        val resultCount: Int,
        val results: List<TrackRest>?
)