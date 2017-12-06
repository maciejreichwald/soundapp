package com.rudearts.soloader.ui.main

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rudearts.soloader.R
import com.rudearts.soloader.extentions.loadUrl
import com.rudearts.soloader.extentions.loadUrlThumb
import com.rudearts.soloader.model.local.Question
import kotlinx.android.synthetic.main.question_item.view.*
class QuestionAdapter (context:Context, items: List<Question>, private val listener: (Question) ->Unit) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

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

    fun updateItems(items: List<Question>) {
        this.items = items
        notifyDataSetChanged()
    }

    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        fun bind(question:Question, listener: (Question)->Unit) = with(itemView) {
            avatar.setImageResource(R.drawable.user)
            question.owner.profileImage?.let {
                avatar.loadUrlThumb(DEFUALT_IMAGE_SIZE, R.drawable.user, it)
            }
            title.text = question.title
            user.text = question.owner.displayName
            setOnClickListener { listener(question) }
        }
    }
}

