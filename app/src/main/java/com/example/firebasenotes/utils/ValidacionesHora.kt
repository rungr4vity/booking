package com.example.firebasenotes.utils

import com.example.firebasenotes.models.TimeRange

object ValidacionesHora {

    // example
    //    val result = minutesToHour(430)
    //    val result2 = minutesToHour(490)
    //    println("${result.toString()} - ${result2.toString()}")
    fun minutesToHour(min: Int): String {
        val hour = min / 60
        val minute = min % 60
        return "$hour:$minute"
    }

    //    example
    //    val range1 = TimeRange(9, 12)
    //    val range2 = TimeRange(11, 14)
    //    val range3 = TimeRange(13, 15)

    //println("Range1 and Range2 overlap: ${doRangesOverlap(range1, range2)}") // Output: true
    //println("Range1 and Range3 overlap: ${doRangesOverlap(range1, range3)}") // Output: false
    fun doRangesOverlap(range1: TimeRange, range2: TimeRange): Boolean {
        // Check if the ranges overlap
        return range1.start < range2.end && range1.end > range2.start
    }


    // example
    // print(isValidTimeRange(Time(8, 9), Time(8, 10)))
    data class Time(val hours: Int, val minutes: Int)
    fun isValidTimeRange(startTime: Time, endTime: Time): Boolean {
        if (endTime.hours > startTime.hours) return true
        if (endTime.hours == startTime.hours && endTime.minutes > startTime.minutes) return true
        return false
    }



    fun validateAllBookings(mutableList: MutableList<TimeRange>,range: TimeRange):Boolean {
        var total = 0
        mutableList.forEach {
            if(doRangesOverlap(it, range)){
                total++
            }
        }
            if(total == 0){
                return true
            }else{
                return false
            }
    }


}