import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import arc_resus_skills.composeapp.generated.resources.Res
import arc_resus_skills.composeapp.generated.resources.inter_black
import arc_resus_skills.composeapp.generated.resources.inter_bold
import arc_resus_skills.composeapp.generated.resources.inter_extrabold
import arc_resus_skills.composeapp.generated.resources.inter_extralight
import arc_resus_skills.composeapp.generated.resources.inter_light
import arc_resus_skills.composeapp.generated.resources.inter_medium
import arc_resus_skills.composeapp.generated.resources.inter_regular
import arc_resus_skills.composeapp.generated.resources.inter_semibold
import arc_resus_skills.composeapp.generated.resources.inter_thin

import org.jetbrains.compose.resources.Font

@Composable
fun rememberInterFontFamily(): FontFamily {
    return FontFamily(
        Font(Res.font.inter_thin, FontWeight.Thin, FontStyle.Normal),             // 100
        Font(Res.font.inter_extralight, FontWeight.ExtraLight, FontStyle.Normal), // 200
        Font(Res.font.inter_light, FontWeight.Light, FontStyle.Normal),           // 300
        Font(Res.font.inter_regular, FontWeight.Normal, FontStyle.Normal),        // 400
        Font(Res.font.inter_medium, FontWeight.Medium, FontStyle.Normal),         // 500
        Font(Res.font.inter_semibold, FontWeight.SemiBold, FontStyle.Normal),     // 600
        Font(Res.font.inter_bold, FontWeight.Bold, FontStyle.Normal),             // 700
        Font(Res.font.inter_extrabold, FontWeight.ExtraBold, FontStyle.Normal),   // 800
        Font(Res.font.inter_black, FontWeight.Black, FontStyle.Normal),           // 900
    )
}