package com.rudearts.soundapp.ui.details

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.rudearts.soundapp.R
import com.rudearts.soundapp.di.details.DaggerDetailsComponent
import com.rudearts.soundapp.di.details.DetailsComponent
import com.rudearts.soundapp.di.details.DetailsModule
import com.rudearts.soundapp.extentions.bind
import com.rudearts.soundapp.extentions.visible
import com.rudearts.soundapp.model.LoadingState
import com.rudearts.soundapp.ui.ToolbarActivity
import javax.inject.Inject

class DetailsActivity : ToolbarActivity(), DetailsContract.View {

    companion object {
        val TITLE = "DetailsTitle"
        val LINK = "DetailsLink"
    }

    @Inject lateinit var presenter:DetailsContract.Presenter

    private val emptyView:View by bind(R.id.empty_view)
    private val progressBar:View by bind(R.id.progress_bar)
    private val webView:WebView by bind(R.id.web_view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    internal fun setup() {
        inject()
        setupTitle()

        presenter.setupContent(intent)
    }

    internal fun inject() {
        createComponent().apply {
            this.inject(this@DetailsActivity)
        }
    }

    internal fun createComponent() = DaggerDetailsComponent.builder()
            .detailsModule(DetailsModule(this))
            .build()

    override fun updateLoadingState(state:LoadingState) {
        progressBar.visible = state == LoadingState.LOADING
        webView.visible = state == LoadingState.SHOW_RESULTS
        emptyView.visible = state == LoadingState.NO_RESULTS
    }

    override fun setupWebView(client: WebViewClient, link:String) = with (webView) {
            webViewClient = client
            loadUrl(link)
    }

    private fun setupTitle() {
        title = intent.getStringExtra(TITLE)
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
