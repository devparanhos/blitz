package br.com.detran.blitz.presentation.components.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.detran.blitz.R
import br.com.detran.blitz.ui.theme.Primary

@Composable
fun FloatingBlitzButton(
    showMarker: Boolean,
    onClick: (Boolean) -> Unit
) {
    FloatingActionButton(
        modifier = Modifier.height(42.dp),
        backgroundColor = if (showMarker) Color.White else Primary,
        contentColor = if (showMarker) Color.Black else Color.White,
        onClick = {
            onClick(!showMarker)
        }
    ) {
        Row(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(26.dp),
                painter = painterResource(
                    id = if (showMarker) {
                        R.drawable.ic_close
                    } else {
                        R.drawable.ic_add
                    }
                ),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(
                    id = if (showMarker) {
                        R.string.button_label_cancel
                    } else {
                        R.string.button_label_add_blitz
                    }
                )
            )
        }
    }
}

@Composable
fun DefaultButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Primary
        ),
        onClick = {
            onClick()
        }
    ) {
        Text(
            text = text.uppercase(),
            color = Color.White
        )
    }
}