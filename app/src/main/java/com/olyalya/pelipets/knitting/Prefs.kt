package com.olyalya.pelipets.knitting

import android.content.Context
import android.content.SharedPreferences
import com.olyalya.pelipets.knitting.Direction.FROM_RIGHT_TO_LEFT

enum class Direction {
  FROM_LEFT_TO_RIGHT,
  FROM_RIGHT_TO_LEFT
}

class Prefs {
  public constructor(context: Context) {
    this.context = context
    this.prefs = context.getSharedPreferences(PREFS_FILENAME, 0);
    directionMap = HashMap()
    directionMap.put(DIRECTION_FROM_LEFT_TO_RIGHT, Direction.FROM_LEFT_TO_RIGHT)
    directionMap.put(DIRECTION_FROM_RIGHT_TO_LEFT, Direction.FROM_RIGHT_TO_LEFT)
  }

  val DIRECTION_FROM_LEFT_TO_RIGHT = 111
  val DIRECTION_FROM_RIGHT_TO_LEFT = 112
  val PREFS_FILENAME = "mysettings"
  val COUNTER_VALUE = "simplecounter"
  val CURRENT_PROGRESS = "simpleprogress"
  val OLD_PROGRESS = "simpleoldprogress"
  val DIRECTION = "simpledrive"

  var context: Context
  val directionMap: Map<Int, Direction>
  var prefs: SharedPreferences

  var prefCounter: Int
    get() = prefs.getInt(COUNTER_VALUE, 0)
    set(value) = prefs.edit().putInt(COUNTER_VALUE, value).apply()

  var prefCurrentProgress: Int
    get() = prefs.getInt(CURRENT_PROGRESS, 0)
    set(value) = prefs.edit().putInt(CURRENT_PROGRESS, value).apply()

  var prefOldProgress: Int
    get() = prefs.getInt(OLD_PROGRESS, 0)
    set(value) = prefs.edit().putInt(OLD_PROGRESS, value).apply()

  var prefDirection: Direction
    get() = directionMap.get(prefs.getInt(DIRECTION, DIRECTION_FROM_LEFT_TO_RIGHT))!!
    set(value) =
      if (value == FROM_RIGHT_TO_LEFT) {
        prefs.edit().putInt(DIRECTION, DIRECTION_FROM_RIGHT_TO_LEFT).apply()
      } else {
        prefs.edit().putInt(DIRECTION, DIRECTION_FROM_LEFT_TO_RIGHT).apply()
      }

}
