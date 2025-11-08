package com.example.smartfit.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.smartfit.SmartFitApplication
import com.example.smartfit.di.AppGraph
import com.example.smartfit.ui.auth.LoginViewModel
import com.example.smartfit.ui.auth.OnboardingViewModel
import com.example.smartfit.ui.auth.SignUpViewModel
import com.example.smartfit.ui.logs.AddLogViewModel
import com.example.smartfit.ui.logs.LogDetailViewModel
import com.example.smartfit.ui.logs.LogsViewModel
import com.example.smartfit.ui.profile.ProfileViewModel
//import com.example.smartfit.ui.tips.TipsViewModel
import com.example.smartfit.ui.home.HomeViewModel

object AppViewModelProvider {

    val Factory = viewModelFactory {

        // --- Auth ---
        initializer {
            val graph = appGraph()
            LoginViewModel(graph.userRepo, graph.prefsRepo)
        }

        initializer {
            val graph = appGraph()
            SignUpViewModel(graph.userRepo, graph.prefsRepo)
        }

        // --- Logs list ---
        initializer {
            val graph = appGraph()
            LogsViewModel(graph.activityRepo)
        }

        // --- Log detail (needs SavedStateHandle to read "id") ---
        initializer {
            val graph = appGraph()
            LogDetailViewModel(
                repo = graph.activityRepo,
                savedStateHandle = this.createSavedStateHandle()  // contains "id" arg
            )
        }

        // --- Add log form ---
        initializer {
            val graph = appGraph()
            AddLogViewModel(graph.activityRepo)     // Add Log VM
        }

        /*// --- Tips ---
        initializer {
            val graph = appGraph()
            TipsViewModel(graph.tipsRepo)
        }*/

        // --- Profile (theme, goal, logout) ---
        initializer {
            val graph = appGraph()
            ProfileViewModel(graph.prefsRepo)
        }

        // --- Onboarding ---
        initializer {
            val graph = appGraph()
            OnboardingViewModel(graph.prefsRepo)
        }

        // --- Home (if you create one) ---
        initializer {
            val graph = appGraph()
            HomeViewModel(graph.activityRepo)
        }
    }
}

/** Resolve our Application → AppGraph from CreationExtras (same pattern as your sample). */
private fun CreationExtras.appGraph(): AppGraph {
    val app = (this[AndroidViewModelFactory.APPLICATION_KEY] as Application) as SmartFitApplication
    return app.graph
}

