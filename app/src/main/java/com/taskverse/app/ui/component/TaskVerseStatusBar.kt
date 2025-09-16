package com.taskverse.app.ui.component

import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.SideEffect

@Composable
fun TaskVerseStatusBar() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = true // true nếu muốn icon và text status bar tối

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color(0xFFF5F0FF), // màu background giống màn hình
            darkIcons = useDarkIcons
        )
    }
}
