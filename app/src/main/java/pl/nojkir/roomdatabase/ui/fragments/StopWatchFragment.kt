package pl.nojkir.roomdatabase.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_stopwatch.*
import pl.nojkir.roomdatabase.R
import pl.nojkir.roomdatabase.other.Constants.ACTION_PAUSE_SERVICE
import pl.nojkir.roomdatabase.other.Constants.ACTION_START_OR_RESUME_SERVICE
import pl.nojkir.roomdatabase.other.Constants.ACTION_STOP_SERVICE
import pl.nojkir.roomdatabase.services.StopWatchService
import pl.nojkir.roomdatabase.ui.StopWatchViewModel
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class StopWatchFragment : Fragment(R.layout.fragment_stopwatch) {
    private lateinit var stopwatch: Chronometer
    private lateinit var start: ImageButton
    private lateinit var pause: ImageButton
    private lateinit var reset: ImageButton

    private var isTracking = false

    private var currentTimeInMillis = 0L

    private val viewModel: StopWatchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stopwatch, container, false)


        start = view.findViewById(R.id.button_start)
        pause = view.findViewById(R.id.button_pause)
        reset = view.findViewById(R.id.button_reset)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pause.setOnClickListener {
            if (isTracking) {
                sendCommandToService(ACTION_PAUSE_SERVICE)
            }
        }
        start.setOnClickListener {
            sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
        }

        reset.setOnClickListener {
            sendCommandToService(ACTION_STOP_SERVICE)
        }

        subscribe()
    }

    private fun subscribe() {
        StopWatchService.isTracking.observe(viewLifecycleOwner, Observer {
            updateTracking(it)
        })

        StopWatchService.timeInMillis.observe(viewLifecycleOwner, Observer {
            currentTimeInMillis = it
            val formattedText = getFormattedStopWatchTime(currentTimeInMillis, true)
            tv_Timer.text = formattedText
        })


    }

    private fun updateTracking(isTracking: Boolean) {
        this.isTracking = isTracking

    }

    fun getFormattedStopWatchTime(ms: Long, includeMillis: Boolean = false): String {
        var milliseconds = ms
        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
        milliseconds -= TimeUnit.HOURS.toMillis(hours)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
        milliseconds -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)
        if (!includeMillis) {
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

    private fun sendCommandToService(action: String) {
        Intent(requireContext(), StopWatchService::class.java).also {
            it.action = action
            requireContext().startService(it)
        }
    }
}








