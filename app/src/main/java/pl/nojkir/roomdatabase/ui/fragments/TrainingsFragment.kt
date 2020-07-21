package pl.nojkir.roomdatabase.ui.fragments

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_trainings.*
import pl.nojkir.roomdatabase.R
import pl.nojkir.roomdatabase.data.ExerciseDataBase
import pl.nojkir.roomdatabase.data.db.entities.Exercise
import pl.nojkir.roomdatabase.data.repositories.ExerciseRepository
import pl.nojkir.roomdatabase.other.ExerciseAdapter
import pl.nojkir.roomdatabase.ui.AddDialogListener
import pl.nojkir.roomdatabase.ui.AddExerciseDialog
import pl.nojkir.roomdatabase.ui.ExerciseViewModel



@AndroidEntryPoint
class TrainingsFragment :  Fragment (R.layout.fragment_trainings){

    private val viewModel : ExerciseViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




            val adapter = ExerciseAdapter(listOf(), viewModel)


            rvExercises.layoutManager = LinearLayoutManager(requireContext())
            rvExercises.adapter = adapter


                viewModel.getAllExercise().observe(viewLifecycleOwner, Observer {
                    adapter.items = it
                    adapter.notifyDataSetChanged()
                })


            button.setOnClickListener {
                    AddExerciseDialog(requireContext(), object :
                        AddDialogListener {
                        override fun onAddButtonClicked(exercise: Exercise) {
                            viewModel.upsert(exercise)
                        }
                    }).show()

            }
        }




}
