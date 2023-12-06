package br.com.detran.blitz.core.extension

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText

fun AnnotatedString.dateFilter(): TransformedText {
    val trimmed = if (this.text.length >= 8) this.text.substring(0..7) else this.text
    var out = ""
    for (i in trimmed.indices) {
        out += trimmed[i]
        if (i % 2 == 1 && i < 4) out += "/"
    }

    val numberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 1) return offset
            if (offset <= 3) return offset +1
            if (offset <= 8) return offset +2
            return 10
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <=2) return offset
            if (offset <=5) return offset -1
            if (offset <=10) return offset -2
            return 8
        }
    }

    return TransformedText(AnnotatedString(out), numberOffsetTranslator)
}

fun AnnotatedString.timeFilter() : TransformedText {
    val trimmed = if (this.text.length > 5) this.text.substring(0..5) else this.text
    var out = ""
    for (i in trimmed.indices) {
        if (i == 2) {
            out += ":"
        }
        out += trimmed[i]
    }

    val numberOffsetMapping = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 2) return offset
            if (offset <= 3) return offset + 1
            return 5
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 2) return offset
            if (offset <= 4) return offset-1
            return 4
        }
    }

    return TransformedText(AnnotatedString(out), numberOffsetMapping)
}

