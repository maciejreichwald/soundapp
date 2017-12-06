package com.rudearts.soloader.model.local

data class User(
        val reputation:Int,
        val id:Int,
        val type:UserType,
        val acceptRate:Int,
        val profileImage:String?,
        val displayName:String,
        val link:String?)