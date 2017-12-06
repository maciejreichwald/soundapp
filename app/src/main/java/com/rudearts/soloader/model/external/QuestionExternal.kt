package com.rudearts.soloader.model.external


data class QuestionExternal(
        val tags: List<String>?,
        val is_answered: Boolean,
        val view_count: Int,
        val answer_count: Int,
        val score: Int,
        val last_activity_date: Long,
        val creation_date: Long,
        val question_id: Long,
        val link: String?,
        val title:String?,
        val owner: UserExternal
)
