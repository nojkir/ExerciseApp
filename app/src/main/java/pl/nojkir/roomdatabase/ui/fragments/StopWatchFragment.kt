package pl.nojkir.roomdatabase.ui.fragments

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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_stopwatch.*
import pl.nojkir.roomdatabase.R
import pl.nojkir.roomdatabase.ui.StopWatchViewModel

@AndroidEntryPoint
class StopWatchFragment : Fragment(R.layout.fragment_stopwatch) {
    private lateinit var stopwatch: Chronometer
    private lateinit var start : ImageButton
    private lateinit var pause : ImageButton
    private lateinit var reset : ImageButton


    private val viewModel: StopWatchViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         val view = inflater.inflate(R.layout.fragment_stopwatch, container, false)
        stopwatch = view.findViewById(R.id.chronometer)

        start = view.findViewById(R.id.button_start)
        pause = view.findViewById(R.id.button_pause)
        reset = view.findViewById(R.id.button_reset)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (viewModel.startTime == null){
            viewModel.startTime = 0

        }else{
            if (!viewModel.isVisible){
                stopwatch.visibility = View.GONE
                info_TV.text = viewModel.infoText
                info_TV.visibility = View.VISIBLE
            }
            stopwatch.base = viewModel.startTime!!
            stopwatch.start()
        }


        start.setOnClickListener { startChronometer() }
        pause.setOnClickListener { pauseChronometer() }
        reset.setOnClickListener { resetChronometer() }

    }



        fun startChronometer(){
                if (!viewModel.isRunning) {
                    stopwatch.apply {
                        base = SystemClock.elapsedRealtime() - viewModel.startTime!!
                        stopwatch.visibility = View.VISIBLE
                        start()
                    }
                   viewModel.apply {
                       startTime = stopwatch.base
                       isRunning = true
                       isVisible = true
                   }
                    info_TV.visibility = View.GONE
                }

        }

        fun pauseChronometer () {
            if (viewModel.isRunning) {
                stopwatch.apply {
                    stop()
                    visibility = View.GONE
                }
                viewModel.apply {
                    isRunning = false
                    startTime = SystemClock.elapsedRealtime() - stopwatch.base
                    isVisible = false
                    infoText = "Tap start to resume"
                }
                info_TV.apply {
                    text = viewModel.infoText + " " + viewModel.startTime!!/1000 + " seconds"
                    visibility = View.VISIBLE
                }

            }
        }

            fun resetChronometer(){
                stopwatch.apply {
                    base = SystemClock.elapsedRealtime()
                    stop()
                    visibility = View.GONE
                }

                viewModel.apply {
                    startTime = 0
                    isVisible = false
                    isRunning = false
                    infoText = "Tap start to start"
                }


                info_TV.apply {
                    text = viewModel.infoText
                    visibility = View.VISIBLE
                }

            }

            }







