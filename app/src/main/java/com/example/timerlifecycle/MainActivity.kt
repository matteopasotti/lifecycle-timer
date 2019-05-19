package com.example.timerlifecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.timerlifecycle.databinding.ActivityMainBinding
import timber.log.Timber

const val KEY_TIMER_SECONDS = "timer_seconds_key"

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var timer: Timer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        timer = Timer(this.lifecycle)

        if(savedInstanceState != null) {
            timer.seconds = savedInstanceState.getInt(KEY_TIMER_SECONDS, 0)
            timer.secondsCount.value = timer.seconds
            timer.onStart()
        }


        binding.btnStart.setOnClickListener {
            timer.onStart()
        }

        binding.btnStop.setOnClickListener {
            timer.onStop()
        }

        timer.secondsCount.observe(this, Observer { t ->
            binding.txtTimerValue.text = t.toString()
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_TIMER_SECONDS, timer.seconds)
        Timber.i("onSaveInstanceState Called")
    }


    override fun onStop() {
        super.onStop()
        timer.onStop()
    }


}