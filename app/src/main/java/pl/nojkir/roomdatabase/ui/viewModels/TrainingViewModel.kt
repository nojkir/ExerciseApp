package pl.nojkir.roomdatabase.ui.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.nojkir.roomdatabase.data.db.entities.Training
import pl.nojkir.roomdatabase.data.repositories.TrainingRepository

class TrainingViewModel @ViewModelInject constructor(
    private val repository: TrainingRepository
):ViewModel() {

    fun upsert(training: Training) = CoroutineScope(Dispatchers.Main).launch {
        repository.upsert(training)
    }

    fun delete(training: Training) = CoroutineScope(Dispatchers.Main).launch {
        repository.delete(training)
    }

    fun getAllTrainings() = repository.getAllTrainings()
}