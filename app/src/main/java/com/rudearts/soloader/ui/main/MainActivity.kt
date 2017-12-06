package com.rudearts.soloader.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.View
import com.rudearts.soloader.R
import com.rudearts.soloader.extentions.bind
import com.rudearts.soloader.model.local.Question
import com.rudearts.soloader.ui.ToolbarActivity
import com.rudearts.soloader.ui.details.DetailsActivity

class MainActivity : ToolbarActivity(), MainContract.View {

    private val refreshLayout:SwipeRefreshLayout by bind(R.id.swipe_container)
    private val progressBar:View by bind(R.id.progress_bar)
    private val listItems:RecyclerView by bind(R.id.items_list)
    private val emptyView:View by bind(R.id.empty_view)

    private val presenter:MainContract.Presenter = MainPresenter(this, this)
    private lateinit var adapter:QuestionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(getString(R.string.so_loader))
        setup()
    }

    private fun setup() {
        showSearchView()
        setupList()
        setupRefresh()
        setupSearchView()
        loadItems()
    }

    private fun setupRefresh() {
        refreshLayout.setOnRefreshListener {
            loadItems()
            refreshLayout.isRefreshing = false
        }
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object: OnQueryTextListener() {
            override fun onQueryTextSubmit(query: String?): Boolean {
                loadItems()
                return super.onQueryTextSubmit(query)
            }
        })
        searchView.setOnCloseListener {
            loadItems()
            false
        }
    }

    private fun loadItems() {
        presenter.loadQuestions(searchView.query.toString())
    }

    private fun setupList() {
        adapter = QuestionAdapter(this, ArrayList(), {onQuestionClick(it)})
        listItems.adapter = adapter
        listItems.layoutManager = LinearLayoutManager(this)
    }

    override fun provideSubContentViewId() = R.layout.activity_main

    override fun updateLoadingState(isLoading: Boolean) {
        progressBar.visibility = View.GONE
        listItems.visibility = View.GONE

        when(isLoading) {
            true -> {
                progressBar.visibility = View.VISIBLE
                showEmptyView(false)
            }
            false -> listItems.visibility = View.VISIBLE
        }
    }

    override fun updateItems(questions: List<Question>) {
        adapter.updateItems(questions)
        showEmptyView(questions.isEmpty())
    }

    override fun showMessage(message: String) {
        showSnackMessage(message)
    }

    private fun showEmptyView(isVisible:Boolean) = when(isVisible) {
        true -> emptyView.visibility = View.VISIBLE
        else -> emptyView.visibility = View.GONE
    }

    private fun onQuestionClick(question: Question) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.TITLE, question.title)
        intent.putExtra(DetailsActivity.LINK, question.link)
        startActivity(intent)
    }

    override fun onBackPressed() {
        if (!searchView.isIconified) {
            searchView.isIconified = true
            return
        }

        super.onBackPressed()
    }
}
