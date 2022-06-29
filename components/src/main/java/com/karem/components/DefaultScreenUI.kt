package com.karem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.karem.core.ProgressBarState
import com.karem.core.Queue
import com.karem.core.UIComponent

@Composable
fun DefaultScreenUI(
    queue: Queue<UIComponent> = Queue(mutableListOf()),
    progressBarState: ProgressBarState,
    onRemoveHeadFromDialog: () -> Unit,
    content: @Composable () -> Unit,
){
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
            ){
            content()
            if(progressBarState is ProgressBarState.Loading){
                CircularProgressBar()
            }
            if (!queue.isEmpty()){
                queue.peek()?.let { uiComponent ->
                    if (uiComponent is UIComponent.Dialog)
                    GenericDialog(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        title = uiComponent.title,
                        description = uiComponent.description,
                        onRemoveHeadFromQueue = onRemoveHeadFromDialog
                    )
                }
            }
        }
    }
}