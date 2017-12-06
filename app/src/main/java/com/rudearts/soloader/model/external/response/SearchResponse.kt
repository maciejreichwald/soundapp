package com.rudearts.soloader.model.external.response

import com.rudearts.soloader.model.external.QuestionExternal

data class SearchResponse (
        val items: List<QuestionExternal>?,
        val has_more: Boolean,
        val quota_max: Int,
        val quota_remaining: Int
)