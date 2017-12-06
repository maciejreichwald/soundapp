package com.rudearts.soloader.ui.main

import com.rudearts.soloader.model.local.Question

interface MainContract {

    interface View {
        fun updateLoadingState(isLoading:Boolean)
        fun updateItems(questions:List<Question>)
        fun showMessage(message:String)
    }

    interface Presenter {
        fun loadQuestions(query:String)
    }
}