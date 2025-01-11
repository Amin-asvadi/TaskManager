package com.saba.design_system.snackbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.saba.common_ui_resources.R


@Composable
fun AppnackBarScreen(
    modifier: Modifier=Modifier,
    seccondModifer: Modifier=Modifier,
    snackbarHostState: SnackbarHostState =SnackbarHostState(),
    message: String,
    dissmiss :()-> Unit ={},
    isSuccess: Boolean = false
) {


    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 50.dp)
    ) {
        // Snackbar Host to display the custom Snackbar
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.TopCenter),
            snackbar = { data ->
                AppSnackbar(
                    message = message,
                    modifier = seccondModifer,
                    messageFontSize = 14,
                    backgroundColor = if (isSuccess) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer,
                    borderColor = if (isSuccess) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.error,
                    btnFontSize = 12,
                    btnTxtColor = if (isSuccess) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.error,
                    isVisibleBtn = true,
                    btnTxt = "متوجه شدم",
                    icon = {
                        Image(
                            painter = painterResource(id = if (isSuccess) R.drawable.information_green else R.drawable.information_red),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp)
                        )
                    }) {
                    dissmiss()

                }
            }
        )
    }
}
