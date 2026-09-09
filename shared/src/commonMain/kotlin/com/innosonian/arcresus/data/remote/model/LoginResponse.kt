package com.innosonian.arcresus.data.remote.model
import kotlinx.serialization.Serializable
@Serializable
data class LoginRequest(
    //vcc용 테스트
//    var email: String? = null,
//    var password: String? = null,
//    var organization : String? = null
//    arc용
    val login_id: String,
    val password: String
)


@Serializable
data class LoginResponse(
    val environment: String,
    val session_id: String,
    val session_token: String,
    val expires_in: Int,
    val expires_at: String,
    val mock_user: MockUser
)



@Serializable
data class MockUser(
    val id: String,
    val display_id: String
)
//
//
///* vcc용 response */
//@Serializable
//data class LoginResponse(
//    val data: LoginData,
//    val success: Boolean,
//    val message: String,
//    val timestamp: String
//)
//@Serializable
//class LoginData{
//    val token: String = ""
//    val first_name: String = ""
//    val email: String = ""
//    val organization_name : String =""
//    val last_name: String = ""
//    val session_id: String = ""
//    val name : String = ""
//    val role: String =""
//    val logoUrl : String? = null
//}

/* vcc용 response end*/

