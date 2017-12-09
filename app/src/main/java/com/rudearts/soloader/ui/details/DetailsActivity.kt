package com.rudearts.soloader.ui.details

import android.os.Bundle
import android.view.View
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

    private val progressBar:View by bind(R.id.progress_bar)
    private val webView:WebView by bind(R.id.web_view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    private fun setup() {
        setupTitle()
        setupWebView()
    }

    private fun setupWebView() = with (webView) {
            //settings.javaScriptEnabled = true
            webViewClient = createWebViewClient()
            loadUrl(intent.getStringExtra(LINK))
    }

    private fun createWebViewClient() = object : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            progressBar.visible = false
            super.onPageFinished(view, url)
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
