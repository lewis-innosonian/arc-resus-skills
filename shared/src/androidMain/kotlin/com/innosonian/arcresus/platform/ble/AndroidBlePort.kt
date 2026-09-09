package com.innosonian.arcresus.platform.ble

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.*
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import com.innosonian.arcresus.device.protocol.BlePacketParser
import java.util.UUID

object AndroidAppContext {
    var applicationContext: Context? = null
}

class AndroidBlePort : BlePort {
    private var currentConnectedGatt: BluetoothGatt? = null
    private val DESCRIPTOR_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")
    private var isScanning = false
    private val handler = Handler(Looper.getMainLooper())
    private var depthCallback: ((Int) -> Unit)? = null
    private var scannerCallback: ScanCallback? = null

    @SuppressLint("MissingPermission")
    override fun startScanning(
        onDeviceFound: (name: String, address: String) -> Unit,
        onScanFinished: () -> Unit
    ) {
        val context = AndroidAppContext.applicationContext ?: run {
            onScanFinished()
            return
        }

        val hasPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        }

        if (!hasPermission) {
            onScanFinished()
            return
        }

        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as? BluetoothManager
        val scanner = bluetoothManager?.adapter?.bluetoothLeScanner

        if (bluetoothManager?.adapter?.isEnabled != true || scanner == null) {
            onScanFinished()
            return
        }

        if (isScanning) return
        isScanning = true

        scannerCallback = object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult?) {
                super.onScanResult(callbackType, result)
                result?.device?.let { device ->
                    onDeviceFound(device.name ?: "Unknown", device.address)
                }
            }

            override fun onScanFailed(errorCode: Int) {
                super.onScanFailed(errorCode)
                isScanning = false
                onScanFinished()
            }
        }

        try {
            scanner.startScan(scannerCallback)
        } catch (e: SecurityException) {
            isScanning = false
            onScanFinished()
            return
        }


    }

    @SuppressLint("MissingPermission")
    override fun stopScanning() {
        if (!isScanning) return
        val context = AndroidAppContext.applicationContext ?: return
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as? BluetoothManager
        val scanner = bluetoothManager?.adapter?.bluetoothLeScanner
        scannerCallback?.let {
            try {
                scanner?.stopScan(it)
            } catch (_: Exception) {}
        }
        isScanning = false
        scannerCallback = null
    }

    @SuppressLint("MissingPermission")
    override fun connectDevice(
        address: String,
        onConnectionStateChange: (state: String) -> Unit
    ) {
        val context = AndroidAppContext.applicationContext ?: return
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as? BluetoothManager
        val device = bluetoothManager?.adapter?.getRemoteDevice(address) ?: return

        onConnectionStateChange("연결 시도 중...")

        device.connectGatt(context, false, object : BluetoothGattCallback() {
            override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
                when (newState) {
                    BluetoothProfile.STATE_CONNECTED -> {
                        onConnectionStateChange("연결됨 (서비스 탐색 중...)")
                        currentConnectedGatt = gatt
                        gatt.discoverServices()
                    }
                    BluetoothProfile.STATE_DISCONNECTED -> {
                        onConnectionStateChange("연결 끊김")
                        if (currentConnectedGatt == gatt) currentConnectedGatt = null
                        try { gatt.close() } catch (_: Exception) {}
                    }
                }
            }

            override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    onConnectionStateChange("서비스 탐색 완료")
                    for (service in gatt.services) {
                        for (characteristic in service.characteristics) {
                            if ((characteristic.properties and BluetoothGattCharacteristic.PROPERTY_NOTIFY) != 0) {
                                gatt.setCharacteristicNotification(characteristic, true)
                                characteristic.getDescriptor(DESCRIPTOR_UUID)?.let { descriptor ->
                                    descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                                    gatt.writeDescriptor(descriptor)
                                }
                            }
                        }
                    }
                }
            }

            @SuppressLint("MissingPermission")
            override fun onDescriptorWrite(gatt: BluetoothGatt?, descriptor: BluetoothGattDescriptor?, status: Int) {
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    onConnectionStateChange("연결 및 수신 준비 완료")
                    gatt?.services?.forEach { service ->
                        service.characteristics.forEach { characteristic ->
                            val props = characteristic.properties
                            if ((props and BluetoothGattCharacteristic.PROPERTY_WRITE) != 0 ||
                                (props and BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE) != 0) {
                                characteristic.value = BlePacketParser.initPacket
                                gatt.writeCharacteristic(characteristic)
                                return@forEach
                            }
                        }
                    }
                }
            }

            override fun onCharacteristicChanged(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic) {
                val parsedDepth = BlePacketParser.parseDepth(characteristic.value)
                if (parsedDepth != null) {
                    Handler(Looper.getMainLooper()).post {
                        depthCallback?.invoke(parsedDepth)
                    }
                }
            }
        }, BluetoothDevice.TRANSPORT_LE)
    }

    @SuppressLint("MissingPermission")
    override fun sendTrainingStartPacket() {
        val gatt = currentConnectedGatt ?: return
        for (service in gatt.services) {
            for (characteristic in service.characteristics) {
                val props = characteristic.properties
                if ((props and BluetoothGattCharacteristic.PROPERTY_WRITE) != 0 ||
                    (props and BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE) != 0) {
                    characteristic.value = BlePacketParser.startPacket
                    gatt.writeCharacteristic(characteristic)
                    return
                }
            }
        }
    }

    override fun registerDepthCallback(callback: (Int) -> Unit) {
        depthCallback = callback
    }
}