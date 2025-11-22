package com.example.smartfit.ui.navigation

@kotlinx.serialization.Serializable
sealed interface Dest {
    @kotlinx.serialization.Serializable data object Onboarding : Dest
    @kotlinx.serialization.Serializable data object Login : Dest
    @kotlinx.serialization.Serializable data object SignUp : Dest
    @kotlinx.serialization.Serializable data object Home : Dest
    @kotlinx.serialization.Serializable data object Logs : Dest
    @kotlinx.serialization.Serializable data class LogDetail(val id: Long) : Dest
    @kotlinx.serialization.Serializable data object AddLog : Dest
    @kotlinx.serialization.Serializable data object Tips : Dest
    @kotlinx.serialization.Serializable data object Profile : Dest
    @kotlinx.serialization.Serializable data object ActivityStats : Dest
}
