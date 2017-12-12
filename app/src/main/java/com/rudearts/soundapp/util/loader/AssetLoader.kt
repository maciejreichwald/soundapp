package com.rudearts.soundapp.util.loader

import android.content.Context
import android.content.ContextWrapper
import io.reactivex.Single
import io.reactivex.SingleEmitter

class AssetLoader(base: Context) : ContextWrapper(base) {

    fun loadAsset(path: String): Single<String>
            = Single.create({ subscriber -> loadAssetFromFile(path, subscriber) })

    private fun loadAssetFromFile(path:String, subscriber: SingleEmitter<String>) = assets.open(path).use {
        it.reader().use { reader ->
            subscriber.onSuccess(reader.readText())
        }
    }


}