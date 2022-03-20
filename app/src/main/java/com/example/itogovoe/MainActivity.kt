package com.example.itogovoe

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.itogovoe.data.api.RetrofitInstance.repository
import com.example.itogovoe.databinding.ActivityMainBinding
import com.example.itogovoe.ui.main.MainViewModel
import com.example.itogovoe.ui.main.MainViewModelFactory

// TODO: Сделать адаптер и вывод на главный экран валют
// TODO: Сделать БД на корутинах с localDataSource

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navController = findNavController(R.id.fragmentContainerView)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.historyFragment,
                R.id.analyticsFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavigationView.setupWithNavController(navController)


        /*val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.getCurrency()
        viewModel.liveData.observe(this) { response ->
            if (response != null) {
                Log.d("MY_TAG", response.currencyList.toString())
            } else {
                Log.d("MY_TAG", "response = null")
            }
        }*/
    }
}