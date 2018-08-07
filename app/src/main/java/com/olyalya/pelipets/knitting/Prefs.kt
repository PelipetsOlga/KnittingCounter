package com.olyalya.pelipets.knitting

import android.content.Context
import android.content.SharedPreferences

class Prefs (context: Context) {
  val PREFS_FILENAME = "knitting_counter"
  val COUNTER_VALUE = "counter_value"
  val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0);

  var prefCounter: Int
    get() = prefs.getInt(COUNTER_VALUE, 0)
    set(value) = prefs.edit().putInt(COUNTER_VALUE, value).apply()
}
