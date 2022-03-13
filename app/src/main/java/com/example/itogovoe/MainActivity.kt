package com.example.itogovoe

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.itogovoe.data.api.RetrofitInstance.repository
import com.example.itogovoe.ui.main.MainViewModel
import com.example.itogovoe.ui.main.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        /*
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("http://data.fixer.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CurrencyApi::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val currencies = service.getCurrencies()
            Log.d("MY_TAG", "$currencies")
        }*/

        /*val retrofit = Retrofit.Builder()
            .baseUrl("http://data.fixer.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CurrencyApi::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val currencies = service.getCurrencies()
            print(currencies)
        }*/
    }
}