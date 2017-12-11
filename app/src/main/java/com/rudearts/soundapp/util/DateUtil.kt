package com.rudearts.soundapp.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtil private constructor() {

    private object Holder { val INSTANCE = DateUtil() }

    companion object {
        val TACK_DATE_FORMAT = "yyyy-MM-DD'T'hh:mm:ss'Z'"
        val YEAR_FORMAT = "yyyy"
        val instance: DateUtil by lazy { Holder.INSTANCE }
    }

    private var trackFormat = SimpleDateFormat(TACK_DATE_FORMAT)
    private var yearFormat = SimpleDateFormat(YEAR_FORMAT)

    fun string2date(textDate:String?) = textDate?.let { trackFormat.parse(it) } ?: Date(0)

    fun long2date(time:Long?) = Date(time ?: 0L)

    fun year2date(year:Long?):Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year?.toInt() ?: 0)
        return calendar.time
    }

    fun date2year(date:Date) = yearFormat.format(date)
}
