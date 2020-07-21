package pl.nojkir.roomdatabase.other

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.Navigation.createNavigateOnClickListener
import androidx.navigation.Navigation.findNavController

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.exercise_item.view.*
import pl.nojkir.roomdatabase.R
import pl.nojkir.roomdatabase.data.db.entities.Exercise
import pl.nojkir.roomdatabase.ui.ExerciseViewModel
import pl.nojkir.roomdatabase.ui.fragments.ExercisesFragment

class ExerciseAdapter(
    var items : List<Exercise>,
    private val viewModel: ExerciseViewModel

) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {
     inner class ExerciseViewHolder (view : View) : RecyclerView.ViewHolder(view){
     }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.exercise_item, parent, false)


        return ExerciseViewHolder(view)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
    val currentExercise = items[position]
        holder.itemView.tv_TrainingName.text = currentExercise.trainingName
        holder.itemView.tv_ExerciseName.text = currentExercise.exerciseName
        holder.itemView.tvReps.text = "Reps: ${currentExercise.reps.toString()}"
        holder.itemView.tvSets.text = "Sets: ${currentExercise.amountOfSeries.toString()}"
        holder.itemView.tvWeight.text = "Weight: ${currentExercise.weight.toString()}"


        holder.itemView.btDelete.setOnClickListener {
            viewModel.delete(currentExercise)
        }

        holder.itemView.setOnClickListener(
            createNavigateOnClickListener(R.id.action_trainingsFragment_to_exercisesFragment)
        )




    }


}