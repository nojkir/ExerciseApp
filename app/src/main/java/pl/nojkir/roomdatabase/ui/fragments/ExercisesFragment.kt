package pl.nojkir.roomdatabase.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_exercises.*
import kotlinx.android.synthetic.main.fragment_trainings.*
import pl.nojkir.roomdatabase.R
import pl.nojkir.roomdatabase.data.db.entities.Exercise
import pl.nojkir.roomdatabase.other.ExercisesAdapter
import pl.nojkir.roomdatabase.ui.ExerciseViewModel
import pl.nojkir.roomdatabase.ui.dialogs.AddDialogListener
import pl.nojkir.roomdatabase.ui.dialogs.AddExerciseDialog


@AndroidEntryPoint
class ExercisesFragment : Fragment(R.layout.fragment_exercises) {


    private val viewModel: ExerciseViewModel by viewModels()


    lateinit var trainingName: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        trainingName = requireArguments().getString("TrainingName").toString()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ExercisesAdapter(listOf(), viewModel)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter



        viewModel.findExercisesByTrainingName(trainingName).observe(viewLifecycleOwner, Observer {
            adapter.items = it
            adapter.notifyDataSetChanged()
        })

        buttonek.setOnClickListener {
            AddExerciseDialog(requireContext(), object : AddDialogListener {
                override fun onAddButtonClicked(exercise: Exercise) {
                    viewModel.upsert(exercise)
                }
            }).show()

        }








    }


}
