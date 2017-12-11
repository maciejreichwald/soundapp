package com.rudearts.soloader.model.filter


data class TrackFilter(
        val query: String,
        val type: SearchType,
        val source: SourceType,
        val sort: SortType)