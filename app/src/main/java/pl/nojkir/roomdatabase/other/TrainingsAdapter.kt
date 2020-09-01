package pl.nojkir.roomdatabase.other

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation.createNavigateOnClickListener

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.trening_item.view.*
import pl.nojkir.roomdatabase.R
import pl.nojkir.roomdatabase.data.db.entities.Training
import pl.nojkir.roomdatabase.ui.viewModels.ExerciseViewModel
import pl.nojkir.roomdatabase.ui.viewModels.TrainingViewModel

class TrainingsAdapter(
    var items: List<Training>,
    private val viewModel: TrainingViewModel

) : RecyclerView.Adapter<TrainingsAdapter.ExerciseViewHolder>() {
    inner class ExerciseViewHolder(view: View) : RecyclerView.ViewHolder(view) {


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.trening_item, parent, false)


        return ExerciseViewHolder(view)

    }

    override fun getItemCount(): Int {


        return items.size
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {

        val currentTraining = items[position]

        holder.itemView.tv_TrainingName.text = currentTraining.trainingName


        val bundle = bundleOf(
            "TrainingId" to currentTraining.id.toString(),
            "TrainingName" to currentTraining.trainingName

        )

        holder.itemView.btDelete.setOnClickListener {
            viewModel.delete(currentTraining)

        }

        holder.itemView.goToStatsButton.setOnClickListener(
            createNavigateOnClickListener(
                R.id.action_trainingsFragment_to_statisticsFragment2, bundle
            )
        )


        holder.itemView.setOnClickListener(
            createNavigateOnClickListener(
                R.id.action_trainingsFragment_to_exercisesFragment, bundle
            )

        )

    }


}
