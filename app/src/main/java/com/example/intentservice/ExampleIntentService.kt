package com.example.intentservice

import android.app.IntentService
import android.app.Notification
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.PowerManager
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.intentservice.App.Companion.CHANNEL_ID

class ExampleIntentService : IntentService("ExampleIntentService") {

    lateinit var wakeLock : PowerManager.WakeLock

    init {
        setIntentRedelivery(true)
    }

    companion object {
        private val TAG: String = ExampleIntentService::class.java.simpleName
    }

    override fun onCreate() {
        super.onCreate()

        val powerManager : PowerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "ExampleApp:WakeLock")
        wakeLock.acquire()
        Log.d(TAG, "Wakelock acquired")

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val notification : Notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Example Service")
                .setContentText("Example Text")
                .setSmallIcon(R.drawable.ic_android).build()

            startForeground(1, notification)
        }

    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "onHandleIntent")

        val data: String = intent?.getStringExtra("data") ?: ""

        //make the Intent work for 10 seconds
        for(i in 1..10) {
            Log.d(TAG, " Hey I am printing this -> $i and my data was $data ")
            SystemClock.sleep(1000)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        wakeLock.release()
        Log.d(TAG, "Wakelock released")
    }
}