package com.innosonian.arcresus.composeapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.innosonian.arcresus.presentation.LoginScreen
import com.innosonian.arcresus.presentation.LoginViewModel
import com.innosonian.arcresus.di.NetworkModule
import com.innosonian.arcresus.bridge.DeviceSetupBridge
import com.innosonian.arcresus.bridge.DeviceUiData
import com.innosonian.arcresus.platform.ble.provideBlePort
import kotlinx.coroutines.coroutineScope

@Composable
fun App() {
    var isLoggedIn by remember { mutableStateOf(false) }
    var showDeviceSetup by remember { mutableStateOf(false) }

    val loginViewModel = remember { LoginViewModel(NetworkModule.authRepository) }

    val blePort = remember { provideBlePort() }
    val deviceSetupBridge = remember { DeviceSetupBridge(blePort) }

    val scannedDevices by deviceSetupBridge.scannedDevices.collectAsState()

    val deviceList = scannedDevices.map {
        DeviceUiModel(
            id = it.id,
            name = "${it.name}",
            firmware = it.firmware
        )
    }

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            deviceSetupBridge.startSearch()
        }
    }

    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars)
        ) {
            if (!isLoggedIn) {
                LoginScreen(
                    onSubmit = { email, password ->
                        coroutineScope {
                            loginViewModel.login(email, password)
                                .onSuccess { response ->
                                    isLoggedIn = true
                                }
                                .onFailure { error ->
                                    println("로그인 실패: ${error.message}")
                                }
                        }
                    }
                )
            } else {
                Box(modifier = Modifier.fillMaxSize()) {
                    MainScreen(
                        devices = deviceList,
                        isSearching = true,
                        onLogoutClick = {
                            deviceSetupBridge.stopSearch()
                            isLoggedIn = false
                        },
                        onContinueClick = { programTitle ->
                            showDeviceSetup = true
                        }
                    )

                    if (showDeviceSetup) {
                        DeviceSetupRoute(
                            deviceSetupBridge = deviceSetupBridge,
                            onClose = {
                                showDeviceSetup = false
                            },
                            onDeviceClick = { selectedDevice ->
                                showDeviceSetup = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DeviceSetupRoute(
    deviceSetupBridge: DeviceSetupBridge,
    onClose: () -> Unit,
    onDeviceClick: (DeviceUiModel) -> Unit
) {
    val scannedDevices by deviceSetupBridge.scannedDevices.collectAsState()
    val isSearching by remember { mutableStateOf(true) }

//    DeviceSetupScreen(
//        devices = scannedDevices.map {
//            DeviceUiModel(id = it.id, name = it.name, firmware = it.firmware)
//        },
//        isSearching = isSearching,
//        onClose = {
//            onClose()
//        },
//        onDeviceClick = { selectedUiModel ->
//            deviceSetupBridge.connect(selectedUiModel.id)
//            onDeviceClick(selectedUiModel)
//        }
//    )
}
