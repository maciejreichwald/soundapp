package com.rudearts.soloader.ui.details

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.rudearts.soloader.R
import com.rudearts.soloader.extentions.bind
import com.rudearts.soloader.extentions.visible
import com.rudearts.soloader.ui.ToolbarActivity

class DetailsActivity : ToolbarActivity() {

    companion object {
        val TITLE = "DetailsTitle"
        val LINK = "DetailsLink"
    }

    private val emptyView:View by bind(R.id.empty_view)
    private val progressBar:View by bind(R.id.progress_bar)
    private val webView:WebView by bind(R.id.web_view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    private fun setup() {
        setupTitle()
        setupContent()
    }

    private fun setupContent() {
        val link = intent.getStringExtra(LINK)

        when(TextUtils.isEmpty(link)) {
            true -> onInvalidUrl()
            false -> {
                emptyView.visible = false
                setupWebView(link)
            }
        }
    }

    private fun onInvalidUrl() {
        progressBar.visible = false
        webView.visible = false
        emptyView.visible = true
    }

    private fun setupWebView(link:String) = with (webView) {
            webViewClient = createWebViewClient()
            loadUrl(link)
    }

    private fun createWebViewClient() = object : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            progressBar.visible = false
            super.onPageFinished(view, url)
        }

        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            onInvalidUrl()
            super.onReceivedError(view, request, error)
        }
    }

    private fun setupTitle() {
        setTitle(intent.getStringExtra(TITLE))
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun provideSubContentViewId() = R.layout.activity_details

    override fun onBackPressed() = with(webView) {
        when(canGoBack()) {
            true -> goBack()
            false -> super.onBackPressed()
        }
    }
}
