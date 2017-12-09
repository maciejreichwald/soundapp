package com.rudearts.soloader.util.loader

import android.content.Context
import android.content.ContextWrapper
import io.reactivex.Single

class AssetLoader(base:Context) : ContextWrapper(base) {

    fun loadAsset(path:String):Single<String>
            = Single.create({ subscriber ->
        assets.open(path).use {
            it.reader().use { reader ->
                subscriber.onSuccess(reader.readText())
            }
        }
    })

}