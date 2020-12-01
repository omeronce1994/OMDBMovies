package nando.android.core.model.error

import android.content.Context
import androidx.annotation.StringRes
import nando.android.core.util.CoreConstants.UNKNOWN_ERROR

data class LocalizedErrorHandler(
    @StringRes private val stringId: Int
): ErrorHandler {

    override fun errorMessage(context: Context?) = context?.let {
        it.getString(stringId)
    } ?: UNKNOWN_ERROR
}