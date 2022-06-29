package com.karem.core

sealed class UIComponentState {
    object Show: UIComponentState()

    object Hide: UIComponentState()
}