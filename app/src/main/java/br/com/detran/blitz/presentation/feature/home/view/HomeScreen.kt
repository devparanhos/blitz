
package br.com.detran.blitz.presentation.feature.home.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.detran.blitz.R
import br.com.detran.blitz.core.bottomsheet.BottomSheetLayout
import br.com.detran.blitz.presentation.feature.home.action.HomeAction
import br.com.detran.blitz.presentation.feature.home.viewmodel.HomeViewModel
import br.com.detran.blitz.ui.theme.LightGray
import br.com.detran.blitz.ui.theme.Primary
import org.koin.androidx.compose.getViewModel
import br.com.detran.blitz.core.enums.tab.Tab.*
import br.com.detran.blitz.presentation.components.bottomsheet.AddBlitzBottomSheet
import br.com.detran.blitz.presentation.components.bottomsheet.PositionBlitzBottomSheet
import br.com.detran.blitz.presentation.components.button.FloatingBlitzButton
import br.com.detran.blitz.presentation.feature.fine.view.FineScreen
import br.com.detran.blitz.presentation.feature.map.view.MapScreen
import br.com.detran.blitz.presentation.model.blitz.Blitz
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = getViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { false }
    )

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = {
            when(state.currentBottomSheet) {
                is BottomSheetLayout.PositionBlitzBottomSheet -> {
                    PositionBlitzBottomSheet(
                        onClick = {
                            coroutineScope.launch {
                                modalSheetState.hide()
                            }
                        }
                    )
                }

                is BottomSheetLayout.AddBlitzBottomSheet -> {
                    AddBlitzBottomSheet(
                        address = (state.currentBottomSheet as BottomSheetLayout.AddBlitzBottomSheet).address,
                        onClick = { date, time, address ->
                            coroutineScope.launch {
                                viewModel.triggerAction(
                                    action = HomeAction.CreateBlitz(
                                        Blitz(
                                            latitude = (state.currentBottomSheet as BottomSheetLayout.AddBlitzBottomSheet).latitude,
                                            longitude = (state.currentBottomSheet as BottomSheetLayout.AddBlitzBottomSheet).longitude,
                                            address = address,
                                            date = date,
                                            time = time
                                        )
                                    )
                                )
                                modalSheetState.hide()
                            }
                        }
                    )
                }
            }
        }
    ) {
        Scaffold(
            backgroundColor = LightGray,
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    backgroundColor = Primary,
                    elevation = 0.dp,
                    title = {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Card(
                                shape = RoundedCornerShape(5.dp)
                            ) {
                                Image(
                                    modifier = Modifier.size(34.dp),
                                    painter = painterResource(id = R.drawable.logo),
                                    contentDescription = null
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = stringResource(id = R.string.app_name),
                                color = Color.White
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                if (!state.loading && state.tabSelected == MAP) {
                    FloatingBlitzButton(
                        showMarker = state.showMarker,
                        onClick = {
                            coroutineScope.launch {
                                viewModel.triggerAction(
                                    action = HomeAction.UpdateShowMarker(
                                        show = it
                                    )
                                )

                                if (it) modalSheetState.show()
                            }
                        }
                    )
                }
            }
        ) {
            Column {
                TabRow(
                    selectedTabIndex = state.tabSelected.index,
                    backgroundColor = Primary,
                    contentColor = Color.White
                ) {
                    state.tabs.forEachIndexed { index, tab ->
                        Tab(
                            text = {
                                Text(text = tab.type.uppercase())
                            },
                            selected = index == state.tabSelected.index,
                            unselectedContentColor = Color.Gray.copy(alpha = 0.3f),
                            onClick = {
                                viewModel.triggerAction(
                                    action = HomeAction.UpdateTabSelected(
                                        tab = tab
                                    )
                                )
                            }
                        )
                    }
                }

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

                    state.tabSelected == MAP -> {
                        MapScreen(
                            blitz = state.blitz,
                            showMarker = state.showMarker,
                            onSetBlitz = { latitude, longitude ->
                                coroutineScope.launch {
                                    viewModel.triggerAction(
                                        action = HomeAction.OpenCreateBlitzForm(
                                            latitude = latitude,
                                            longitude = longitude,
                                            context = context
                                        )
                                    )
                                    modalSheetState.show()
                                }
                            }
                        )
                    }

                    state.tabSelected == FINE -> {
                        FineScreen(
                            list = state.blitz,
                            toMap = {
                                viewModel.triggerAction(
                                    action = HomeAction.UpdateTabSelected(
                                        tab = MAP,
                                        showMarker = true
                                    )
                                )
                            },
                            showSnack = {
                                coroutineScope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        "Feature não implementada"
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

