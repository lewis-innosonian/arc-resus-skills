package com.innosonian.arcresus.bridge

import com.innosonian.arcresus.platform.ble.BlePort
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class DeviceUiData(
    val id: String,
    val name: String,
    val firmware: String
)

class DeviceSetupBridge(private val blePort: BlePort) {

    private val _scannedDevices = MutableStateFlow<List<DeviceUiData>>(emptyList())
    val scannedDevices: StateFlow<List<DeviceUiData>> = _scannedDevices.asStateFlow()

    fun startSearch() {
        _scannedDevices.value = emptyList()

        blePort.startScanning(
            onDeviceFound = { name, address ->
                val targetKeyword = "Brayden"

                if (name.contains(targetKeyword, ignoreCase = true)) {
                    val newDevice = DeviceUiData(id = address, name = name, firmware = "")

                    _scannedDevices.update { currentList ->
                        if (currentList.any { it.id == address }) {
                            currentList
                        } else {
                            currentList + newDevice
                        }
                    }
                }
            },
            onScanFinished = {
                // 스캔 완료
            }
        )
    }

    fun stopSearch() {
        blePort.stopScanning()
    }

    fun connect(address: String) {
        blePort.connectDevice(address) { state ->
            // 연결 상태 변경 콜백 처리
        }
    }
}