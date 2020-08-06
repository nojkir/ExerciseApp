package pl.nojkir.roomdatabase.ui.fragments

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import pl.nojkir.roomdatabase.R

@AndroidEntryPoint
class StopWatchFragment : Fragment(R.layout.fragment_stopwatch) {
    private lateinit var chronometer: Chronometer
    private lateinit var start : Button
    private lateinit var pause : Button
    private lateinit var reset : Button
    private var pauseOffset : Long = 0
    private var isRunning : Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         val view = inflater.inflate(R.layout.fragment_stopwatch, container, false)
        chronometer = view.findViewById(R.id.chronometer)

         start = view.findViewById(R.id.button_start)
         pause = view.findViewById(R.id.button_pause)
         reset = view.findViewById(R.id.button_reset)

        chronometer.base = SystemClock.elapsedRealtime()

        start.setOnClickListener { startChronometer() }
        pause.setOnClickListener { pauseChronometer() }
        reset.setOnClickListener { resetChronometer() }

        return view
    }



        fun startChronometer(){
                if (!isRunning) {
                    chronometer.base = SystemClock.elapsedRealtime() - pauseOffset
                    chronometer.start()
                    isRunning = true
                }

        }

        fun pauseChronometer () {
            if (isRunning) {
                chronometer.stop()
                pauseOffset = SystemClock.elapsedRealtime() - chronometer.base
                isRunning = false
            }
        }

            fun resetChronometer(){
                chronometer = requireView().findViewById(R.id.chronometer)
                chronometer.base = SystemClock.elapsedRealtime()
                pauseOffset = 0
            }


    override fun onSaveInstanceState(outState: Bundle) {
        outState.putLong("time", pauseOffset)
        super.onSaveInstanceState(outState)

    }
        }



