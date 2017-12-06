package com.rudearts.soloader.model.local

data class Question(
        val tags: List<String>,
        val answered: Boolean,
        val views: Int,
        val answers: Int,
        val score: Int,
        val lastActivityDate: Long,
        val creationDate: Long,
        val id: Long,
        val link: String?,
        val title:String,
        val owner: User
)