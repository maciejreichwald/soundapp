package com.rudearts.soundapp.ui.details

import android.content.Intent
import android.text.TextUtils
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.rudearts.soundapp.extentions.visible
import com.rudearts.soundapp.model.LoadingState

class DetailsPresenter(private val view:DetailsContract.View) : DetailsContract.Presenter {

    override fun setupContent(intent: Intent) {
        val link = intent.getStringExtra(DetailsActivity.LINK)

        when(TextUtils.isEmpty(link)) {
            true -> view.updateLoadingState(LoadingState.NO_RESULTS)
            false -> {
                view.updateLoadingState(LoadingState.LOADING)
                view.setupWebView(createWebViewClient(), link)
            }
        }
    }

    internal fun createWebViewClient() = object : WebViewClient() {
        override fun onPageFinished(webView: WebView?, url: String?) {
            view.updateLoadingState(LoadingState.SHOW_RESULTS)
            super.onPageFinished(webView, url)
        }

        override fun onReceivedError(webView: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            view.updateLoadingState(LoadingState.NO_RESULTS)
            super.onReceivedError(webView, request, error)
        }
    }
}