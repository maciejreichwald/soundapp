package com.rudearts.soundapp.model.external

import com.google.gson.annotations.SerializedName

data class TrackAsset (
        @SerializedName("Song Clean")
        val name:String?,
        @SerializedName("ARTIST CLEAN")
        val artist:String?,
        @SerializedName("Release Year")
        val releaseDate:TrackNumber?
)