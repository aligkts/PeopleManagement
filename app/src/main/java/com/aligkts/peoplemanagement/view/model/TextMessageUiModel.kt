package com.aligkts.peoplemanagement.view.model

import android.content.Context
import androidx.annotation.StringRes

/**
 * Created by Ali Göktaş on 22.07.2022.
 */

sealed class TextMessageUiModel {
    data class DynamicString(val value: String) : TextMessageUiModel()
    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any
    ) : TextMessageUiModel()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(resId, *args)
        }
    }
}