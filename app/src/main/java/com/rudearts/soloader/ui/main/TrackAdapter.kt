package com.rudearts.soloader.ui.main

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rudearts.soloader.R
import com.rudearts.soloader.extentions.loadUrlThumb
import com.rudearts.soloader.model.local.Track
import kotlinx.android.synthetic.main.question_item.view.*
class TrackAdapter(context:Context, items: List<Track>, private val listener: (Track) ->Unit) : RecyclerView.Adapter<TrackAdapter.ViewHolder>() {

    companion object {
        val DEFUALT_IMAGE_SIZE = 150
    }

    private val inflater = LayoutInflater.from(context)
    private var items = items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
             ViewHolder(inflater.inflate(R.layout.question_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
            = holder.bind(items[position], listener)

    override fun getItemCount(): Int = items.size

    fun updateItems(items: List<Track>) {
        this.items = items
        notifyDataSetChanged()
    }

    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        fun bind(track:Track, listener: (Track)->Unit) = with(itemView) {
            avatar.setImageResource(R.drawable.user)
            track.smallIcon?.let {
                avatar.loadUrlThumb(DEFUALT_IMAGE_SIZE, R.drawable.user, it)
            }
            title.text = track.name
            user.text = track.artist
            setOnClickListener { listener(track) }
        }
    }
}

