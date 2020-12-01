package nando.android.core.model.error

import android.content.Context
import nando.android.core.util.CoreConstants.UNKNOWN_ERROR

class ExceptionErrorHandler(
    private val throwable: Throwable
): ErrorHandler {
    override fun errorMessage(context: Context?): String = throwable.message ?: UNKNOWN_ERROR
}