package com.innosonian.arcresus.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import arc_resus_skills.composeapp.generated.resources.Res
import arc_resus_skills.composeapp.generated.resources.arc_logo
import arc_resus_skills.composeapp.generated.resources.login_logo
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import rememberInterFontFamily

private const val DESIGN_WIDTH = 1366f
private const val DESIGN_HEIGHT = 1024f

// 이동
object LoginColors {
    val ARC_Red = Color(0xFFF60006)
    val black3 = Color(0xFF999999)
    val black4 = Color(0xFFD9D9D9)
    val black1 = Color(0xFF333333)
    val white = Color(0xFFFFFFFF)
    val black6 = Color(0xFFF5F5F5)
}

@Composable
fun LoginScreen(
    onSubmit: suspend (String, String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val interFontFamily = rememberInterFontFamily()

    MaterialTheme(
        typography = Typography(
            bodyLarge = TextStyle(fontFamily = interFontFamily),
            bodyMedium = TextStyle(fontFamily = interFontFamily),
            bodySmall = TextStyle(fontFamily = interFontFamily),
            titleLarge = TextStyle(fontFamily = interFontFamily),
            titleMedium = TextStyle(fontFamily = interFontFamily),
            titleSmall = TextStyle(fontFamily = interFontFamily),
            labelLarge = TextStyle(fontFamily = interFontFamily),
            labelMedium = TextStyle(fontFamily = interFontFamily),
            labelSmall = TextStyle(fontFamily = interFontFamily),
        )
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(LoginColors.black6)
                .statusBarsPadding()
                .imePadding()
        ) {
            val fixedWidth = remember { maxWidth }
            val fixedHeight = remember { maxHeight }

            val scaleX = fixedWidth.value / DESIGN_WIDTH
            val scaleY = fixedHeight.value / DESIGN_HEIGHT
            val scale = minOf(scaleX, scaleY)

            val responsiveDp: @Composable (Dp) -> Dp = { figmaDp -> (figmaDp.value * scale).dp }
            val responsiveSp: @Composable (Int) -> TextUnit = { figmaSp -> (figmaSp * scale).sp }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.padding(vertical = responsiveDp(0.dp)),
                    horizontalArrangement = Arrangement.spacedBy(responsiveDp(56.dp)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.wrapContentHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.arc_logo),
                            contentDescription = "Red Cross Logo",
                            modifier = Modifier.size(responsiveDp(400.dp))
                        )
                    }

                    Card(
                        modifier = Modifier
                            .width(responsiveDp(550.dp))
                            .height(responsiveDp(450.dp)),
                        shape = RoundedCornerShape(responsiveDp(30.dp)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                        colors = CardDefaults.cardColors(containerColor = LoginColors.white)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(responsiveDp(50.dp)),
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                text = "Please enter your ID to login",
                                fontSize = responsiveSp(20),
                                fontWeight = FontWeight.W600,
                                color = LoginColors.black1,
                            )

                            Spacer(modifier = Modifier.height(responsiveDp(15.dp)))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(responsiveDp(54.dp))
                                    .clip(RoundedCornerShape(responsiveDp(15.dp)))
                                    .border(1.dp, LoginColors.black4, RoundedCornerShape(responsiveDp(15.dp)))
                                    .background(LoginColors.white)
                                    .padding(horizontal = responsiveDp(19.dp)),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                BasicTextField(
                                    value = email,
                                    onValueChange = { email = it },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                    enabled = !isLoading,
                                    textStyle = TextStyle(
                                        fontSize = responsiveSp(16),
                                        color = LoginColors.black1,
                                        fontFamily = interFontFamily
                                    ),
                                    decorationBox = { innerTextField ->
                                        if (email.isEmpty()) {
                                            Text(
                                                text = "Email",
                                                style = TextStyle(
                                                    fontSize = responsiveSp(20),
                                                    color = LoginColors.black3,
                                                    fontWeight = FontWeight.W400,
                                                    fontFamily = interFontFamily
                                                )
                                            )
                                        }
                                        innerTextField()
                                    }
                                )
                            }

                            Spacer(modifier = Modifier.height(responsiveDp(12.dp)))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(responsiveDp(54.dp))
                                    .clip(RoundedCornerShape(responsiveDp(15.dp)))
                                    .border(1.dp, LoginColors.black4, RoundedCornerShape(responsiveDp(15.dp)))
                                    .background(LoginColors.white)
                                    .padding(horizontal = responsiveDp(19.dp)),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                BasicTextField(
                                    value = password,
                                    onValueChange = { password = it },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                    enabled = !isLoading,
                                    visualTransformation = PasswordVisualTransformation(),
                                    textStyle = TextStyle(
                                        fontSize = responsiveSp(20),
                                        color = LoginColors.black1,
                                        fontWeight = FontWeight.W400,
                                        fontFamily = interFontFamily
                                    ),
                                    decorationBox = { innerTextField ->
                                        if (password.isEmpty()) {
                                            Text(
                                                text = "Password",
                                                style = TextStyle(
                                                    fontSize = responsiveSp(16),
                                                    color = LoginColors.black3,
                                                    fontFamily = interFontFamily
                                                )
                                            )
                                        }
                                        innerTextField()
                                    }
                                )
                            }

                            Spacer(modifier = Modifier.height(responsiveDp(30.dp)))

                            Button(
                                onClick = {
                                    coroutineScope.launch {
                                        isLoading = true
                                        onSubmit(email, password)
                                        isLoading = false
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(responsiveDp(60.dp)),
                                shape = RoundedCornerShape(responsiveDp(15.dp)),
                                colors = ButtonDefaults.buttonColors(containerColor = LoginColors.ARC_Red),
                                enabled = !isLoading && email.isNotBlank() && password.isNotBlank()
                            ) {
                                if (isLoading) {
                                    CircularProgressIndicator(
                                        color = LoginColors.white,
                                        modifier = Modifier.size(responsiveDp(24.dp)),
                                        strokeWidth = 2.dp
                                    )
                                } else {
                                    Text(
                                        text = "Login",
                                        color = LoginColors.white,
                                        fontSize = responsiveSp(20),
                                        fontWeight = FontWeight.SemiBold,
                                        fontFamily = interFontFamily
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 하단 버전 정보
            Text(
                text = "1.0.2(35)",
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = responsiveDp(24.dp), bottom = responsiveDp(8.dp)),
                color = LoginColors.black1,
                fontSize = responsiveSp(16)
            )

            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = responsiveDp(24.dp), bottom = responsiveDp(8.dp)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Certification Provided By",
                    color = LoginColors.black1,
                    fontSize = responsiveSp(16)
                )
                Spacer(modifier = Modifier.width(responsiveDp(6.dp)))
                Image(
                    painter = painterResource(Res.drawable.login_logo),
                    contentDescription = "Small Logo",
                    modifier = Modifier.size(responsiveDp(16.dp))
                )
                Spacer(modifier = Modifier.width(responsiveDp(4.dp)))
                Text(
                    text = "American Red Cross",
                    color = LoginColors.black1,
                    fontSize = responsiveSp(16),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
