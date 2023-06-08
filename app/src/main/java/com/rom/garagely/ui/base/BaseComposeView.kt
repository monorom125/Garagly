package com.rom.garagely.ui.base

import androidx.compose.runtime.Composable

interface BaseComposeView {
    @Composable
    fun ShowMessageDialog(title: String?, message: String, isShow: Boolean, onDismiss: () -> Unit)

    @Composable
    fun ShowDecisionMessageDialog(
        title: String?,
        message: String,
        isShow: Boolean,
        onDismiss: () -> Unit,
        onConfirm: () -> Unit
    )

    fun showCreateUpdateDeleteMessageDialog(
        name: String,
        isUpdate: Boolean = false,
        isDelete: Boolean = false
    )

    fun showProgress()

    fun hideProgress()
    fun showProgressDialog()

    fun hideProgressDialog()
}