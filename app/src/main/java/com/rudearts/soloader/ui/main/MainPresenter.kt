package com.rudearts.soloader.ui.main

import android.content.Context
import android.content.ContextWrapper
import android.text.TextUtils
import com.rudearts.soloader.R
import com.rudearts.soloader.api.ExternalMapper
import com.rudearts.soloader.api.RestController
import com.rudearts.soloader.model.external.response.SearchResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class MainPresenter(base:Context, view:MainContract.View) : ContextWrapper(base), MainContract.Presenter {

    private val view = view

    private val restController = RestController.instance
    private val mapper = ExternalMapper(this)

    override fun loadQuestions(query:String) {
        view.updateLoadingState(true)

        performSearch(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {onItemsLoaded(it)},
                        {onError(it)})
    }

    private fun performSearch(query: String) = when(TextUtils.isEmpty(query)) {
        true -> restController.restApi.questions()
        false -> restController.restApi.search(query)
    }


    private fun onError(error: Throwable) {
        error.printStackTrace()

        view.updateLoadingState(false)
        handleMessagesError(error.toString())
    }

    private fun onItemsLoaded(response: Response<SearchResponse>) {
        view.updateLoadingState(false)

        if (!response.isSuccessful) {
            handleMessagesError(getString(R.string.loading_error))
            return
        }

        val itemsExternal = response.body()?.items ?: ArrayList()
        val items = itemsExternal.map { mapper.question2local(it) }
        view.updateItems(items)
    }

    private fun handleMessagesError(message: String) {
        view.showMessage(message)
        view.updateItems(ArrayList())
    }
}