package com.example.smartfit.ui.theme

import androidx.compose.ui.graphics.Color
// ---- 主色：更亮一点的 Lime ----
// 我用的是 #B3FF00，比 #A5FF00 稍微亮一点，在白底上更清楚
val LimePrimary = Color(0xFFB3FF00)
val LimePrimarySoft = Color(0x33B3FF00) // 20% 透明，用在渐变/背景

// ---- Light Theme ----
val LightBackground   = Color(0xFFEFF2F5)      // 比纯白稍灰一点，衬托 lime
val LightSurface      = Color(0xFFFFFFFF)
val LightSurfaceGlass = Color(0xD9FFFFFF)      // 85% 白玻璃
val LightSurfaceStrong = Color(0xFFFFFFFF)     // 比较实心的卡片可用

val LightPrimary      = LimePrimary
val LightPrimarySoftAlias = LimePrimarySoft
val LightSecondary    = Color(0xFF22C55E)

val LightOnBackground = Color(0xFF020617)
val LightOnSurface    = Color(0xFF050816)
val LightOutline      = Color(0x22020617)      // 很淡的描边

// ---- Dark Theme ----
val DarkBackground   = Color(0xFF020617)       // 深海军蓝
val DarkSurface      = Color(0xFF020617)
val DarkSurfaceGlass = Color(0x6615253A)       // 深色玻璃
val DarkSurfaceStrong = Color(0xFF020617)

val DarkPrimary      = LimePrimary
val DarkPrimarySoftAlias = LimePrimarySoft
val DarkSecondary    = Color(0xFF34D399)

val DarkOnBackground = Color(0xFFE5E7EB)
val DarkOnSurface    = Color(0xFFF9FAFB)
val DarkOutline      = Color(0x33E5E7EB)

// 其他小点缀
val AccentRed    = Color(0xFFF97373)
val AccentYellow = Color(0xFFFACC15)


