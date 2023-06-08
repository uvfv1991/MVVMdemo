package com.example.kotlin.app

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.agroapp.repository.AgroRepository
import com.example.agroapp.viewModel.DiscoverViewModel
import com.example.agroapp.viewModel.HomeViewModel
import com.example.agroapp.viewModel.NavigationViewModel
import com.example.agroapp.viewModel.ProjectListViewModel

/**
 * Created by goldze on 2019/3/26.
 */
class AppViewModelFactory private constructor(private val mApplication: Application, repository: AgroRepository) : ViewModelProvider.NewInstanceFactory() {
    private val mRepository: AgroRepository
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(mApplication, mRepository) as T
        } else if (modelClass.isAssignableFrom(ProjectListViewModel::class.java)) {
            return ProjectListViewModel(mApplication, mRepository) as T
        } else if (modelClass.isAssignableFrom(NavigationViewModel::class.java)) {
            return NavigationViewModel(mApplication, mRepository) as T
        } else if (modelClass.isAssignableFrom(DiscoverViewModel::class.java)) {
            return DiscoverViewModel(mApplication, mRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: AppViewModelFactory? = null
        fun getInstance(application: Application): AppViewModelFactory? {
            if (INSTANCE == null) {
                synchronized(AppViewModelFactory::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = AppViewModelFactory(application, Injection.provideDemoRepository())
                    }
                }
            }
            return INSTANCE
        }

        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }

    init {
        mRepository = repository
    }
}