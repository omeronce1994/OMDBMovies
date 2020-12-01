package nando.android.core.model.db.util

import nando.android.core.model.Resource
import nando.android.core.model.error.ExceptionErrorHandler
import java.lang.Exception

suspend fun <T> safeDbCall(action: suspend () -> T): Resource<T> = try {
    Resource.Success(action())
} catch (e: Exception) {
    Resource.Error(ExceptionErrorHandler(e))
}