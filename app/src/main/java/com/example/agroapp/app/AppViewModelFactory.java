package com.example.agroapp.app;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.agroapp.repository.AgroRepository;
import com.example.agroapp.viewModel.DiscoverViewModel;
import com.example.agroapp.viewModel.HomeItemViewModel;
import com.example.agroapp.viewModel.HomeViewModel;
import com.example.agroapp.viewModel.NavigationViewModel;
import com.example.agroapp.viewModel.ProjectListViewModel;

/**
 * Created by goldze on 2019/3/26.
 */
public class AppViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @SuppressLint("StaticFieldLeak")
    private static volatile AppViewModelFactory INSTANCE;
    private final Application mApplication;
    private final AgroRepository mRepository;


    public static AppViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (AppViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppViewModelFactory(application, Injection.provideDemoRepository());
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    private AppViewModelFactory(Application application, AgroRepository repository) {
        this.mApplication = application;
        this.mRepository = repository;

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(mApplication, mRepository);
        }else  if (modelClass.isAssignableFrom(ProjectListViewModel.class)) {
            return (T) new ProjectListViewModel(mApplication, mRepository);
        }else  if (modelClass.isAssignableFrom(NavigationViewModel.class)) {
            return (T) new NavigationViewModel(mApplication, mRepository);
        }else  if (modelClass.isAssignableFrom(DiscoverViewModel.class)) {
            return (T) new DiscoverViewModel(mApplication, mRepository);
        }


        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
