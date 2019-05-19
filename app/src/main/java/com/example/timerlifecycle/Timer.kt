package com.example.timerlifecycle

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import timber.log.Timber

class Timer {

    var secondsCount : MutableLiveData<Int> = MutableLiveData()

    var seconds = 0

    private var handler = Handler()

    private lateinit var runnable: Runnable

    fun onStart() {
        runnable = Runnable {
            seconds++
            secondsCount.value = seconds
            // postDelayed re-adds the action to the queue of actions the Handler is cycling
            // through. The delayMillis param tells the handler to run the runnable in
            // 1 second (1000ms)
            Timber.i("Runnable is running - seconds : $seconds")
            handler.postDelayed(runnable, 1000)
        }

        // This is what initially starts the timer
        handler.postDelayed(runnable, 1000)

        // Note that the Thread the handler runs on is determined by a class called Looper.
        // In this case, no looper is defined, and it defaults to the main or UI thread.
    }

    fun onStop() {
        // Removes all pending posts of runnable from the handler's queue, effectively stopping the
        // timer
        handler.removeCallbacks(runnable)
    }
}