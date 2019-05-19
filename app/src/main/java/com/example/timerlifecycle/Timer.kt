package com.example.timerlifecycle

import android.os.Handler
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import timber.log.Timber


/*
with LifecycleObserver, the timer can watch for lifecycle changes
But what lifecycle ? the Timer needs a reference
 */
class Timer(lifecycle: Lifecycle) : LifecycleObserver {

    var secondsCount : MutableLiveData<Int> = MutableLiveData()

    var seconds = 0

    private var handler = Handler()

    private lateinit var runnable: Runnable

    init {
        lifecycle.addObserver(this)
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun dummyMethod_forOnPause() {
        Timber.i("onPause I was called")
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun dummyMethod_forOnResume() {
        Timber.i("onResume I was called")
    }



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


    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        // Removes all pending posts of runnable from the handler's queue, effectively stopping the
        // timer
        handler.removeCallbacks(runnable)
    }
}