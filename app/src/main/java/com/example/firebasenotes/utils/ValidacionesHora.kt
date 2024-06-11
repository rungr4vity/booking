package com.example.firebasenotes.utils

import com.example.firebasenotes.models.TimeRange
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object ValidacionesHora {


    fun dayOfYearToDayAndMonth(dayOfYear: Int, year: Int): String {
        // Create a LocalDate from the day of the year and year
        val date = LocalDate.ofYearDay(year, dayOfYear)

        // Format the date to a string with the desired pattern (e.g., "dd MMMM")
        val formatter = DateTimeFormatter.ofPattern("dd MMMM")
        return date.format(formatter)
    }

    fun main() {
        val dayOfYear = 100
        val year = 2024
        val result = dayOfYearToDayAndMonth(dayOfYear, year)
        println(result)  // Output: "09 April"
    }


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
    fun validateAllBookings(requerido: TimeRange,agendados: MutableList<TimeRange>):Boolean {
        var total = 0
        agendados.forEach {
            if(doRangesOverlap(requerido, it)){
                total++
            }
        }
        if(total == 0){
            return true
        }else{
            return false
        }
    }



    // example
    // print(isValidTimeRange(Time(8, 9), Time(8, 10)))
    data class Time(val hours: Int, val minutes: Int)
    fun isValidTimeRange(startTime: Time, endTime: Time): Boolean {
        if (endTime.hours > startTime.hours) return true
        if (endTime.hours == startTime.hours && endTime.minutes > startTime.minutes) return true
        return false
    }






}
