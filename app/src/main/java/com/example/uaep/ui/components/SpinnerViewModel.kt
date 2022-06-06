package com.example.uaep.ui.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class SpinnerViewModel: ViewModel() {
    private val mTime = MutableLiveData("경기 일정 없음")
    private val mYear = mutableStateOf(0)
    private val mMonth = mutableStateOf(0)
    private val mDay = mutableStateOf(0)
    private val mHour = mutableStateOf(0)
    private val mMinute = mutableStateOf(0)
    private val mSelected = mutableStateOf(false)

    var time: LiveData<String> = mTime
    val year: State<Int> = mYear
    val month: State<Int> = mMonth
    val day: State<Int> = mDay
    val hour: State<Int> = mHour
    val minute: State<Int> = mMinute
    val selected: State<Boolean> = mSelected


    fun selectDateTime(context: Context) {
        var time = ""
        val currentDateTime = Calendar.getInstance()
        val startYear = currentDateTime.get(Calendar.YEAR)
        val startMonth = currentDateTime.get(Calendar.MONTH)
        val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
        val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
        val startMinute = currentDateTime.get(Calendar.MINUTE)

        DatePickerDialog(context, { _, year, month, day ->
            TimePickerDialog(context, { _, hour, minute ->
                val pickedDateTime = Calendar.getInstance()
                pickedDateTime.set(year, (month + 1), day, hour, minute)

                mYear.value = year
                mMonth.value = month + 1
                mDay.value = day
                mHour.value = hour
                mMinute.value = minute

                time = "${year}년 ${mMonth.value}월 ${day}일 ${hour}시 ${minute}분"
                updateDateTime(time)
                onSelected(true)
            }, startHour, startMinute, false).show()
        }, startYear, startMonth, startDay).show()
    }

    private fun updateDateTime(dateTime: String) {
        mTime.value = dateTime
    }

    private fun onSelected(selected: Boolean) {
        mSelected.value = selected
    }
}