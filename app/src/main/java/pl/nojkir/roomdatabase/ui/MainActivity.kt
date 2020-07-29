package pl.nojkir.roomdatabase.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import pl.nojkir.roomdatabase.R


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel : ExerciseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.navHostFragment)

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.statisticsFragment, R.id.trainingsFragment,R.id.stopWatchFragment, R.id.exercisesFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)

            bottomNavigationView.setupWithNavController(navController)
        



    }
}