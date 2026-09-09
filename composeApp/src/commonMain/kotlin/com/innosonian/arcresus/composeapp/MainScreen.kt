package com.innosonian.arcresus.composeapp

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import arc_resus_skills.composeapp.generated.resources.Res
import arc_resus_skills.composeapp.generated.resources.arc_main_logo
import com.innosonian.arcresus.presentation.LoginColors
import org.jetbrains.compose.resources.painterResource
import rememberInterFontFamily

private const val DESIGN_WIDTH = 1366f
private const val DESIGN_HEIGHT = 1024f

@Composable
fun MainScreen(
    devices: List<DeviceUiModel>,
    isSearching: Boolean,
    onLogoutClick: () -> Unit,
    onContinueClick: (String) -> Unit
) {
    val primaryRed = Color(0xFFE53935)
    val backgroundGray = Color(0xFFF8F9FA)

    var showSidePanel by remember { mutableStateOf(false) }
    var selectedProgramTitle by remember { mutableStateOf("") }

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
                .statusBarsPadding()
                .background(backgroundGray)
        ) {
            val screenWidth = maxWidth
            val screenHeight = maxHeight

            val scaleX = screenWidth.value / DESIGN_WIDTH
            val scaleY = screenHeight.value / DESIGN_HEIGHT
            val scale = minOf(scaleX, scaleY)

            val responsiveDp: @Composable (Dp) -> Dp = { figmaDp -> (figmaDp.value * scale).dp }
            val responsiveSp: @Composable (Int) -> TextUnit = { figmaSp -> (figmaSp * scale).sp }

            val targetPanelWidth = if (showSidePanel) screenWidth / 2.2f else 0.dp

            val panelWidth by animateDpAsState(
                targetValue = targetPanelWidth,
                animationSpec = spring(dampingRatio = 0.8f, stiffness = 300f),
                label = "panelWidth"
            )

            val mainContentWidth = screenWidth - panelWidth

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = responsiveDp(40.dp),
                        end = responsiveDp(40.dp),
                        top = responsiveDp(30.dp),
                        bottom = responsiveDp(20.5.dp)
                    )
            ) {
                Surface(
                    color = backgroundGray,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(responsiveDp(30.dp)),
                            color = Color.White,
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = responsiveDp(20.dp), vertical = responsiveDp(0.dp)),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(responsiveDp(20.dp))
                            ) {
                                Image(
                                    painter = painterResource(Res.drawable.arc_main_logo),
                                    contentDescription = "Logo",
                                    modifier = Modifier
                                        .width(responsiveDp(186.dp))
                                        .height(responsiveDp(60.dp))
                                )

                                Text(
                                    text = "First Name La...",
                                    fontSize = responsiveSp(20),
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF222222)
                                )
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(responsiveDp(12.dp)),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(responsiveDp(20.dp)),
                                color = Color.White,
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = responsiveDp(14.dp), vertical = responsiveDp(14.dp)),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(responsiveDp(6.dp))
                                ) {
                                    Text(
                                        text = "Connectable Devices (${devices.size})",
                                        fontSize = responsiveSp(20),
                                        fontWeight = FontWeight.Bold,
                                        color = Color.DarkGray
                                    )
                                }
                            }

                            Surface(
                                shape = RoundedCornerShape(responsiveDp(20.dp)),
                                color = Color.White,
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = responsiveDp(14.dp), vertical = responsiveDp(14.dp)),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(responsiveDp(6.dp))
                                ) {
                                    Text(
                                        text = "Text Size",
                                        fontSize = responsiveSp(20),
                                        fontWeight = FontWeight.Bold,
                                        color = Color.DarkGray
                                    )
                                }
                            }

                            Button(
                                onClick = onLogoutClick,
                                shape = RoundedCornerShape(responsiveDp(20.dp)),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                contentPadding = PaddingValues(horizontal = responsiveDp(16.dp), vertical = responsiveDp(14.dp))
                            ) {
                                Text(
                                    text = "Logout",
                                    fontSize = responsiveSp(20),
                                    fontWeight = FontWeight.Bold,
                                    color = Color.DarkGray
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(responsiveDp(35.dp)))

                // 하단 영역
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Column(
                        modifier = Modifier
                            .width(mainContentWidth)
                            .fillMaxHeight()
                            .padding(end = if (showSidePanel) responsiveDp(20.dp) else 0.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(responsiveDp(20.dp))
                        ) {
                            val anySelected = selectedProgramTitle.isNotEmpty()

                            ProgramCard(
                                title = "BLS Adult",
                                date = "~ Feb 29. 2028",
                                statusText = "In Progress",
                                statusColor = primaryRed,
                                progressText = "4 of 7 steps",
                                progressFraction = 4f / 7f,
                                progressColor = primaryRed,
                                buttonText = "Continue",
                                anySelected = anySelected,
                                isSelected = selectedProgramTitle == "BLS Adult",
                                responsiveDp = responsiveDp,
                                responsiveSp = responsiveSp,
                                onClick = {
                                    selectedProgramTitle = "BLS Adult"
                                    showSidePanel = true
                                }
                            )

                            ProgramCard(
                                title = "BLS Infant",
                                date = "~ Feb 29. 2028",
                                statusText = "To-Do",
                                statusColor = primaryRed,
                                progressText = "0 of 4 steps",
                                progressFraction = 0f,
                                progressColor = primaryRed,
                                buttonText = "Start",
                                anySelected = anySelected,
                                isSelected = selectedProgramTitle == "BLS Infant",
                                responsiveDp = responsiveDp,
                                responsiveSp = responsiveSp,
                                onClick = {
                                    selectedProgramTitle = "BLS Infant"
                                    showSidePanel = true
                                }
                            )

                            ProgramCard(
                                title = "CPR+AED Adult",
                                date = "~ Feb 29. 2028",
                                statusText = "To-Do",
                                statusColor = primaryRed,
                                progressText = "0 of 3 steps",
                                progressFraction = 0f,
                                progressColor = primaryRed,
                                buttonText = "Start",
                                anySelected = anySelected,
                                isSelected = selectedProgramTitle == "CPR+AED Adult",
                                responsiveDp = responsiveDp,
                                responsiveSp = responsiveSp,
                                onClick = {
                                    selectedProgramTitle = "CPR+AED Adult"
                                    showSidePanel = true
                                }
                            )

                            ProgramCard(
                                title = "CPR+AED Infant",
                                date = "~ Feb 29. 2028",
                                statusText = "Completed",
                                statusColor = Color(0xFF0288D1),
                                progressText = "4 of 4 steps",
                                progressFraction = 1f,
                                progressColor = Color(0xFF0288D1),
                                buttonText = "Review",
                                isCompleted = true,
                                anySelected = anySelected,
                                isSelected = selectedProgramTitle == "CPR+AED Infant",
                                responsiveDp = responsiveDp,
                                responsiveSp = responsiveSp,
                                onClick = {
                                    selectedProgramTitle = "CPR+AED Infant"
                                    showSidePanel = true
                                }
                            )
                        }
                    }

                    // 우측 상세 패널 영역
                    Surface(
                        modifier = Modifier
                            .width(panelWidth)
                            .fillMaxHeight(),
                        color = Color.White,
                        shape = RoundedCornerShape(responsiveDp(24.dp))
                    ) {
                        val sideScrollState = rememberScrollState()

                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(sideScrollState)
                                    .padding(
                                        start = responsiveDp(30.dp),
                                        end = responsiveDp(30.dp),
                                        top = responsiveDp(40.dp),
                                        bottom = responsiveDp(40.dp)
                                    ),
                                verticalArrangement = Arrangement.spacedBy(responsiveDp(24.dp))
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = selectedProgramTitle.ifEmpty { "Detailed Information" },
                                        fontSize = responsiveSp(24),
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )

                                    IconButton(
                                        onClick = {
                                            showSidePanel = false
                                            selectedProgramTitle = ""
                                        }
                                    ) {
                                        Text(
                                            text = "✕",
                                            color = Color.Black,
                                            fontSize = responsiveSp(20),
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }

                                DetailedDrillCard(
                                    statusText = "Passed",
                                    drillTitle = "Chest Compression Drill",
                                    items = listOf(
                                        DrillItem("[Video] {file name}", "View", isActionable = true),
                                        DrillItem("[Practice] 60 Co...", "Again", isActionable = true)
                                    ),
                                    responsiveDp = responsiveDp,
                                    responsiveSp = responsiveSp
                                )

                                DetailedDrillCard(
                                    statusText = "Passed",
                                    drillTitle = "BVM Ventilation Drill",
                                    items = listOf(
                                        DrillItem("[Video] {file name}", "View", isActionable = true),
                                        DrillItem("[Image] {file name}", "View", isActionable = true),
                                        DrillItem("[PDF] {file name}", "View", isActionable = true),
                                        DrillItem("[Practice] 12 Ven...", "Again", isActionable = true)
                                    ),
                                    responsiveDp = responsiveDp,
                                    responsiveSp = responsiveSp
                                )

                                DetailedDrillCard(
                                    statusText = "Passed",
                                    drillTitle = "Two-Responder + AED Scenario",
                                    items = listOf(
                                        DrillItem("[Video] Scenario Overview", "View", isActionable = true),
                                        DrillItem("[PDF] Guidelines 2026", "View", isActionable = true),
                                        DrillItem("[Practice] Full Simulation", "Again", isActionable = true)
                                    ),
                                    responsiveDp = responsiveDp,
                                    responsiveSp = responsiveSp
                                )

                                DetailedDrillCard(
                                    statusText = "In Progress",
                                    drillTitle = "Advanced Airway Management",
                                    items = listOf(
                                        DrillItem("[Video] Intubation Guide", "View", isActionable = true),
                                        DrillItem("[Practice] Tube Insertion", "Start", isActionable = true)
                                    ),
                                    responsiveDp = responsiveDp,
                                    responsiveSp = responsiveSp
                                )
                            }

                            if (sideScrollState.value > 0) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(responsiveDp(40.dp))
                                        .align(Alignment.TopCenter)
                                        .background(
                                            brush = Brush.verticalGradient(
                                                colors = listOf(
                                                    Color(0xFFE53935).copy(alpha = 0.8f),
                                                    Color(0xFFE53935).copy(alpha = 0.0f)
                                                )
                                            )
                                        )
                                )
                            }

                            if (sideScrollState.value < sideScrollState.maxValue) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(responsiveDp(40.dp))
                                        .align(Alignment.BottomCenter)
                                        .background(
                                            brush = Brush.verticalGradient(
                                                colors = listOf(
                                                    Color(0xFFE53935).copy(alpha = 0.0f),
                                                    Color(0xFFE53935).copy(alpha = 0.8f)
                                                )
                                            )
                                        )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

data class DrillItem(
    val title: String,
    val actionText: String,
    val isActionable: Boolean
)

@Composable
fun DetailedDrillCard(
    statusText: String,
    drillTitle: String,
    items: List<DrillItem>,
    responsiveDp: @Composable (Dp) -> Dp,
    responsiveSp: @Composable (Int) -> TextUnit
) {
    Card(
        shape = RoundedCornerShape(responsiveDp(20.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(responsiveDp(24.dp)),
            verticalArrangement = Arrangement.spacedBy(responsiveDp(16.dp))
        ) {
            Surface(
                shape = RoundedCornerShape(responsiveDp(8.dp)),
                color = Color(0xFF00BCD4)
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = responsiveDp(10.dp))
                        .padding(
                            top = responsiveDp(5.dp),
                            bottom = responsiveDp(5.dp)
                        ),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = statusText,
                        color = Color.White,
                        fontSize = responsiveSp(14),
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(
                            lineHeight = responsiveSp(14),
                            lineHeightStyle = LineHeightStyle(
                                alignment = LineHeightStyle.Alignment.Center,
                                trim = LineHeightStyle.Trim.None
                            )
                        )
                    )
                }
            }

            Text(
                text = drillTitle,
                fontSize = responsiveSp(22),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(responsiveDp(12.dp))
            ) {
                items.forEach { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(responsiveDp(8.dp)),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = item.title,
                                fontSize = responsiveSp(16),
                                color = Color.DarkGray,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        OutlinedButton(
                            onClick = { },
                            shape = RoundedCornerShape(responsiveDp(10.dp)),
                            border = BorderStroke(1.dp, Color(0xFF00BCD4)),
                            contentPadding = PaddingValues(horizontal = responsiveDp(16.dp), vertical = responsiveDp(8.dp))
                        ) {
                            Text(
                                text = item.actionText,
                                color = Color(0xFF00BCD4),
                                fontSize = responsiveSp(16),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProgramCard(
    title: String,
    date: String,
    statusText: String,
    statusColor: Color,
    progressText: String,
    progressFraction: Float,
    progressColor: Color,
    buttonText: String,
    isCompleted: Boolean = false,
    anySelected: Boolean,
    isSelected: Boolean,
    responsiveDp: @Composable (Dp) -> Dp,
    responsiveSp: @Composable (Int) -> TextUnit,
    onClick: () -> Unit
) {
    val cardBackgroundColor = Color.White
    val cardAlpha = if (anySelected && !isSelected) 0.2f else 1.0f

    Card(
        onClick = onClick,
        shape = RoundedCornerShape(responsiveDp(30.dp)),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor),
        modifier = Modifier
            .fillMaxWidth()
            .alpha(cardAlpha)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(responsiveDp(35.dp))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.width(responsiveDp(10.dp)))
                    Text(
                        text = title,
                        fontSize = responsiveSp(24),
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.width(responsiveDp(20.dp)))
                    Text(
                        text = date,
                        fontSize = responsiveSp(18),
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(responsiveDp(6.dp))
                ) {
                    Text(
                        text = "View Details",
                        fontSize = responsiveSp(18),
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(responsiveDp(29.75.dp)))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(responsiveDp(30.dp)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(2f),
                    verticalArrangement = Arrangement.spacedBy(responsiveDp(12.dp))
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(responsiveDp(10.dp)),
                            border = BorderStroke(1.5.dp, statusColor),
                            color = Color.Transparent
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = responsiveDp(10.dp))
                                    .padding(
                                        top = responsiveDp(5.dp),
                                        bottom = responsiveDp(5.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = statusText,
                                    fontSize = responsiveSp(18),
                                    color = statusColor,
                                    fontWeight = FontWeight.W700,
                                    style = TextStyle(
                                        lineHeight = responsiveSp(18),
                                        lineHeightStyle = LineHeightStyle(
                                            alignment = LineHeightStyle.Alignment.Center,
                                            trim = LineHeightStyle.Trim.None
                                        )
                                    )
                                )
                            }
                        }

                        Text(
                            text = progressText,
                            fontSize = responsiveSp(20),
                            color = LoginColors.black1,
                            fontWeight = FontWeight.W700
                        )
                    }

                    LinearProgressIndicator(
                        progress = { progressFraction },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(responsiveDp(10.dp))
                            .clip(RoundedCornerShape(responsiveDp(10.dp))),
                        color = progressColor,
                        trackColor = Color.LightGray.copy(alpha = 0.3f),
                    )
                }

                Box(
                    modifier = Modifier
                        .height(responsiveDp(60.dp))
                        .weight(1f)
                        .clip(RoundedCornerShape(responsiveDp(15.dp)))
                        .background(if (!isCompleted) progressColor else Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    if (!isCompleted) {
                        Text(
                            text = buttonText,
                            color = Color.White,
                            fontSize = responsiveSp(20),
                            fontWeight = FontWeight.SemiBold
                        )
                    } else {
                        Surface(
                            shape = RoundedCornerShape(responsiveDp(15.dp)),
                            border = BorderStroke(1.dp, Color(0xFF0288D1)),
                            color = Color.Transparent,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = buttonText,
                                    color = Color(0xFF0288D1),
                                    fontSize = responsiveSp(20),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}