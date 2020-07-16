package pl.nojkir.roomdatabase.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pl.nojkir.roomdatabase.data.repositories.ExerciseRepository

class ExerciseViewModelFactory(
    private val repository: ExerciseRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ExerciseViewModel(repository) as T
    }
}