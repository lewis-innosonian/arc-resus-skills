package com.innosonian.arcresus.platform.ble

actual fun provideBlePort(): BlePort = AndroidBlePort()