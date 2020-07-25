package pl.nojkir.roomdatabase.ui.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_exercises.*
import kotlinx.android.synthetic.main.fragment_trainings.*
import pl.nojkir.roomdatabase.R
import pl.nojkir.roomdatabase.ui.ExerciseViewModel

@AndroidEntryPoint
class ExercisesFragment : Fragment(R.layout.fragment_exercises) {


    private val viewModel: ExerciseViewModel by viewModels()
    lateinit var trainingName: String
    lateinit var exerciseName: String
    lateinit var reps: String
    lateinit var sets: String
    lateinit var weight: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        trainingName = requireArguments().getString("TrainingName").toString()
        exerciseName = requireArguments().getString("ExerciseName").toString()
        reps = requireArguments().getString("Reps").toString()
        sets = requireArguments().getString("Sets").toString()
        weight = requireArguments().getString("Weight").toString()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        view.findViewById<TextView>(R.id.training).text = trainingName
        view.findViewById<TextView>(R.id.exercise).text = exerciseName
        view.findViewById<TextView>(R.id.dispaySets).text = sets
        view.findViewById<TextView>(R.id.displayReps).text = reps
        view.findViewById<TextView>(R.id.displayWeight).text = weight

        button2.setOnClickListener {

        }


    }


}
