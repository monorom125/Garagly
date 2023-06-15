package com.rom.garagely.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.fragment.app.Fragment
import com.rom.garagely.theme.GaragelyTheme

abstract class BaseComposeFragment : Fragment(), BaseComposeView {

    private var progressState = mutableStateOf(false)
    private var progressDialogState = mutableStateOf(false)

    @Composable
    abstract fun ContentView()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                GaragelyTheme {
                    val isLoading by remember { progressState }
                    val isDialogLoading by remember { progressDialogState }

                    ShowProgressDialog(isShow = isDialogLoading)

                    Box(modifier = Modifier.fillMaxSize()) {
                        ContentView()
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }

    override fun showProgress() {
        progressState.value = true
    }

    override fun hideProgress() {
        progressState.value = false
    }

    override fun showProgressDialog() {
        progressDialogState.value = true
    }

    override fun hideProgressDialog() {
        progressDialogState.value = false
    }

    @Composable
    override fun ShowMessageDialog(
        title: String?,
        message: String,
        isShow: Boolean,
        onDismiss: () -> Unit
    ) {
        ShowAlertDialog(
            title = title,
            message = message,
            isShow = isShow,
            onConfirm = onDismiss,
            onDismiss = null
        )
    }

    @Composable
    override fun ShowDecisionMessageDialog(
        title: String?,
        message: String,
        isShow: Boolean,
        onDismiss: () -> Unit,
        onConfirm: () -> Unit
    ) {
        ShowAlertDialog(
            title = title,
            message = message,
            isShow = isShow,
            onConfirm = onConfirm,
            onDismiss = onDismiss
        )
    }

    @Composable
    private fun ShowAlertDialog(
        title: String?,
        message: String,
        isShow: Boolean,
        onConfirm: () -> Unit,
        onDismiss: (() -> Unit)?
    ) {
        if (isShow) {
            AlertDialog(
                onDismissRequest = { onDismiss?.invoke() },
                title = if (title.isNullOrEmpty()) null else ({
                    Text(text = title)
                }),
                text = {
                    Text(text = message)
                },
                dismissButton = if (onDismiss != null) {
                    {
                        Button(
                            onClick = onDismiss,
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                            elevation = null
                        ) {
                            Text(text = "Cancel", color = Color.Red)
                        }
                    }
                } else null,
                confirmButton = {
                    Button(
                        onClick = onConfirm,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        elevation = null
                    ) {
                        Text(text = "Ok", color = Color.Red)
                    }
                }
            )
        }
    }

    @Composable
    private fun ShowProgressDialog(isShow: Boolean) {
        if (isShow) {
            Dialog(
                onDismissRequest = {
                    progressDialogState.value = false
                },
                properties = DialogProperties(
                    dismissOnBackPress = false,
                    dismissOnClickOutside = false
                )
            ) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .background(Color.Transparent)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}