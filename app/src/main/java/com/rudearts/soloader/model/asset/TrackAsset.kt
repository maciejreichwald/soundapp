package com.rudearts.soloader.model.asset

import com.google.gson.annotations.SerializedName

data class TrackAsset (
        @SerializedName("Song Clean")
        val name:String?,
        @SerializedName("ARTIST CLEAN")
        val artist:String?,
        @SerializedName("Release Year")
        val releaseDate:String?
)