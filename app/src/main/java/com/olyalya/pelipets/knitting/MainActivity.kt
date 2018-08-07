package com.olyalya.pelipets.knitting

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
  var counter:Int = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  override fun onResume() {
    super.onResume()
    counter = App.prefs!!.prefCounter
  }

  override fun onPause() {
    App.prefs!!.prefCounter = counter
    super.onPause()
  }
}
