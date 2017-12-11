package com.rudearts.soundapp.ui.main

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rudearts.soundapp.R
import com.rudearts.soundapp.extentions.loadUrlThumb
import com.rudearts.soundapp.model.local.Track
import com.rudearts.soundapp.util.DateUtil
import kotlinx.android.synthetic.main.song_item.view.*
import java.util.*

class TrackAdapter(context:Context, items: List<Track>, private val listener: (Track) ->Unit) : RecyclerView.Adapter<TrackAdapter.ViewHolder>() {

    companion object {
        val DEFAULT_IMAGE_SIZE = 150
        val MIN_YEAR = 500L
    }

    private val dateUtil = DateUtil.instance
    private val inflater = LayoutInflater.from(context)
    private var items = items

    private val unknownText = context.getString(R.string.none)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
             ViewHolder(inflater.inflate(R.layout.song_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
            = holder.bind(items[position], listener)

    override fun getItemCount(): Int = items.size

    fun updateItems(items: List<Track>) {
        this.items = items
        notifyDataSetChanged()
    }

    private fun generateDate(releaseDate: Date) = when(isReleaseDateStrange(releaseDate.time)) {
        true -> unknownText
        false -> dateUtil.date2year(releaseDate)
    }

    private fun isReleaseDateStrange(time: Long) = time < MIN_YEAR

    inner class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        fun bind(track:Track, listener: (Track)->Unit) = with(itemView) {
            avatar.setImageResource(R.drawable.song_unknown)
            track.smallIcon?.let {
                avatar.loadUrlThumb(DEFAULT_IMAGE_SIZE, R.drawable.song_unknown, it)
            }
            title.text = track.name
            user.text = track.artist
            date.text = generateDate(track.releaseDate)
            setOnClickListener { listener(track) }
        }
    }
}

