package com.aligkts.peoplemanagement.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aligkts.peoplemanagement.R
import com.aligkts.peoplemanagement.data.local.datasource.FetchError
import com.aligkts.peoplemanagement.view.model.TextMessageUiModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

/**
 * Created by Ali Göktaş on 22.07.2022.
 */

abstract class BaseViewModel : ViewModel() {

    private val _failurePopup = MutableSharedFlow<TextMessageUiModel>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val failurePopup: Flow<TextMessageUiModel> = _failurePopup.distinctUntilChanged()

    private val _showProgress = MutableSharedFlow<Boolean>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val showProgress: Flow<Boolean> = _showProgress.distinctUntilChanged()

    protected open fun handleFailure(failure: FetchError?) = viewModelScope.launch {
        val messageUiModel = failure?.let {
            TextMessageUiModel.DynamicString(it.errorDescription)
        } ?: TextMessageUiModel.StringResource(R.string.unknown_error)
        _failurePopup.tryEmit(messageUiModel)
    }

    fun showProgress() {
        _showProgress.tryEmit(true)
    }

    fun hideProgress() {
        _showProgress.tryEmit(false)
    }
}