//package com.gis.smart_dinning.utils.others
//
//import android.annotation.SuppressLint
//import com.gis.smart_dinning.ui.calendar.models.CustomCalendarDate
//import java.text.DateFormat
//import java.text.ParseException
//import java.text.SimpleDateFormat
//import java.util.*
//
//@SuppressLint("SimpleDateFormat")
//object DateUtils {
//    private val TAG = DateUtils::class.java.simpleName
//
//    private const val MILLISECONDS_PER_DAY = 24 * 60 * 60 * 1000
//
//    @JvmStatic
//    val DAY_LONG = 86400000
//
//    //wait format
//    @JvmStatic
//    val currentWeek: List // start from monday if add this line else start from sun
//    <String>
//        get() { //wait format
//            val date = Date()
//            val c = Calendar.getInstance()
//            c.time = date
//            val firstDay = c.firstDayOfWeek
//            val dayweek = c[Calendar.DAY_OF_WEEK]
//            val dayOfWeek = dayweek - firstDay
//            c.add(
//                Calendar.DAY_OF_MONTH,
//                -dayOfWeek
//            ) // start from monday if add this line else start from sun
//            val start = c.time
//            c.add(Calendar.DAY_OF_MONTH, 6)
//            val end = c.time
//            val fromDate = SimpleDateFormat("yyyy-MM-dd").format(start)
//            val toDate = SimpleDateFormat("yyyy-MM-dd").format(end)
//            return Arrays.asList(fromDate, toDate)
//        }
//
//    //wait format
//    @JvmStatic
//    val lastWeek: List // -i -7  start from mondey if add this line else start from sun
//    <String>
//        get() { //wait format
//            val date = Date()
//            val c = Calendar.getInstance()
//            c.time = date
//            val i = c[Calendar.DAY_OF_WEEK] - c.firstDayOfWeek
//            c.add(
//                Calendar.DATE,
//                -i - 7
//            ) // -i -7  start from mondey if add this line else start from sun
//            val start = c.time
//            c.add(Calendar.DATE, 6)
//            val end = c.time
//            val fromDate = SimpleDateFormat("yyyy-MM-dd").format(start)
//            val toDate = SimpleDateFormat("yyyy-MM-dd").format(end)
//            return Arrays.asList(fromDate, toDate)
//        }
//
//    @JvmStatic
//    fun getLastMonth(): List<String> {
//        val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)
//        val calendar = Calendar.getInstance()
//        val dates: MutableList<String> = ArrayList()
//
//        // to to previous month
//        calendar.add(Calendar.MONTH, -1)
//        // set the date as the first of the month
//        calendar.set(Calendar.DAY_OF_MONTH, 1)
//        dates.add(format.format(calendar.time))
//        // set the date as the last of the month
//        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DATE))
//        dates.add(format.format(calendar.time))
//        return dates
//    }
//
//    fun getLastYear(): List<String> {
//        val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)
//        val calendar = Calendar.getInstance()
//        val dates: MutableList<String> = ArrayList()
//
//        // go to previous year
//        calendar.add(Calendar.YEAR, -1)
//        // set the date as the first of the year
//        calendar.set(Calendar.DAY_OF_YEAR, 1)
//        calendar.set(Calendar.MONTH, 0)
//        // set the date to the first of the month
//        calendar.set(Calendar.DAY_OF_MONTH, 1)
//        dates.add(format.format(calendar.time))
//        // set the date as the last of the month
//        calendar.add(Calendar.MONTH, 11)
//        dates.add(format.format(calendar.time))
//        return dates
//    }
//
//
//    @JvmStatic
//    fun formatDateTime(dateTime: String?): Date? {
//        if (dateTime == null) return Date()
//        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
//        var newDate: Date? = null
//        newDate = try {
//            format.parse(dateTime)
//        } catch (e: ParseException) {
//            e.printStackTrace()
//            Date()
//        }
//        return newDate
//    }
//
//    @JvmStatic
//    fun journalDateArrange(dateTime: String?): String? {
//        val pattern = "EEE , HH:mm"
//        val simpleDateFormat = SimpleDateFormat(pattern, Locale.US)
//        val td = formatStringDateWithZone(dateTime)
//        return if (td != null) {
//            simpleDateFormat.format(td)
//        } else dateTime
//    }
//
//    @JvmStatic
//    fun formatSelectedDated(millisecond: Long?): String {
//        return SimpleDateFormat("yyyy-MM-dd").format(millisecond)
//    }
//
//    @JvmStatic
//    val last15Days: String
//        get() {
//            val calendar = Calendar.getInstance()
//            val day = calendar[Calendar.DAY_OF_MONTH]
//            val month = calendar[Calendar.MONTH]
//            var cal = day - 15
//            return if (cal < 0) {
//                calendar[calendar[Calendar.YEAR], calendar[Calendar.MONTH] - 1] = 1
//                val lastDay = calendar.getActualMaximum(Calendar.DATE)
//                cal += lastDay
//                calendar[Calendar.DAY_OF_MONTH] = cal
//                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
//                sdf.format(calendar.time)
//            } else {
//                calendar[Calendar.DAY_OF_MONTH] = cal
//                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
//                sdf.format(calendar.time)
//            }
//        }
//
//    @JvmStatic
//    fun getMillisecond(format: String?): Long {
//        val sdf = SimpleDateFormat("yyyy-MM-dd")
//        var date: Date? = null
//        try {
//            date = sdf.parse(format)
//        } catch (e: ParseException) {
//            e.printStackTrace()
//        }
//        assert(date != null)
//        return date!!.time
//    }
//
//    @JvmStatic
//    val todayDate: String
//        get() = SimpleDateFormat("yyyy-MM-dd").format(Date(System.currentTimeMillis()))
//
//    @JvmStatic
//    val todayDateServer: String
//        get() {
//            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//            val date = Date(System.currentTimeMillis())
//            return formatTimeServer(formatter.format(date))
//        }
//
//    // -1
//    // + 2
//    @JvmStatic
//    val todayDateNowMeal: List<String>
//        get() {
//            val dateTime: MutableList<String> = ArrayList()
//            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//            var date = Date(System.currentTimeMillis() - 3600000) // -1
//            dateTime.add(formatTimeServer(formatter.format(date)))
//            date = Date(System.currentTimeMillis() + 3600000 * 2) // + 2
//            dateTime.add(formatTimeServer(formatter.format(date)))
//            return dateTime
//        }
//
//    @JvmStatic
//    val todayDateNoTime: String
//        get() {
//            val formatter = SimpleDateFormat("yyyy-MM-dd")
//            val date = Date(System.currentTimeMillis())
//            return formatter.format(date.time)
//        }
//
//    @JvmStatic
//    fun todayDateNoTimeFormater(date: String) = SimpleDateFormat("yyyy-MM-dd").parse(date)
//
//
//    @JvmStatic
//    val tomorrowDateServer: String
//        get() {
//            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//            val date = Date(System.currentTimeMillis() + toMilliSeconds(1f))
//            return formatTimeServer(formatter.format(date))
//        }
//
//    @JvmStatic
//    fun getHourFormat(time: Int): String {
//        val min = TimeFormat.getMinute(time)
//        val minute = if (min == 0) "00" else min.toString()
//        return String.format(
//            Locale.US,
//            "%s:%s %s",
//            TimeFormat.getMinuteToHour(time),
//            minute,
//            getMeridian(time)
//        )
//    }
//
//    @JvmStatic
//    fun dateFormatPromotionNoTime(times: String): String {
//        val formatter = SimpleDateFormat("yyyy-MM-dd")
//        var date: Date? = null
//        try {
//            date = formatter.parse(times)
//        } catch (e: ParseException) {
//            e.printStackTrace()
//        }
//        return if (date == null) {
//            times
//        } else SimpleDateFormat("dd/MM/yy").format(date)
//    }
//
//    @JvmStatic
//    fun dateFormatPromotionWithTime(times: String): String {
//        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//        var date: Date? = null
//        try {
//            date = formatter.parse(times)
//        } catch (e: ParseException) {
//            e.printStackTrace()
//        }
//        return if (date == null) {
//            times
//        } else SimpleDateFormat("dd/MM/yy").format(date)
//    }
//
//    @JvmStatic
//    fun dateFormatNotification(times: String): String {
//        val formatter = SimpleDateFormat("yyyy-MM-dd")
//        var date: Date? = null
//        try {
//            date = formatter.parse(times)
//        } catch (e: ParseException) {
//            e.printStackTrace()
//        }
//        return if (date == null) {
//            times
//        } else SimpleDateFormat("MMM dd,yyyy").format(date)
//    }
//
//    @JvmStatic
//    fun getMeridian(time: Int): String {
//        return if (TimeFormat.isAnteMeridian(TimeFormat.getMinuteToHour(time))) {
//            "AM"
//        } else "PM"
//    }
//
//    @JvmStatic
//    fun journalTimes(time: String): String? {
//        // convert time to int
//        val hourMin = time.split(":").toTypedArray()
//        if (hourMin.size >= 2) {
//            val hour = hourMin[0].toInt()
//            val mins = hourMin[1].toInt()
//            return String.format(
//                Locale.US,
//                "%s:%s %s",
//                hour,
//                if (mins < 10) "0$mins" else mins,
//                getMeridian(hour)
//            )
//        }
//        return null
//    }
//
//    @JvmStatic
//    fun calculateDuration(hour: Int, minute: Int): String {
//        val calendar = Calendar.getInstance()
//        val cHour = calendar.get(Calendar.HOUR_OF_DAY)
//        val cMinute = calendar.get(Calendar.MINUTE)
//        val cSecond = calendar.get(Calendar.SECOND)
//        if (hour == cHour && minute == cMinute) {
//            return cSecond.toString().plus("s")
//        }
//        if (cHour - hour <= 0) {
//            return cMinute.toString().plus("mins")
//        }
//        if (cHour - hour >= 0) {
//            return (cHour - hour).toString().plus("hs")
//        }
//
//        return ""
//    }
//
//    @JvmStatic
//    fun createdDateFormat(): String {
//        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//        return dateFormat.format(Date())
//    }
//
//    @JvmStatic
//    fun formateLocaleDate(time: String?): Date? {
//        val mTime = time?.let { Util.replaceDoubleQuote(it) } ?: "" ?: return null
//        val format: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
//        var date: Date? = null
//        date = try {
//            format.parse(mTime)
//        } catch (e: ParseException) {
//            e.printStackTrace()
//            return null
//        }
//        return date
//    }
//
//    @JvmStatic
//    fun formatStringDateWithZone(time: String?): Date? {
//        var data = time
//        data = data?.let { Util.replaceDoubleQuote(it) }
//        if (data == null) return null
//        val format: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
//        val date: Date = try {
//            format.parse(data)
//        } catch (e: ParseException) {
//            e.printStackTrace()
//            return null
//        }
//        return Date(date.time + GregorianCalendar().timeZone.rawOffset)
//    }
//
//    @JvmStatic
//    fun toMilliSeconds(day: Float) = (day * 24 * 60 * 60 * 1000).toLong()
//
//
//    @JvmStatic
//    fun nowInMinutes(): Int {
//        val calendar = Calendar.getInstance()
//        val hour = calendar[Calendar.HOUR_OF_DAY]
//        val minute = calendar[Calendar.MINUTE]
//        return minute + hour.getMinutes()
//    }
//
//    //Line2
//    @JvmStatic
//    val firstDayOfWeek: Date
//        get() {
//            val c = Calendar.getInstance()
//            c.firstDayOfWeek = Calendar.MONDAY //Line2
//            c.timeInMillis = toMilliSeconds(todayDateServer, null)
//            val dayOfWeek = c[Calendar.DAY_OF_WEEK]
//            c[Calendar.DAY_OF_WEEK] = c.firstDayOfWeek
//            return c.time
//        }
//
//    @JvmStatic
//    val todayDTAsLong: Long
//        get() {
//            val date = Date(System.currentTimeMillis())
//            val format: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
//            return getMillisecond(format.format(date))
//        }
//
//    @JvmStatic
//    fun toMilliSeconds(dateTime: String, format: String?): Long {
//        var pattern = format
//        if (pattern == null) {
//            pattern = "yyyy-MM-dd HH:mm:ss"
//        }
//        val sdf = SimpleDateFormat(pattern)
//        var date = Date()
//        try {
//            date = sdf.parse(dateTime)
//        } catch (e: ParseException) {
//            e.printStackTrace()
//        }
//        return date.time
//    }
//
//    @JvmStatic
//    fun toJournalDateFormat(milliSecond: Long): String {
//        val selectedDate = Date(milliSecond)
//        val format = SimpleDateFormat("yyyy/MM/dd", Locale.US)
//        return format.format(selectedDate)
//    }
//
//    //format to server time for insert
//    @JvmStatic
//    fun formatTimeServer(dateTime: String?): String {
//        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
//        val date = formatDateTime(dateTime)
//        return format.format(date!!.time - GregorianCalendar().timeZone.rawOffset)
//    }
//
//
//    //format to local time for use
//    @JvmStatic
//    fun formatTimeLocal(dateTime: String?): String {
//        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
//        val date = formatDateTime(dateTime)
//        return format.format(date!!.time + GregorianCalendar().timeZone.rawOffset)
//    }
//
//    @JvmStatic
//    fun timeFormatBooking(dateTime: String?, format: String? = "HH:mm a"): String {
//        val time = SimpleDateFormat(format, Locale.US)
//        val date = formatDateTime(dateTime)
//        return time.format(date!!.time + GregorianCalendar().timeZone.rawOffset)
//    }
//
//    @JvmStatic
//    fun formatDateBillDetail(dateTime: String?): String {
//        val format = SimpleDateFormat("M/dd/yy HH:mm", Locale.US)
//        val date = formatDateTime(dateTime)
//        return format.format(date!!.time + GregorianCalendar().timeZone.rawOffset)
//    }
//
//    @JvmStatic
//    fun isBIllDate(date: String?): Boolean {
//        val format = SimpleDateFormat("M/dd/yy HH:mm", Locale.US)
//        return try {
//            val mDate = format.parse(date)
//            true
//        } catch (e: ParseException) {
//            e.printStackTrace()
//            false
//        }
//    }
//
//    @JvmStatic
//    fun printDataDate(): String {
//        val format = SimpleDateFormat("M/dd/yy h:mm a", Locale.US)
//        return format.format(Date())
//    }
//
//    @JvmStatic
//    fun dateFormatForNext(aday: Long = 86400000, isAdd: Boolean = false): String {
//        val date = Date()
//        var time = date.time
//        if (isAdd) {
//            time += aday
//        } else {
//            time -= aday
//        }
//        return convertDate(time)
//    }
//
//    @JvmStatic
//    fun convertDate(dateInMilliseconds: Long, dateFormat: String = "EEE, dd MMM yyyy") =
//        SimpleDateFormat(dateFormat).format(dateInMilliseconds)
//
//    @JvmStatic
//    fun convertDate(date: String, currentFormat: String, toFormat: String = "M/dd/yy h:mm a") =
//        convertDate(SimpleDateFormat(currentFormat).parse(date), toFormat)
//
//    @JvmStatic
//    fun convertDate(date: Date, dateFormat: String) = convertDate(date.time, dateFormat)
//
//    @JvmStatic
//    fun printDataDate(date: String?) = SimpleDateFormat(
//        "M/dd/yy h:mm a",
//        Locale.US
//    ).format(formatDateTime(date)) //2021-06-17 02:32:05 this format
//
//    @JvmStatic
//    fun dateFormatTodayFloor() = SimpleDateFormat("EEE, dd MMM yyyy", Locale.US).format(Date())
//
//    fun dateFormatMMM(date: String) =
//        SimpleDateFormat("MMM dd, yyyy", Locale.US).format(formatDateTime(date)!!)
//
//    @JvmStatic
//    fun getCurrentTime(): Int {
//        val now = Calendar.getInstance()
//        return now.get(Calendar.HOUR_OF_DAY)
//    }
//
//    @JvmStatic
//    fun compareDate(firstDate: Date, secondDate: Date): Int {
//        return firstDate.compareTo(secondDate)
//    }
//
//    @JvmStatic
//    fun lastYearServer(): String {
//        val prevYear = Calendar.getInstance()
//        val currentYear = prevYear[Calendar.YEAR].toString()
//        prevYear.add(Calendar.YEAR, -1)
//        val lastYear = prevYear[Calendar.YEAR].toString()
//        return todayDateServer.replace(oldValue = currentYear, newValue = lastYear, true)
//    }
//
//    @JvmStatic
//    fun getToday(curDate: Date): String {
//        val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)
//        val date: Date = format.parse(format.format(curDate))
//        val calendar = Calendar.getInstance()
//        calendar.time = date
//        return format.format(calendar.time)
//    }
//
//    @JvmStatic
//    fun format12To24HourTime(timeString: String): Int {
//        val displayFormat = SimpleDateFormat("HH:mm")
//        val parseFormat = SimpleDateFormat("hh:mm a")
//        var date: Date? = null
//        var time = timeString
//        try {
//            date = parseFormat.parse(timeString)
//            time = displayFormat.format(date)
//            val times = time.split(":")
//            if (times.isNotEmpty() && times.size >= 2) {
//                return (times[0].toInt() * 60) + times[1].toInt()
//            }
//        } catch (e: ParseException) {
//            e.printStackTrace()
//        }
//        return -1
//    }
//
//    @JvmStatic
//    fun formatTimeTo24Hour(timeString: String): Int {
//        val times = timeString.split(":")
//        if (times.isNotEmpty()) {
//            return times[0].toInt() * 60 + times[1].toInt()
//        }
//        return -1
//    }
//
//    @JvmStatic
//    fun format24To12HourTime(timeString: String, format: String? = "HH : mm a"): String {
//        val displayFormat = SimpleDateFormat(format)
//        val parseFormat = SimpleDateFormat("hh:mm a")
//        var date: Date? = null
//        try {
//            date = displayFormat.parse(timeString)
//            var timeString = parseFormat.format(date)
//            return timeString
//        } catch (e: ParseException) {
//            e.printStackTrace()
//        }
//        return timeString
//    }
//
//
//    @JvmStatic
//    fun format24To12(hour: Int): Int {
//        if (hour >= 24) return 0
//        if (hour < 13) return hour
//        var mHour = 1
//        for (i in 13..24) {
//            if (hour == i) {
//                return mHour
//            }
//            mHour++
//        }
//        return mHour
//    }
//
//    @JvmStatic
//    fun format12To24(hour: Int, meridian: String): Int {
//        if (meridian.equals("am", true)) return hour
//        if (hour >= 24) return 0
//
//        var mHour = 1
//        for (i in 13..24) {
//            if (hour == mHour) {
//                return i
//            }
//            mHour++
//        }
//        return mHour
//    }
//
////    @JvmStatic
////    fun weekListOfMonth(
////        date: CustomCalendarDate,
////    ): MutableList<CustomCalendarDate> {
////
////        val weekList: MutableList<CustomCalendarDate> = ArrayList()
////        val now = Calendar.getInstance()
////        val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)
////        val mDate: Date = format.parse(date.rawString)
////        now.time = mDate
////
////        now.add(Calendar.DAY_OF_MONTH, 1)
////        for (i in now.get(Calendar.DATE)..now.getActualMaximum(Calendar.DAY_OF_MONTH)) {
////            weekList.add(CustomCalendarDate(format.format(now.time)))
////            now.add(Calendar.DAY_OF_MONTH, 1)
////        }
////
////        now.time = mDate
////        val startTime = now.get(Calendar.DAY_OF_MONTH)
////        for (i in 1..startTime) {
////            weekList.add(0, CustomCalendarDate(format.format(now.time)))
////            now.add(Calendar.DAY_OF_MONTH, -(1))
////        }
////
////        return weekList
////    }
//
//    @JvmStatic
//    fun getNextMonth(date: Date? = null): Date? {
//        val calendar = Calendar.getInstance()
//        calendar.time = date ?: Date()
//        if (calendar[Calendar.MONTH] == Calendar.DECEMBER) {
//            calendar[Calendar.MONTH] = Calendar.JANUARY
//            calendar[Calendar.YEAR] = calendar[Calendar.YEAR] + 1
//        } else {
//            calendar.roll(Calendar.MONTH, true)
//        }
//        return calendar.time
//    }
//
//    @JvmStatic
//    fun getDatesBetweenUsingJava7(startDate: Date = Date(), endDate: Date): MutableList<Date> {
//        val datesInRange = mutableListOf<Date>()
//        val calendar = getCalendarWithoutTime(startDate)
//        val endCalendar = getCalendarWithoutTime(endDate)
//        while (calendar.before(endCalendar)) {
//            val result = calendar.time
//            datesInRange.add(result)
//            calendar.add(Calendar.DATE, 1)
//        }
//        return datesInRange
//    }
//
//    private fun getCalendarWithoutTime(date: Date): Calendar {
//        val calendar: Calendar = GregorianCalendar()
//        calendar.time = date
//        calendar[Calendar.HOUR] = 0
//        calendar[Calendar.HOUR_OF_DAY] = 0
//        calendar[Calendar.MINUTE] = 0
//        calendar[Calendar.SECOND] = 0
//        calendar[Calendar.MILLISECOND] = 0
//        return calendar
//    }
//
//    fun getMonthsFromCurrent(date: String): MutableList<String> {
//        val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)
//        val now = Calendar.getInstance()
//        val mDate: Date = format.parse(date)!!
//        now.time = mDate
//
//        val monthList: MutableList<String> = ArrayList()
//        for (i in 6 downTo 1) {
//            monthList.add(
//                String.format(
//                    "%d-%02d-01",
//                    now.get(Calendar.YEAR),
//                    now.get(Calendar.MONTH) - i
//                )
//            )
//        }
//        monthList.add(String.format("%d-%02d-01", now.get(Calendar.YEAR), now.get(Calendar.MONTH)))
//        for (i in 1..5) {
//            monthList.add(
//                String.format(
//                    "%d-%02d-01",
//                    now.get(Calendar.YEAR),
//                    now.get(Calendar.MONTH) + i
//                )
//            )
//        }
//        return monthList
//    }
//
//    @SuppressLint("SimpleDateFormat")
//    fun String.isDateWithin(days: Int): Boolean {
//        val daysAgoDate = Date(System.currentTimeMillis() - days * MILLISECONDS_PER_DAY)
//        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//        val serverDate = formatter.parse(this)
//        serverDate?.let {
//            return serverDate.after(daysAgoDate)
//        }
//        return false
//    }
//
//}
//
//fun Int.getMinutes() = this * 60
//fun Int.toHour() = this / 60
//fun Int.toMinute() = this % 60
//fun Date.isTodayDate(date: Date) = this.compareTo(date) == 0
//fun Calendar.getTimeInMinutes() : Int {
//    return (get(Calendar.HOUR_OF_DAY).getMinutes()) + get(Calendar.MINUTE)
//}