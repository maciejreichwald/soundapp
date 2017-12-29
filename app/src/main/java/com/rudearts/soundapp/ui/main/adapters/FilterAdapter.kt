package com.rudearts.soundapp.ui.main.adapters

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rudearts.soundapp.R
import com.rudearts.soundapp.databinding.FilterTabBinding
import com.rudearts.soundapp.model.filter.SourceType
import javax.inject.Inject

class  FilterAdapter @Inject constructor(context: Context) {

    internal val inflater = LayoutInflater.from(context)
    internal lateinit var filters:List<FilterTabBinding?>

    internal lateinit var rootView:ViewGroup
    internal lateinit var listener: () ->Unit

    fun setup(items: List<SourceType>, rootView:ViewGroup, listener: () ->Unit) {
        this.rootView = rootView
        this.listener = listener

        filters = setupFilterItems(items)
        selectFirst()
    }

    internal fun selectFirst() {
        filters.first {
            it?.root?.isSelected = true
            true
        }
    }

    internal fun setupFilterItems(items: List<SourceType>) = items.map { item ->
            val bindedView = createViewBinding()
            bindedView?.let {
                it.sourceType = item
                it.root.isSelected = false
                it.root.setOnClickListener { onItemClick(bindedView) }
            }
            bindedView
    }

    internal fun onItemClick(bindedView: FilterTabBinding) {
        changeSelection(bindedView.root)
        listener()
    }

    internal fun changeSelection(view:View) {
        filters.forEach { it?.root?.isSelected = false }
        view.isSelected = true
    }

    fun retrieveSelectedSourceType():SourceType {
        val bindedView = filters.find { it?.root?.isSelected ?: false }
        return bindedView?.sourceType ?: SourceType.BOTH
    }

    internal fun createViewBinding(): FilterTabBinding? =
            DataBindingUtil.inflate(inflater, R.layout.filter_tab, rootView, true)

}