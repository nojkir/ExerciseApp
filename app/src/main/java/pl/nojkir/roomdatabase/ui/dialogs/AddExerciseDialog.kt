package pl.nojkir.roomdatabase.ui.dialogs

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import kotlinx.android.synthetic.main.dialog_add_exercise_item.*
import pl.nojkir.roomdatabase.R
import pl.nojkir.roomdatabase.data.db.entities.Exercise

class AddExerciseDialog (context: Context, var addDialogListener: AddDialogListener)
    :AppCompatDialog(context){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_exercise_item)


        btAdd.setOnClickListener {

            val trainingName = etTrainingName.text.toString()
            val exerciseName = etExerciseName.text.toString()
            val reps = etReps.text.toString()
            val sets = etSets.text.toString()
            val weight = etWeight.text.toString()
            if ( trainingName.isEmpty() || exerciseName.isEmpty() || reps.isBlank() || sets.isBlank() || weight.isBlank()){
                Toast.makeText(context, "Please enter all data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val exercise = Exercise(
                trainingName,
                exerciseName,
                reps.toInt(),
                weight.toDouble(),
                sets.toInt()
            )
            addDialogListener.onAddButtonClicked(exercise)
            dismiss()
        }




    }
}