package com.rudearts.soundapp.domain

import android.text.TextUtils
import com.rudearts.soundapp.api.RestAPI
import com.rudearts.soundapp.model.external.response.SearchResponse
import com.rudearts.soundapp.model.filter.SourceType
import com.rudearts.soundapp.model.filter.TrackFilter
import com.rudearts.soundapp.model.local.Track
import com.rudearts.soundapp.util.loader.ExternalMapper
import io.reactivex.Single
import io.reactivex.SingleEmitter
import retrofit2.Response
import javax.inject.Inject

class LoadTracksFromRest @Inject constructor(
        internal val restApi: RestAPI,
        internal val mapper:ExternalMapper) : TrackLoadable.Rest {

    override fun loadTracks(filter: TrackFilter): Single<List<Track>> = Single.create { subscriber ->
        val query = emptyQuery2Default(filter.query)
        when(filter.source) {
            SourceType.ASSET -> subscriber.onSuccess(emptyList())
            SourceType.REST,
            SourceType.BOTH -> restApi.search(query)
                    .subscribe({ response -> onTracksLoaded(response, subscriber) },
                            {error -> subscriber.onError(error)})
        }
    }

    internal fun onTracksLoaded(response: Response<SearchResponse>, subscriber: SingleEmitter<List<Track>>) {
        if (!response.isSuccessful) {
            subscriber.onSuccess(emptyList())
            return
        }

        val itemsExternal = response.body()?.results ?: ArrayList()
        val items = itemsExternal.map { mapper.track2local(it) }
        subscriber.onSuccess(items)
    }

    internal fun emptyQuery2Default(query: String) = when(TextUtils.isEmpty(query)) {
        true -> LoadTracksUseCase.DEFAULT_SEARCH_SIGN
        false -> query
    }
}