package br.com.detran.blitz.presentation.components.content

import android.location.Address
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.detran.blitz.R
import br.com.detran.blitz.core.function.calculateDistanceBetweenPoints
import br.com.detran.blitz.presentation.components.button.DefaultButton
import br.com.detran.blitz.presentation.model.blitz.Blitz
import br.com.detran.blitz.ui.theme.Primary

@Composable
fun PermissionContent(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.title_enable_permission),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.description_enable_permission),
            fontSize = 14.sp,
            fontWeight = FontWeight.Light,
            color = Color.LightGray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        DefaultButton(
            text = stringResource(R.string.button_label_enable).uppercase(),
            onClick = {
                onClick()
            }
        )
    }
}

@Composable
fun FineHeaderContent(
    address: String
) {
    Text(
        text = stringResource(id = R.string.description_near_blitz),
        color = Color.LightGray,
        fontSize = 12.sp
    )
    Spacer(modifier = Modifier.height(12.dp))
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.label_my_location)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(id = R.drawable.ic_location),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = address,
                fontSize = 12.sp,
                color = Color.LightGray
            )
        }
    }
}

@Composable
fun BlitzNotFoundContent(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.title_blitz_not_found),
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(id = R.string.description_blitz_not_found),
            fontWeight = FontWeight.Light,
            fontSize = 14.sp,
            color = Color.LightGray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        DefaultButton(
            text = stringResource(R.string.button_label_add_blitz),
            onClick = {
                onClick()
            }
        )
    }
}

@Composable
fun NearBlitzNotFoundContent(
    myLocation: Address,
    list: List<Blitz>
) {
    Column(
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.title_blitz_not_found_location),
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(id = R.string.title_registrated_blitz),
            fontSize = 12.sp,
            color = Color.LightGray
        )
        Spacer(modifier = Modifier.height(12.dp))
        Column {
            list.forEach { blitz ->
                val distance = calculateDistanceBetweenPoints(
                    latBlitz = blitz.latitude,
                    longBlitz = blitz.longitude,
                    latLocation = myLocation.latitude,
                    longLocation = myLocation.longitude
                )
                if (distance <= 20.0) {
                    Card {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Column {
                                Text(
                                    modifier = Modifier,
                                    text = blitz.address,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row {
                                        Row(
                                            horizontalArrangement = Arrangement.Start,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                modifier = Modifier.size(18.dp),
                                                painter = painterResource(id = R.drawable.ic_calendar),
                                                contentDescription = null,
                                                tint = Color.LightGray
                                            )
                                            Text(
                                                text = blitz.date,
                                                color = Color.LightGray,
                                                fontSize = 14.sp
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Row(
                                            horizontalArrangement = Arrangement.Start,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                modifier = Modifier.size(18.dp),
                                                painter = painterResource(id = R.drawable.ic_time),
                                                contentDescription = null,
                                                tint = Color.LightGray
                                            )
                                            Text(
                                                text = blitz.time,
                                                color = Color.LightGray,
                                                fontSize = 14.sp
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Row(
                                            horizontalArrangement = Arrangement.Start,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                modifier = Modifier.size(18.dp),
                                                painter = painterResource(id = R.drawable.ic_status),
                                                contentDescription = null,
                                                tint = Color.LightGray
                                            )
                                            Text(
                                                text = blitz.status.type,
                                                color = Color.LightGray,
                                                fontSize = 14.sp
                                            )
                                        }
                                    }

                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        text = "${distance.toInt()} Km",
                                        maxLines = 1,
                                        textAlign = TextAlign.End,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun BlitzContent(
    blitz: Blitz,
    onClick: () -> Unit
) {
    Card (
        modifier = Modifier.padding(top = 12.dp)
    ){
        Column(
            modifier = Modifier.padding(12.dp)
        ){
            Text(
                text = stringResource(id = R.string.title_blitz_location),
                fontSize = 14.sp,
                color = Color.LightGray
            )
            Text(
                text = blitz.address,
                fontSize = 16.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(id = R.string.title_blitz_execution),
                fontSize = 14.sp,
                color = Color.LightGray
            )
            Text(
                text = blitz.date,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(id = R.string.placeholder_blitz_hour),
                fontSize = 14.sp,
                color = Color.LightGray
            )
            Text(
                text = blitz.time,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    modifier = Modifier,
                    text = stringResource(id = R.string.title_offender),
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
                Text(
                    modifier = Modifier.clickable {
                        onClick()
                    },
                    text = stringResource(id = R.string.button_label_add),
                    color = Primary,
                    textAlign = TextAlign.End
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(id = R.string.title_offender_not_found),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(24.dp))
            DefaultButton(
                text = stringResource(id = R.string.button_label_end_blitz),
                onClick = {
                    onClick()
                }
            )
        }
    }
}