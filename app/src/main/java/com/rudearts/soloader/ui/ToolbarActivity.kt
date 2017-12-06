package com.rudearts.soloader.ui

import android.graphics.drawable.ColorDrawable
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
import com.rudearts.soloader.R
import com.rudearts.soloader.extentions.bind


abstract class ToolbarActivity : AppCompatActivity() {

    protected val toolbar:Toolbar by bind(R.id.toolbar)
    protected val searchView:SearchView by bind(R.id.menu_search)
    private val toolbarTitle:TextView by bind(R.id.toolbar_title)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default)
        setupActionBar()
    }

    override fun setContentView(@LayoutRes layoutResID: Int) {
        super.setContentView(layoutResID)
        includeSubview()
    }

    override fun setTitle(title: CharSequence) {
        super.setTitle("")
        toolbarTitle.text = title
    }

    private fun includeSubview() {
        val id = provideSubContentViewId()
        View.inflate(this, id, findViewById(R.id.coordinator_layout))
    }

    protected fun showSnackMessage(message: String) {
        coordinatorLayout?.let {
            Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show()
        }
    }

    protected fun showSearchView() {
        searchView.visibility = View.VISIBLE
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar)
        toolbar?.setTitleTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
    }

    val coordinatorLayout by lazy {
        findViewById<CoordinatorLayout>(R.id.coordinator_layout)
    }

    abstract fun provideSubContentViewId(): Int
}