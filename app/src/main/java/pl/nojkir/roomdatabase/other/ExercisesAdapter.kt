package pl.nojkir.roomdatabase.other

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.exercise_item.view.*
import pl.nojkir.roomdatabase.R
import pl.nojkir.roomdatabase.data.db.entities.Exercise
import pl.nojkir.roomdatabase.ui.viewModels.ExerciseViewModel

class ExercisesAdapter(
    var items: List<Exercise>,
    private val viewModel: ExerciseViewModel

) : RecyclerView.Adapter<ExercisesAdapter.TrainingViewHolder>() {

    inner class TrainingViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExercisesAdapter.TrainingViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.exercise_item, parent, false)


        return TrainingViewHolder(view)
    }

    override fun getItemCount(): Int {

        return items.size

    }

    override fun onBindViewHolder(holder: ExercisesAdapter.TrainingViewHolder, position: Int) {
        val currentExercise = items[position]

        holder.itemView.exerciseNameTextView.text = currentExercise.exerciseName
        holder.itemView.tVReps.text = "Reps: ${currentExercise.reps.toString()}"
        holder.itemView.tVSets.text = "Sets: ${currentExercise.amountOfSeries.toString()}"
        holder.itemView.tVWeight.text = "Weight ${currentExercise.weight.toString()}"




        holder.itemView.deleteButton.setOnClickListener {
            viewModel.delete(currentExercise)
        }


    }


}
