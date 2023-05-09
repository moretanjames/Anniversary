package yt.tjd.anniversary.home

import android.app.Application
import android.icu.util.GregorianCalendar
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Date


class HomeScreenViewModel(application: Application) : AndroidViewModel(application) {

  val state = MutableStateFlow(emptyList<Holiday>())

  init {
    viewModelScope.launch {
      while (true) {
        val holidays = holidayData.map { data ->
          val millisTill = data.dateInMillis - Date().time

          val monthInMillis = DateUtils.DAY_IN_MILLIS * 30
          val weekInMillis = DateUtils.DAY_IN_MILLIS * 7
          val (monthsLeft, millisAfterMonths) = Pair((millisTill / monthInMillis).toInt(), millisTill % monthInMillis)
          val (weeksLeft, millisAfterWeeks) = Pair((millisAfterMonths / weekInMillis).toInt(), millisAfterMonths % weekInMillis)
          val (daysLeft, millisAfterDays) = Pair((millisAfterWeeks / DateUtils.DAY_IN_MILLIS).toInt(), millisAfterWeeks % DateUtils.DAY_IN_MILLIS)
          val (hoursLeft, millisAfterHours) = Pair((millisAfterDays / DateUtils.HOUR_IN_MILLIS).toInt(), millisAfterDays % DateUtils.HOUR_IN_MILLIS)
          val (minutesLeft, millisAfterMinutes) = Pair((millisAfterHours / DateUtils.MINUTE_IN_MILLIS).toInt(), millisAfterHours % DateUtils.MINUTE_IN_MILLIS)
          val (secondsLeft, millisLeft) = Pair((millisAfterMinutes / DateUtils.SECOND_IN_MILLIS).toInt(), millisAfterMinutes % DateUtils.SECOND_IN_MILLIS)

          Holiday(
            name = data.name,
            monthsLeft = monthsLeft,
            weeksLeft = weeksLeft,
            daysLeft = daysLeft,
            hoursLeft = hoursLeft,
            minutesLeft = minutesLeft,
            secondsLeft = secondsLeft
          )
        }
        Log.d("holidays", holidays.toString())
        state.emit(holidays)
        delay(100)
      }
    }
  }
}

private val holidayData = listOf(
  HolidayData(
    name = "May 24th",
    month = 4,
    day = 23
  ),
  HolidayData(
    name = "4th of July",
    month = 6,
    day = 3
  ),
  HolidayData(
    name = "Pioneer Day",
    month = 6,
    day = 23
  ),
  HolidayData(
    name = "Halloween",
    month = 9,
    day = 30
  ),
  HolidayData(
    name = "Thanks Giving",
    month = 10,
    day = 22
  ),
  HolidayData(
    name = "Christmas",
    month = 11,
    day = 24
  )
)


private data class HolidayData(
  val name: String,
  val month: Int,
  val day: Int
) {
  val dateInMillis = GregorianCalendar().apply { set(get(GregorianCalendar.YEAR), month, day, 0, 0, 0) }.timeInMillis.let { it - (it % DateUtils.SECOND_IN_MILLIS) }
}

data class Holiday(
  val name: String,
  val monthsLeft: Int,
  val weeksLeft: Int,
  val daysLeft: Int,
  val hoursLeft: Int,
  val minutesLeft: Int,
  val secondsLeft: Int
)