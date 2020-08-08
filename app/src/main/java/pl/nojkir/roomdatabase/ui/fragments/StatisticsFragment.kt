package pl.nojkir.roomdatabase.ui.fragments

import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Chronometer
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_statistics.*
import pl.nojkir.roomdatabase.R
import pl.nojkir.roomdatabase.data.db.entities.Exercise
import pl.nojkir.roomdatabase.ui.ExerciseViewModel


@AndroidEntryPoint
class StatisticsFragment : Fragment (R.layout.fragment_statistics) {

    private val viewModel: ExerciseViewModel by viewModels()
    private lateinit var trainings : List<Exercise>
    private var volume : Double = 0.0

    lateinit var trainingName: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


            trainingName = requireArguments().getString("TrainingName").toString()



    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)






            viewModel.findExercisesByTrainingName(trainingName)
                .observe(viewLifecycleOwner, Observer {
                    trainings = it
                    for (training in trainings) {
                        volume += training.weight * training.reps * training.amountOfSeries
                    }
                    statistics_VolumeTV.text = volume.toString()
                    statistics_trainingTV.text = trainingName

                })







    }

}