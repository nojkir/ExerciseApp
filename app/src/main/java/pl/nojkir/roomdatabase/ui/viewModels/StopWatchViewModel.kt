package pl.nojkir.roomdatabase.ui.viewModels

import androidx.lifecycle.ViewModel

class StopWatchViewModel :  ViewModel() {
    var isVisible : Boolean = true
    var startTime : Long? = null
    var isRunning : Boolean = false
    var infoText : String = ""
}