package com.karem.core

sealed class ProgressBarState{
    object Loading: ProgressBarState()

    object Idle: ProgressBarState()
}
