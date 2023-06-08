package com.example.kotlin.app

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin.base.AppRepository
import com.example.kotlin.ui.HomeViewModel

//viewmodel的创建工厂
@Suppress("UNCHECKED_CAST")
class AppViewModelFactory (private val repository:  AppRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return HomeViewModel(repository) as T
    }
}