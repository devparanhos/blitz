package br.com.detran.blitz.core.time

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import br.com.detran.blitz.core.extension.timeFilter

class TimeTransformation() : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return text.timeFilter()
    }
}