package pl.nojkir.roomdatabase.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pl.nojkir.roomdatabase.R
import pl.nojkir.roomdatabase.other.Constants.ACTION_PAUSE_SERVICE
import pl.nojkir.roomdatabase.other.Constants.ACTION_START_OR_RESUME_SERVICE
import pl.nojkir.roomdatabase.other.Constants.ACTION_STOP_SERVICE
import pl.nojkir.roomdatabase.other.Constants.NOTIFICATION_CHANNEL_ID
import pl.nojkir.roomdatabase.other.Constants.NOTIFICATION_CHANNEL_NAME
import pl.nojkir.roomdatabase.ui.MainActivity

class StopWatchService : LifecycleService() {

    companion object{
        var timeInMillis = MutableLiveData<Long>()
        var isTracking = MutableLiveData<Boolean>()
    }
    private val timeInSeconds = MutableLiveData<Long>()
    private var isFirstRun : Boolean = true
    private val NOTIFICATION_ID = 1

    private fun postInitialValues(){
        isTracking.postValue(false)
        timeInMillis.postValue(0L)
        timeInSeconds.postValue(0L)

    }



    override fun onCreate() {
        postInitialValues()
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.let {
            when(it.action){
                ACTION_START_OR_RESUME_SERVICE ->
                    if (isFirstRun){
                        startForegroundService()
                        isFirstRun = false
                    }else{
                        startTimer()
                    }
                ACTION_PAUSE_SERVICE -> pausesService()
                ACTION_STOP_SERVICE -> Log.d("Action", "Stop service")
                else -> Log.d("Action", "Else branch")
            }

        }

        return super.onStartCommand(intent, flags, startId)
    }

    private var isTimerEnabled = false
    private var lapTime = 0L
    private var timeRun = 0L
    private var timeStarted = 0L
    private var lastSecondTimeStamp = 0L

    private fun startTimer(){
        isTracking.postValue(true)
        timeStarted = System.currentTimeMillis()
        isTimerEnabled = true
        CoroutineScope(Dispatchers.Main).launch {
            while (isTracking.value!!){
                //Time difference between now and timer started
                lapTime = System.currentTimeMillis() - timeStarted
                // Post new lap time
                timeInMillis.postValue(timeRun + lapTime)
                if (timeInMillis.value!! >= lastSecondTimeStamp +1000L){
                    timeInSeconds.postValue(timeInSeconds.value!! + 1)
                    lastSecondTimeStamp += 1000L
                }
                delay(50L)
            }
            timeRun += lapTime
        }
    }

    private fun pausesService(){
        isTracking.postValue(false)
        isTimerEnabled = false
    }

    private fun startForegroundService(){
        startTimer()
        isTracking.postValue(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotificationChannel(notificationManager)
        }

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Exercise App")
            .setContentText("00:00:00")
            .setSmallIcon(R.drawable.ic_timer)
            .setContentIntent(pendingIntent)
            .build()


        startForeground(NOTIFICATION_ID, notification)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel (notificationManager: NotificationManager){
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )

        notificationManager.createNotificationChannel(channel)
    }

}