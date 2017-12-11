package com.rudearts.soundapp.model.local

import java.util.*

data class Track(
        val name:String,
        val artist:String,
        val releaseDate:Date,
        val smallIcon:String?=null,
        val bigIcon:String?=null,
        val trackPreviewUrl:String?=null,
        val fileUrl:String?=null
)