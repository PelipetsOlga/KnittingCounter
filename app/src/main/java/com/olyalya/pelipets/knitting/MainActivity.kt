package com.olyalya.pelipets.knitting

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import com.olyalya.pelipets.knitting.Direction.FROM_LEFT_TO_RIGHT
import com.olyalya.pelipets.knitting.Direction.FROM_RIGHT_TO_LEFT

class MainActivity : AppCompatActivity(), OnSeekBarChangeListener {
  val LEFT_EDGE = 20
  val RIGHT_EDGE = 80
  var currentProgress = 0
  var counter: Int = 0
  var seekBarView: SeekBar? = null
  var tvCounter: TextView? = null
  var direction = Direction.FROM_LEFT_TO_RIGHT
  var oldProgress = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    seekBarView = findViewById(R.id.seekBar)
    tvCounter = findViewById(R.id.tv_counter)
    seekBarView!!!!.setOnSeekBarChangeListener(this);
  }

  override fun onResume() {
    super.onResume()
    counter = App.prefs!!.prefCounter
    currentProgress = App.prefs!!.prefCurrentProgress
    oldProgress = App.prefs!!.prefOldProgress
    direction = App.prefs!!.prefDirection
    tvCounter?.setText(counter.toString())
    seekBarView?.setProgress(currentProgress)
  }

  override fun onPause() {
    App.prefs!!.prefCounter = counter
    App.prefs!!.prefCurrentProgress = currentProgress
    App.prefs!!.prefOldProgress = oldProgress
    App.prefs!!.prefDirection = direction
    super.onPause()
  }

  override fun onProgressChanged(
    p0: SeekBar?,
    p1: Int,
    p2: Boolean
  ) {
  }

  override fun onStartTrackingTouch(seekBar: SeekBar?) {}

  override fun onStopTrackingTouch(seekBar: SeekBar?) {
    var newProgress = seekBar?.progress
    if (direction == FROM_LEFT_TO_RIGHT) {
      if (newProgress!! > oldProgress) {
        if (newProgress!! > RIGHT_EDGE) {
          oldProgress = 100
          seekBarView?.setProgress(oldProgress)
          direction = FROM_RIGHT_TO_LEFT
          counter++
          tvCounter?.setText(counter.toString())
          currentProgress = 100
        } else {
          oldProgress = newProgress
          currentProgress = oldProgress
        }
      } else {
        seekBarView?.setProgress(oldProgress)
        currentProgress = oldProgress
      }
    } else if (direction == FROM_RIGHT_TO_LEFT) {
      if (newProgress!! < oldProgress) {
        if (newProgress < LEFT_EDGE) {
          oldProgress = 0
          seekBarView?.setProgress(oldProgress)
          direction = FROM_LEFT_TO_RIGHT
          counter++
          tvCounter?.setText(counter.toString())
          currentProgress = 0
        } else {
          oldProgress = newProgress
          currentProgress = oldProgress
        }
      } else {
        seekBarView?.setProgress(oldProgress)
        currentProgress = oldProgress
      }
    }
  }
}
