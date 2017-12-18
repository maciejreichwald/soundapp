package com.rudearts.soundapp.ui

import android.support.v4.content.ContextCompat
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.widget.TextView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.View
import com.rudearts.soundapp.R
import com.rudearts.soundapp.extentions.bind
import com.rudearts.soundapp.extentions.visible


abstract class ToolbarActivity : AppCompatActivity() {

    protected val searchView:SearchView by bind(R.id.menu_search)
    private val toolbar:Toolbar by bind(R.id.toolbar)
    private val toolbarTitle:TextView by bind(R.id.toolbar_title)
    protected val coordinatorLayout:CoordinatorLayout by bind(R.id.coordinator_layout)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default)
        includeSubview()
        setupActionBar()
    }

    override fun setTitle(title: CharSequence) {
        super.setTitle("")
        toolbarTitle.text = title
    }

    private fun includeSubview() {
        View.inflate(this, provideSubContentViewId(), coordinatorLayout)
    }

    protected fun showSnackMessage(message: String) = coordinatorLayout.let {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show()
    }

    protected fun showSearchView() {
        searchView.visible = true
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.primary))
    }

    abstract fun provideSubContentViewId(): Int
}