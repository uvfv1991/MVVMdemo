package com.example.kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.ViewModelProviders
import com.example.kotlin.util.InjectorUtil
import me.goldze.mvvmhabit.base.BaseActivity
import me.goldze.mvvmhabit.base.BaseViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModel = ViewModelProviders.of(this, InjectorUtil.getMainModelFactory()).get(MainViewModel::class.java)
        if (viewModel.isWeatherCached()) {
            val intent = Intent(this, WeatherActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            supportFragmentManager.beginTransaction().replace(R.id.container, ChooseAreaFragment()).commit()
        }
    }


}


