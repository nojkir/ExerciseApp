package pl.nojkir.roomdatabase.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.PendingIntent.getService
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pl.nojkir.roomdatabase.R
import pl.nojkir.roomdatabase.other.Constants.ACTION_PAUSE_SERVICE
import pl.nojkir.roomdatabase.other.Constants.ACTION_SHOW_STOPWATCH_FRAGMENT
import pl.nojkir.roomdatabase.other.Constants.ACTION_START_OR_RESUME_SERVICE
import pl.nojkir.roomdatabase.other.Constants.ACTION_STOP_SERVICE
import pl.nojkir.roomdatabase.other.Constants.NOTIFICATION_CHANNEL_ID
import pl.nojkir.roomdatabase.other.Constants.NOTIFICATION_CHANNEL_NAME
import pl.nojkir.roomdatabase.ui.MainActivity
import pl.nojkir.roomdatabase.ui.fragments.StopWatchFragment
import java.util.concurrent.TimeUnit
import javax.inject.Inject
@AndroidEntryPoint
class StopWatchService : LifecycleService() {

    @Inject
    lateinit var baseNotificationBuilder : NotificationCompat.Builder

    lateinit var currentNotificationBuilder : NotificationCompat.Builder

    companion object{
        var timeInMillis = MutableLiveData<Long>()
        var isTracking = MutableLiveData<Boolean>()
    }
    var serviceKilled = false
    private val timeInSeconds = MutableLiveData<Long>()
    private var isFirstRun : Boolean = true
    private val NOTIFICATION_ID = 1

    private fun postInitialValues(){
        isTracking.postValue(false)
        timeInMillis.postValue(0L)
        timeInSeconds.postValue(0L)

    }



    override fun onCreate() {
        super.onCreate()
        currentNotificationBuilder = baseNotificationBuilder
        postInitialValues()
        isTracking.observe(this, Observer {
            updateNotificationTrackingState(it)
        })

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
                ACTION_STOP_SERVICE -> {
                    Log.d("Action", "Stop service")
                    killService()
                }
                else -> Log.d("Action", "Else branch")

            }

        }

        return super.onStartCommand(intent, flags, startId)
    }
    private fun killService(){
        serviceKilled = true
        isFirstRun = true
        pausesService()
        postInitialValues()
        stopForeground(false)
        stopSelf()
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
        startForeground(NOTIFICATION_ID, baseNotificationBuilder.build())

        timeInSeconds.observe(this, Observer {
            if (!serviceKilled) {
                val notification = currentNotificationBuilder
                    .setContentText(getFormattedStopWatchTime(it * 1000))
                notificationManager.notify(NOTIFICATION_ID, notification.build())
            }
        })


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

private fun updateNotificationTrackingState( isTracking: Boolean){
    val notificationActionText = if (isTracking) "Pause" else "Resume"
    val pendingIntent = if (isTracking){
        val pauseIntent = Intent(this, StopWatchService::class.java).apply {
            action = ACTION_PAUSE_SERVICE
        }
        PendingIntent.getService(this, 1 , pauseIntent, FLAG_UPDATE_CURRENT)
    } else {
        val resumeIntent = Intent(this, StopWatchService::class.java).apply {
            action = ACTION_START_OR_RESUME_SERVICE
        }
        getService(this, 2, resumeIntent, FLAG_UPDATE_CURRENT)
    }

    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    currentNotificationBuilder.javaClass.getDeclaredField("mActions").apply {
        isAccessible = true
        set(currentNotificationBuilder, ArrayList<NotificationCompat.Action>())
    }
        if (!serviceKilled) {
            currentNotificationBuilder = baseNotificationBuilder
                .addAction(R.drawable.ic_timer, notificationActionText, pendingIntent)
            notificationManager.notify(NOTIFICATION_ID, currentNotificationBuilder.build())
        }
}
    fun getFormattedStopWatchTime(ms: Long, includeMillis : Boolean = false) :String{
        var milliseconds = ms
        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
        milliseconds -= TimeUnit.HOURS.toMillis(hours)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
        milliseconds -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)
        if (!includeMillis){
            return "${if (hours < 10) "0" else ""}$hours:" +
                    "${if (minutes < 10) "0" else ""}$minutes:" +
                    "${if (seconds < 10) "0" else ""}$seconds"
        }
        milliseconds -= TimeUnit.SECONDS.toMillis(seconds)
        milliseconds /= 10

        return "${if (hours < 10) "0" else ""}$hours:" +
                "${if (minutes < 10) "0" else ""}$minutes:" +
                "${if (seconds < 10) "0" else ""}$seconds:" +
                "${if (milliseconds < 10) "0" else ""}$milliseconds"
    }

}