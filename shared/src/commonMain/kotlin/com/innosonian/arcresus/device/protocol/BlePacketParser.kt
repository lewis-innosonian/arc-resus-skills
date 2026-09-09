package com.innosonian.arcresus.device.protocol

object BlePacketParser {
    val initPacket: ByteArray = byteArrayOf(0x51, 0x00, 0x00)
    val startPacket: ByteArray = byteArrayOf(0x54, 0x03)

    fun parseDepth(receivedData: ByteArray): Int? {
        if (receivedData.size >= 11 && (receivedData[0].toInt() and 0xFF) == 0xA8) {
            val maxDepth = (1..10).maxOf { receivedData[it].toInt() and 0xFF }
            return maxDepth / 2
        }
        return null
    }
}