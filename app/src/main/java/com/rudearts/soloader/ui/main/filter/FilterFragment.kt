package com.rudearts.soloader.ui.main.filter


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner

import com.rudearts.soloader.R
import com.rudearts.soloader.databinding.SingleFilterViewBinding
import com.rudearts.soloader.extentions.visible
import com.rudearts.soloader.model.filter.*
import com.rudearts.soloader.util.animation.RotatedFadeAnimator

class FilterFragment : Fragment(), FilterContract.View {

    private val presenter:FilterContract.Presenter = FilterPresenter()

    private var fadeAnimator: RotatedFadeAnimator? = null
    private var inflater: LayoutInflater? = null

    private var container:ViewGroup? = null
    private var dropSource: Spinner? = null
    private var dropSearchType: Spinner? = null
    private var dropSort: Spinner? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?):View?
            = inflater.inflate(R.layout.fragment_filter, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupContextVariables()
        setupFilters()
    }

    private fun setupFilters() = context?.let {
        dropSource = setupFilter(R.drawable.source, R.string.source, SourceType.values())
        dropSearchType = setupFilter(R.drawable.search_type, R.string.search_type, SearchType.values())
        dropSort = setupFilter(R.drawable.sort, R.string.sort_by, SortType.values())
    }

    private fun <T:Titlable> setupFilter(iconId: Int, labelId: Int, items: Array<T>) = inflater?.let {
        with(createFilterViewBinding(it)) {
            filter = FilterField(iconId, labelId, View.OnClickListener { onSpinnerClick(dropdown) })
            dropdown.adapter = FilterAdapter(context!!, items.map { it })
            container?.addView(root)
            dropdown
        }
    }

    private fun createFilterViewBinding(inflater: LayoutInflater):SingleFilterViewBinding =
            DataBindingUtil.inflate(inflater, R.layout.single_filter_view, null, false)

    private fun onSpinnerClick(dropdown: Spinner) = with(dropdown) {
        isClickable = true
        performClick()
        isClickable = false
    }

    private fun setupContextVariables() = context?.let {
        fadeAnimator = RotatedFadeAnimator(it, false)
        inflater = LayoutInflater.from(it)
        container = view?.findViewById(R.id.container)
    }

    override fun activatedAnimated(query:String) {
        presenter.storeFilter(getFilter(query))
        view?.let { fadeAnimator?.startFadeIn(it) }
    }

    override fun hideAnimated() {
        view?.let { fadeAnimator?.startFadeOut(it) }
    }

    override fun restoreFilter() {
        restoreFilter(presenter.retrieveFilter())
    }

    private fun restoreFilter(filter: TrackFilter?) = filter?.let{
        restoreSpinnerSelection(dropSearchType, filter.type)
        restoreSpinnerSelection(dropSource, filter.source)
        restoreSpinnerSelection(dropSort, filter.sort)
    }

    private fun <T>restoreSpinnerSelection(spinner: Spinner?, value: T) = spinner?.let {
        val adapter = spinner.adapter as ArrayAdapter<T>
        spinner.setSelection(adapter.getPosition(value))
    }

    override fun hide() {
        view?.visible = false
    }

    override fun isShown(): Boolean = view?.isShown ?: false

    override fun getFilter(query: String): TrackFilter = TrackFilter(
            query,
            dropSearchType?.selectedItem as SearchType,
            dropSource?.selectedItem as SourceType,
            dropSort?.selectedItem as SortType)

    override fun hasFilterChanged(query:String) = presenter.checkFilterChanged(getFilter(query))
}
