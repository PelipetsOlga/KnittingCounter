package com.olyalya.pelipets.knitting

import android.app.Application

class App : Application() {
  companion object {
    var prefs: Prefs? = null
  }

  override fun onCreate() {
    prefs = Prefs(applicationContext)
    super.onCreate()
  }
}