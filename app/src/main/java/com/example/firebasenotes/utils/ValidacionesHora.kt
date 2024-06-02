package com.example.firebasenotes.utils

import com.example.firebasenotes.models.TimeRange

object ValidacionesHora {


    fun minutesToHour(min: Int): String {
        val hour = min / 60
        val minute = min % 60
        return "$hour:$minute"
    }

    fun doRangesOverlap(range1: TimeRange, range2: TimeRange): Boolean {
        // Check if the ranges overlap
        return range1.start < range2.end && range1.end > range2.start
    }

}