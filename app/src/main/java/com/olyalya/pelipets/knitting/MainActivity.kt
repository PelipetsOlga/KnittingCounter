package com.olyalya.pelipets.knitting

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import com.olyalya.pelipets.knitting.Direction.FROM_LEFT_TO_RIGHT
import com.olyalya.pelipets.knitting.Direction.FROM_RIGHT_TO_LEFT
import java.lang.Integer.parseInt

class MainActivity : AppCompatActivity(), OnSeekBarChangeListener {
  val LEFT_EDGE = 10
  val RIGHT_EDGE = 90
  var currentProgress = 0
  var counter: Int = 0
  var seekBarView: SeekBar? = null
  var tvCounter: TextView? = null
  var arrowForward: ImageView? = null
  var arrowBack: ImageView? = null
  var oldProgress = 0
  var btnSetCounter: Button? = null
  var btnMinusOne: Button? = null
  var btnSetZero: Button? = null
  var btnChangeSide: Button? = null

  var direction: Direction = FROM_LEFT_TO_RIGHT
    set(value) {
      field = value
      if (value == FROM_LEFT_TO_RIGHT) {
        arrowForward?.setVisibility(View.VISIBLE)
        arrowBack?.setVisibility(View.GONE)
      } else {
        arrowForward?.setVisibility(View.GONE)
        arrowBack?.setVisibility(View.VISIBLE)
      }
    }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    seekBarView = findViewById(R.id.seekBar)
    tvCounter = findViewById(R.id.tv_counter)
    arrowForward = findViewById(R.id.arrow_forward)
    arrowBack = findViewById(R.id.arrow_back)
    btnSetCounter = findViewById(R.id.btn_set_counter)
    btnMinusOne = findViewById(R.id.btn_minus_1)
    btnSetZero = findViewById(R.id.btn_set_zero)
    btnChangeSide = findViewById(R.id.btn_change_side)

    seekBarView?.setOnSeekBarChangeListener(this);
    btnSetCounter?.setOnClickListener { showDialog() }
    btnSetZero?.setOnClickListener { setCounterToZero() }
    btnMinusOne?.setOnClickListener { minusOne() }
    btnChangeSide?.setOnClickListener { changeSide() }
  }

  fun setCounterToZero() {
    counter = 0
    oldProgress = 0
    currentProgress = 0
    direction = FROM_LEFT_TO_RIGHT
    updateCounterView()
    setOldProgressToSeekBar()
  }

  fun minusOne() {
    if (counter > 0) {
      if (seekBarView?.getProgress() == 0) {
        counter--
        oldProgress = 100
        setOldProgressToSeekBar()
        direction = FROM_RIGHT_TO_LEFT
        updateCounterView()
      } else if (seekBarView?.getProgress() == 100) {
        counter--
        oldProgress = 0
        setOldProgressToSeekBar()
        direction = FROM_LEFT_TO_RIGHT
        updateCounterView()
      } else {
        if (direction == FROM_LEFT_TO_RIGHT) {
          oldProgress = 0
        } else {
          oldProgress = 100
        }
        setOldProgressToSeekBar()
      }
    }
  }

  fun changeSide() {
    if (seekBarView?.progress == 0) {
      oldProgress = 100
      setOldProgressToSeekBar()
      direction = FROM_RIGHT_TO_LEFT
    } else if (seekBarView?.progress == 100) {
      oldProgress = 0
      setOldProgressToSeekBar()
      direction = FROM_LEFT_TO_RIGHT
    } else {
      oldProgress = 100 - seekBarView?.progress!!
      setOldProgressToSeekBar()
      if (direction == FROM_LEFT_TO_RIGHT) {
        direction = FROM_RIGHT_TO_LEFT
      } else {
        direction = FROM_LEFT_TO_RIGHT
      }
    }
  }

  override fun onResume() {
    super.onResume()
    counter = App.prefs!!.prefCounter
    currentProgress = App.prefs!!.prefCurrentProgress
    oldProgress = App.prefs!!.prefOldProgress
    direction = App.prefs!!.prefDirection
    updateCounterView()
    seekBarView?.setProgress(currentProgress)
  }

  override fun onPause() {
    App.prefs?.prefCounter = counter
    App.prefs?.prefCurrentProgress = currentProgress
    App.prefs?.prefOldProgress = oldProgress
    App.prefs?.prefDirection = direction
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
        if (newProgress > RIGHT_EDGE) {
          oldProgress = 100
          setOldProgressToSeekBar()
          direction = FROM_RIGHT_TO_LEFT
          counter++
          updateCounterView()
          currentProgress = 100
        } else {
          oldProgress = newProgress
          currentProgress = oldProgress
        }
      } else {
        setOldProgressToSeekBar()
        currentProgress = oldProgress
      }
    } else if (direction == FROM_RIGHT_TO_LEFT) {
      if (newProgress!! < oldProgress) {
        if (newProgress < LEFT_EDGE) {
          oldProgress = 0
          setOldProgressToSeekBar()
          direction = FROM_LEFT_TO_RIGHT
          counter++
          updateCounterView()
          currentProgress = 0
        } else {
          oldProgress = newProgress
          currentProgress = oldProgress
        }
      } else {
        setOldProgressToSeekBar()
        currentProgress = oldProgress
      }
    }
  }

  private fun updateCounterView() {
    tvCounter?.setText(counter.toString())
  }

  private fun setOldProgressToSeekBar() {
    seekBarView?.setProgress(oldProgress)
  }

  private fun showDialog() {
    val dialogBuilder = AlertDialog.Builder(this)
    val inflater = this.layoutInflater
    val dialogView = inflater.inflate(R.layout.custom_dialog, null)
    dialogBuilder.setView(dialogView)

    val editText = dialogView.findViewById<View>(R.id.et_counter) as EditText

    dialogBuilder.setTitle(R.string.dialog_title)
    dialogBuilder.setMessage(R.string.dialog_message)
    dialogBuilder.setPositiveButton(R.string.btn_save, { dialog, whichButton ->
      var newCounter = -1
      if (!TextUtils.isEmpty(editText.getText().toString())) {
        try {
          newCounter = parseInt(editText.getText().toString())
        } catch (e: NumberFormatException) {
        }
        if (newCounter > -1) {
          setCounterView(newCounter)
        }
      }
    })
    dialogBuilder.setNegativeButton(R.string.btn_cancel, { dialog, whichButton ->
    })
    val b = dialogBuilder.create()
    b.show()
  }

  private fun setCounterView(newCounter: Int) {
    counter = newCounter
    updateCounterView()
  }
}
