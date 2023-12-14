package com.morozov.workarea.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class Dimensions(
    // General
    val spacerXL: Dp = 16.dp,
    val spacerXXL: Dp = 24.dp,
    // Auth screen
    val bottomSheetHeightFraction: Float,
    val subscriptionPlanCardSpacer: Dp,
    val preActionBottomPadding: Dp,
    val authHorizontalPadding: Dp,
    val authBottomPadding: Dp,
    val authLegalTopBarTopPadding: Dp,
    // Home screen
    val homeCTAContentBottomPadding: Dp,
    val homeCTAContentStartPadding: Dp,

    )

val phoneDimensions = Dimensions(
    // Auth screen
    bottomSheetHeightFraction = 0.95f,
    subscriptionPlanCardSpacer = 12.dp,
    preActionBottomPadding = 16.dp,
    authHorizontalPadding = 20.dp,
    authBottomPadding = 24.dp,
    authLegalTopBarTopPadding = 16.dp,
    // Home screen
    homeCTAContentBottomPadding = 20.dp,
    homeCTAContentStartPadding = 20.dp

)

val tabletDimensions = Dimensions(
    // Auth screen
    bottomSheetHeightFraction = 0.80f,
    subscriptionPlanCardSpacer = 12.dp,
    preActionBottomPadding = 16.dp,
    authHorizontalPadding = 20.dp,
    authBottomPadding = 24.dp,
    authLegalTopBarTopPadding = 16.dp,
    // Home screen
    homeCTAContentBottomPadding = 22.dp,
    homeCTAContentStartPadding = 48.dp
)