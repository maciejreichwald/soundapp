package com.rudearts.soundapp.ui.details

import android.content.Intent
import android.webkit.WebViewClient
import com.rudearts.soundapp.model.LoadingState

interface DetailsContract {

    interface View {
        fun updateLoadingState(state:LoadingState)
        fun setupWebView(client:WebViewClient, link:String)
    }

    interface Presenter {
        fun setupContent(intent:Intent)
    }
}