package com.rudearts.soloader.api

import android.content.Context
import android.content.ContextWrapper
import com.rudearts.soloader.R
import com.rudearts.soloader.model.external.QuestionExternal
import com.rudearts.soloader.model.external.UserExternal
import com.rudearts.soloader.model.local.Question
import com.rudearts.soloader.model.local.User
import com.rudearts.soloader.model.local.UserType

class ExternalMapper(base:Context) : ContextWrapper(base) {

    fun question2local(question:QuestionExternal) = with(question) {
        Question(tags ?: ArrayList(), is_answered, view_count, answer_count, score, last_activity_date, creation_date, question_id, link, title ?: getString(R.string.unknown), user2local(owner))
    }

    fun user2local(user:UserExternal) = with(user) {
        User(reputation, user_id, userType2local(user_type ?: ""), accept_rate, profile_image, display_name ?: getString(R.string.unknown), link)
    }

    private fun userType2local(type:String) = when(type) {
        UserType.REGISTERED.type -> UserType.REGISTERED
        else -> UserType.GUEST
    }

}