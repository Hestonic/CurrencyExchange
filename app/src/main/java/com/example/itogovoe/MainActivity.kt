package com.example.itogovoe

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.itogovoe.data.api.RetrofitInstance.repository
import com.example.itogovoe.databinding.ActivityMainBinding
import com.example.itogovoe.ui.main.MainViewModel
import com.example.itogovoe.ui.main.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        /*supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, HomeFragment())
            .commit()

        binding.home.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }

        binding.history.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, HistoryFragment())
                .commit()
        }

        binding.analytics.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, AnalyticsFragment())
                .commit()
        }*/


        val navController = findNavController(R.id.fragmentContainerView)
        binding.bottomNavigationView.setupWithNavController(navController)


        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.getCurrency()
        viewModel.myResponce.observe(this) { response ->
            if (response.isSuccessful) {
                Log.d("MY_TAG", response.body()?.success.toString())
                Log.d("MY_TAG", response.body()?.timestamp.toString())
                Log.d("MY_TAG", response.body()?.base.toString())
                Log.d("MY_TAG", response.body()?.date.toString())
                Log.d("MY_TAG", response.body()?.rates.toString())
                Log.d("MY_TAG", response.body().toString())
            } else {
                Log.d("MY_TAG", response.errorBody().toString())
            }
        }
    }
}