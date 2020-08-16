package pl.nojkir.roomdatabase.services

import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData

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

}