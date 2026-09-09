package com.innosonian.arcresus.platform.ble

interface BlePort {
    fun startScanning(
        onDeviceFound: (name: String, address: String) -> Unit,
        onScanFinished: () -> Unit
    )
    fun stopScanning()
    fun connectDevice(
        address: String,
        onConnectionStateChange: (state: String) -> Unit
    )
    fun sendTrainingStartPacket()
    fun registerDepthCallback(callback: (Int) -> Unit)
}

expect fun provideBlePort(): BlePort