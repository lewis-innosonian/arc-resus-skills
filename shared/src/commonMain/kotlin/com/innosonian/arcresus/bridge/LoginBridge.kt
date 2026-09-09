package com.innosonian.arcresus.bridge

public class LoginBridge(
    // TO BE CONFIRMED
    private val authProvider: suspend (String) -> Result<Unit>
) {
    public suspend fun submitIdentifier(identifier: String): Result<Unit> {
        return authProvider(identifier)
    }
}