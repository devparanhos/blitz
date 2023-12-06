package br.com.detran.blitz.presentation.components.bottomsheet

import android.location.Address
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.detran.blitz.R
import br.com.detran.blitz.core.date.DateTransformation
import br.com.detran.blitz.core.extension.formatDate
import br.com.detran.blitz.core.extension.formatTime
import br.com.detran.blitz.core.extension.isDateValid
import br.com.detran.blitz.core.extension.isTimeValid
import br.com.detran.blitz.core.time.TimeTransformation
import br.com.detran.blitz.presentation.components.button.DefaultButton
import br.com.detran.blitz.ui.theme.Primary

@Composable
fun PositionBlitzBottomSheet(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .width(100.dp)
                .height(2.dp)
                .background(Color.Black)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.title_blitz_location),
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(id = R.string.description_blitz_location),
            fontWeight = FontWeight.Light,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        DefaultButton(
            text = stringResource(id = R.string.button_label_marker_position),
            onClick = {
                onClick()
            }
        )
    }
}

@Composable
fun AddBlitzBottomSheet(
    address: Address?,
    onClick: (date: String, time: String, address: String) -> Unit
) {
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var hasError by remember { mutableStateOf(false) }
    var errorMessage: Int? by remember { mutableStateOf(null) }
    val keyboard = LocalFocusManager.current

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(
                modifier = Modifier
                    .width(100.dp)
                    .height(2.dp)
                    .background(Color.Black)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.title_create_blitz),
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.description_create_blitz),
            fontSize = 14.sp,
            color = Color.LightGray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            address?.let {
                OutlinedTextField(
                    modifier = Modifier
                        .height(50.dp)
                        .weight(1f),
                    value = it.getAddressLine(0) ?: stringResource(id = R.string.placeholder_adress_not_defined),
                    enabled = false,
                    maxLines = 1,
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.placeholder_blitz_address),
                            fontSize = 14.sp
                        )
                    },
                    onValueChange = {}
                )
            }

        }
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            OutlinedTextField(
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f),
                value = date,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.placeholder_blitz_date),
                        fontSize = 14.sp
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Primary,
                    backgroundColor = Color.White,
                    cursorColor = Primary
                ),
                onValueChange = {
                    if (it.length < 10) {
                        date = it
                    }
                },
                visualTransformation = DateTransformation()
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f),
                value = time,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.placeholder_blitz_hour),
                        fontSize = 14.sp
                    )
                },
                onValueChange = {
                    if (it.length < 5) {
                        time = it
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Primary,
                    backgroundColor = Color.White,
                    cursorColor = Primary
                ),
                visualTransformation = TimeTransformation(),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboard.clearFocus()
                    }
                ),
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (hasError) {
            errorMessage?.let { message ->
                Text(
                    text = stringResource(id = message),
                    textAlign = TextAlign.Start,
                    color = Color.Red
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        DefaultButton(
            text = stringResource(id = R.string.button_label_create),
            onClick = {
                if (date.isEmpty() || time.isEmpty()) {
                    hasError = true
                    errorMessage = R.string.error_message_all_required
                } else if (!date.formatDate().isDateValid()){
                    hasError = true
                    errorMessage = R.string.error_message_date_invalid
                } else if (!time.formatTime().isTimeValid()) {
                    hasError = true
                    errorMessage = R.string.error_message_time_invalid
                } else {
                    hasError = false
                    keyboard.clearFocus()
                    address?.let {
                        onClick(date.formatDate(), time.formatTime(), it.getAddressLine(0))
                    }
                }
            }
        )
    }
}