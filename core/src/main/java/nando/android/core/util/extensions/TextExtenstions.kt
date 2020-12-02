package nando.android.core.util.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun TextView.debounceTextChange(
    debounce: Long = 300,
    scope: CoroutineScope,
    listener: (String) -> Unit
) {
    addTextChangedListener(object : TextWatcher {

        private var  searchFor = ""

        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val searchedText = s.toString().trim()
            if (searchedText == searchFor) {
                return
            }
            searchFor = searchedText
            scope.launch {
                delay(debounce)
                if (searchedText != searchFor) {
                    return@launch
                }
                else {
                    listener.invoke(s.toString())
                }
            }
        }
    })
}