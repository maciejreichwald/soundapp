package com.rudearts.soloader.util

import java.text.SimpleDateFormat
import java.util.Date

class DateUtil private constructor() {

    private object Holder { val INSTANCE = DateUtil() }

    companion object {
        val TACK_DATE_FORMAT = "yyyy-MM-DD'T'hh:mm:ss'Z'"
        val instance: DateUtil by lazy { Holder.INSTANCE }
    }

    private var trackFormat = SimpleDateFormat(TACK_DATE_FORMAT)

    fun string2date(textDate:String?) = textDate?.let { trackFormat.parse(it) } ?: Date()
    fun long2date(time:Long?) = Date(time ?: 0L)
}
