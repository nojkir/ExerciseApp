package pl.nojkir.roomdatabase.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_trainings.*
import pl.nojkir.roomdatabase.R
import pl.nojkir.roomdatabase.data.db.entities.Exercise
import pl.nojkir.roomdatabase.data.db.entities.Training
import pl.nojkir.roomdatabase.other.TrainingsAdapter
import pl.nojkir.roomdatabase.ui.dialogs.AddDialogListener
import pl.nojkir.roomdatabase.ui.dialogs.AddExerciseDialog
import pl.nojkir.roomdatabase.ui.viewModels.ExerciseViewModel
import pl.nojkir.roomdatabase.ui.viewModels.TrainingViewModel


@AndroidEntryPoint
class TrainingsFragment : Fragment(R.layout.fragment_trainings) {

    private val viewModel: ExerciseViewModel by viewModels()
    private val trainingViewModel : TrainingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TrainingsAdapter(listOf(), trainingViewModel)

        rvExercises.layoutManager = LinearLayoutManager(requireContext())
        rvExercises.adapter = adapter



        trainingViewModel.getAllTrainings().observe(viewLifecycleOwner, Observer {
            adapter.items = it
            adapter.notifyDataSetChanged()
        })




FAB_ADD_TRAINING.setOnClickListener {
    showAddTrainingDialog()
}

//        button.setOnClickListener {
//            AddExerciseDialog(requireContext(), object : AddDialogListener {
//                override fun onAddButtonClicked(exercise: Exercise) {
//                    viewModel.upsert(exercise)
//                }
//            }).show()


//        }
    }


    fun showAddTrainingDialog(){
        val dialog = MaterialDialog(requireContext())
            .noAutoDismiss()
            .customView(R.layout.training_dialog)

        dialog.show {
            positiveButton (text = "Save"){
                val editText = findViewById<EditText>(R.id.ETaddTrainingName)
                val trainingName = editText.text.toString()
                val training = Training(trainingName)
                trainingViewModel.upsert(training)
                dialog.dismiss()
            }

            negativeButton {
                dialog.dismiss()
            }

        }
    }

}
