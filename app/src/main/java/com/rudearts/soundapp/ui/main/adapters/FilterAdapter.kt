package com.rudearts.soundapp.ui.main.adapters

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rudearts.soundapp.R
import com.rudearts.soundapp.databinding.FilterTabBinding
import com.rudearts.soundapp.model.filter.SourceType

class  FilterAdapter(context: Context,
                     items: List<SourceType>,
                     private val rootView:ViewGroup,
                     private val listener: () ->Unit) {

    internal val inflater = LayoutInflater.from(context)
    internal var filters:List<FilterTabBinding>

    init {
        filters = setupFilterItems(items)
        selectFirst()
    }

    internal fun selectFirst() {
        filters.first {
            it.root.isSelected = true
            true
        }
    }

    internal fun setupFilterItems(items: List<SourceType>) = items.map { item ->
            val bindedView = createViewBinding()
            bindedView.sourceType = item
            bindedView.root.isSelected = false
            bindedView.root.setOnClickListener { onItemClick(bindedView) }
            bindedView
    }

    internal fun onItemClick(bindedView: FilterTabBinding) {
        changeSelection(bindedView.root)
        listener()
    }

    internal fun changeSelection(view:View) {
        filters.forEach { it.root.isSelected = false }
        view.isSelected = true
    }

    fun retrieveSelectedSourceType():SourceType {
        val bindedView = filters.find { it.root.isSelected }
        return bindedView?.sourceType ?: SourceType.BOTH
    }

    internal fun createViewBinding(): FilterTabBinding =
            DataBindingUtil.inflate(inflater, R.layout.filter_tab, rootView, true)

}