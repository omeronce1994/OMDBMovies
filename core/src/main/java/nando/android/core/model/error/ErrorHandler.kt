package nando.android.core.model.error

import android.content.Context

/**
 * Interface used to handle Errors
 *
 */
interface ErrorHandler {

    /**
     * return error message
     *
     * @param context - use nullable context here so that we will be able to show localized error
     * via string resource id
     * @return
     */
    fun errorMessage(context: Context? = null): String
}