package pl.nojkir.roomdatabase.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.nojkir.roomdatabase.data.db.entities.Exercise
import pl.nojkir.roomdatabase.data.repositories.ExerciseRepository

class ExerciseViewModel @ViewModelInject constructor(
    private val repository : ExerciseRepository
) :  ViewModel(){
        fun upsert ( exercise: Exercise) = CoroutineScope(Dispatchers.Main).launch {
            repository.upsert(exercise)
        }
        fun delete ( exercise: Exercise) = CoroutineScope(Dispatchers.Main).launch {
        repository.delete(exercise)
    }



        fun getAllExercise() = repository.getAllExercise()


}