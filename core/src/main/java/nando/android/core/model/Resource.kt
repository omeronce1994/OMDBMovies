package nando.android.core.model

import nando.android.core.model.error.ErrorHandler

/**
 * Kotlin sealed class to represent processes states
 *
 * @param T
 */
sealed class Resource<T> {
   class Success<T>(val data: T) : Resource<T>()
   class Loading<T>() : Resource<T>()
   class Error<T>(val errorHandler: ErrorHandler) : Resource<T>()
}