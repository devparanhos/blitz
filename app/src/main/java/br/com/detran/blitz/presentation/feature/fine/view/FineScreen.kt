package br.com.detran.blitz.presentation.feature.fine.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Snackbar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import br.com.detran.blitz.core.constant.Package.PACKAGE_NAME
import br.com.detran.blitz.presentation.components.content.BlitzContent
import br.com.detran.blitz.presentation.components.content.BlitzNotFoundContent
import br.com.detran.blitz.presentation.components.content.FineHeaderContent
import br.com.detran.blitz.presentation.components.content.NearBlitzNotFoundContent
import br.com.detran.blitz.presentation.components.content.PermissionContent
import br.com.detran.blitz.presentation.feature.fine.action.FineAction
import br.com.detran.blitz.presentation.feature.fine.viewmodel.FineViewModel
import br.com.detran.blitz.presentation.model.blitz.Blitz
import br.com.detran.blitz.ui.theme.LightGray
import br.com.detran.blitz.ui.theme.Primary
import org.koin.androidx.compose.getViewModel

@Composable
fun FineScreen(
    viewModel: FineViewModel = getViewModel(),
    list: List<Blitz>,
    toMap: () -> Unit,
    showSnack: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    var timesRefused by remember { mutableIntStateOf(0) }
    var hasPermission by remember {
        mutableStateOf(
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val requestPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                hasPermission = true
            } else {
                timesRefused++
            }
        }
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            state.loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Primary
                    )
                }
            }

            hasPermission && state.myLocation == null -> {
                viewModel.triggerAction(
                    action = FineAction.GetLocation(blitz = list)
                )
            }

            !hasPermission -> {
                PermissionContent {
                    if (timesRefused <= 2) {
                        requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    } else {
                        val intent = Intent()
                        intent.action = android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        intent.data = Uri.fromParts("package", PACKAGE_NAME, null)
                        context.startActivity(intent)
                    }
                }
            }

            else -> {
                state.myLocation?.let { myLocation ->
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(LightGray)
                            .padding(16.dp)
                    ) {
                        item {
                            FineHeaderContent(address = myLocation.getAddressLine(0))
                        }

                        item {
                            when {
                                list.isEmpty() -> {
                                    BlitzNotFoundContent(
                                        onClick = {
                                            toMap()
                                        }
                                    )
                                }

                                state.nearBlitz == null -> {
                                    NearBlitzNotFoundContent(
                                        myLocation = myLocation,
                                        list = list
                                    )
                                }

                                else -> {
                                    state.nearBlitz?.let { blitz ->
                                        BlitzContent(
                                            blitz = blitz,
                                            onClick = {
                                                showSnack()
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}