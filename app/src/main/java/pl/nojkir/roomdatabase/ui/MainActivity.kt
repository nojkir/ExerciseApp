package pl.nojkir.roomdatabase.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import pl.nojkir.roomdatabase.other.ExerciseAdapter
import pl.nojkir.roomdatabase.R
import pl.nojkir.roomdatabase.data.ExerciseDataBase
import pl.nojkir.roomdatabase.data.db.entities.Exercise
import pl.nojkir.roomdatabase.data.repositories.ExerciseRepository


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
               setSupportActionBar(findViewById(R.id.toolbar))

        val dataBase = ExerciseDataBase(this)
        val repository =
            ExerciseRepository(dataBase)
        val factory =
            ExerciseViewModelFactory(repository)

        val viewModel = ViewModelProvider (this, factory).get(ExerciseViewModel::class.java)
        val adapter =
            ExerciseAdapter(listOf(), viewModel)


        viewModel.getAllExercise().observe(this, Observer {
            adapter.items = it
            adapter.notifyDataSetChanged()
        })


    }
}