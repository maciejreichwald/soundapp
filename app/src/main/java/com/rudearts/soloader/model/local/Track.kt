package com.rudearts.soloader.model.local

data class Track(
        val name:String,
        val artist:String,
        val releaseDate:Long,
        val smallIcon:String?,
        val bigIcon:String?,
        val previewUrl:String?
)