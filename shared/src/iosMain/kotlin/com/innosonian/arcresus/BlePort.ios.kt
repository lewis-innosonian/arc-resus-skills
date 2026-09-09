package com.innosonian.arcresus.platform.ble

class IosBlePort : BlePort {
    override fun startScanning(onDeviceFound: (name: String, address: String) -> Unit, onScanFinished: () -> Unit) {
    }
    override fun stopScanning() {}
    override fun connectDevice(address: String, onConnectionStateChange: (state: String) -> Unit) {}
    override fun sendTrainingStartPacket() {}
    override fun registerDepthCallback(callback: (Int) -> Unit) {}
}

actual fun provideBlePort(): BlePort {
    return IosBlePort()
}