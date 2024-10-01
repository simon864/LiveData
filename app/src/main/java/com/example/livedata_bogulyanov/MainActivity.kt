package com.example.livedata_bogulyanov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.livedata_bogulyanov.viewmodel.WeatherViewModel
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    lateinit var weatherViewModel: WeatherViewModel

    lateinit var buttonGetWeather : Button
    lateinit var textViewTemp : TextView
    lateinit var textViewDesc : TextView
    lateinit var textViewCity : TextView
    lateinit var editTextCity : EditText
    lateinit var imageViewIcon : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Picasso.get().setLoggingEnabled(true)

        buttonGetWeather = findViewById(R.id.button2)
        textViewTemp = findViewById(R.id.textView3)
        textViewDesc = findViewById(R.id.textView2)
        textViewCity = findViewById(R.id.textView)
        editTextCity = findViewById(R.id.editTextText2)
        imageViewIcon = findViewById(R.id.imageView)

        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        buttonGetWeather.setOnClickListener() {
            if (editTextCity.text.toString() != "") {
                weatherViewModel.retrofitAPI(editTextCity.text.toString())
            }
        }

        weatherViewModel.weatherData.observe(this, Observer {
            textViewTemp.text = it.main.temp.toString()
            textViewDesc.text = it.weather[0].description.uppercase()
            textViewCity.text = it.name

            Log.d("ICON", it.weather[0].icon)

            Picasso.get()
                .load("http://openweathermap.org/img/wn/${it.weather[0].icon}@2x.png")
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageViewIcon)
        })
    }
}