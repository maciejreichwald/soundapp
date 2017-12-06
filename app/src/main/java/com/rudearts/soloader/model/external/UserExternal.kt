package com.rudearts.soloader.model.external


data class UserExternal(
        val reputation:Int,
        val user_id:Int,
        val user_type:String?,
        val accept_rate:Int,
        val profile_image:String?,
        val display_name:String?,
        val link:String?)