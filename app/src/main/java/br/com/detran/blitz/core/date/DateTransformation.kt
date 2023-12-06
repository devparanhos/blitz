package br.com.detran.blitz.core.date

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import br.com.detran.blitz.core.extension.dateFilter

class DateTransformation() : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return text.dateFilter()
    }
}


