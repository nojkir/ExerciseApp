package pl.nojkir.roomdatabase.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_statistics.*
import pl.nojkir.roomdatabase.R
import pl.nojkir.roomdatabase.data.db.entities.Exercise
import pl.nojkir.roomdatabase.ui.viewModels.ExerciseViewModel


@AndroidEntryPoint
class StatisticsFragment : Fragment(R.layout.fragment_statistics) {

    private val viewModel: ExerciseViewModel by viewModels()
    private lateinit var trainings: List<Exercise>
    private var volume: Double = 0.0
    private var reps: Int = 0
    private var sets: Int = 0
    private var exercises: Int = 0

    lateinit var trainingId: String
    lateinit var trainingName: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        trainingId = requireArguments().getString("TrainingId").toString()
        trainingName = requireArguments().getString("TrainingName").toString()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)






        viewModel.findExercisesByTrainingId(trainingId.toInt())
            .observe(viewLifecycleOwner, Observer {
                trainings = it
                for (training in trainings) {
                    volume += training.weight * training.reps * training.amountOfSeries
                    reps += training.reps
                    sets += training.amountOfSeries
                    exercises = trainings.size
                }
                statistics_trainingTV.text = trainingName
                statistics_VolumeTV.text = "Total training volume ${volume.toString()} kg"
                statistics_exercisesTV.text = "Exercises in training ${exercises.toString()}"
                statistics_repsTV.text = "Reps in training ${reps.toString()}"
                statistics_setsTV.text = "Sets in training ${sets.toString()}"


            })


    }

}