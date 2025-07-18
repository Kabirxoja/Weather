package uz.kabir.weather.presentation.screen.main

/* ────────── PM 2.5 (μg/m³) ────────── */
internal fun Double.pm25Level() = when {
    this < 10      -> PollutionLevel.GOOD
    this < 25      -> PollutionLevel.MODERATE
    this < 50      -> PollutionLevel.UNHEALTHY
    this < 75      -> PollutionLevel.VERY_UNHEALTHY
    else           -> PollutionLevel.HAZARDOUS
}

/* ────────── PM 10 (μg/m³) ────────── */
internal fun Double.pm10Level() = when {
    this < 20      -> PollutionLevel.GOOD
    this < 50      -> PollutionLevel.MODERATE
    this < 100     -> PollutionLevel.UNHEALTHY
    this < 200     -> PollutionLevel.VERY_UNHEALTHY
    else           -> PollutionLevel.HAZARDOUS
}

/* ────────── NO₂ (μg/m³) ────────── */
internal fun Double.no2Level() = when {
    this < 40      -> PollutionLevel.GOOD
    this < 70      -> PollutionLevel.MODERATE
    this < 150     -> PollutionLevel.UNHEALTHY
    this < 200     -> PollutionLevel.VERY_UNHEALTHY
    else           -> PollutionLevel.HAZARDOUS
}

/* ────────── SO₂ (μg/m³) ────────── */
internal fun Double.so2Level() = when {
    this < 20      -> PollutionLevel.GOOD
    this < 80      -> PollutionLevel.MODERATE
    this < 250     -> PollutionLevel.UNHEALTHY
    this < 350     -> PollutionLevel.VERY_UNHEALTHY
    else           -> PollutionLevel.HAZARDOUS
}

/* ────────── O₃ (μg/m³) ────────── */
internal fun Double.o3Level() = when {
    this < 60      -> PollutionLevel.GOOD
    this < 100     -> PollutionLevel.MODERATE
    this < 140     -> PollutionLevel.UNHEALTHY
    this < 180     -> PollutionLevel.VERY_UNHEALTHY
    else           -> PollutionLevel.HAZARDOUS
}

/* ────────── CO (μg/m³) ────────── */
internal fun Double.coLevel() = when {
    this < 4_400   -> PollutionLevel.GOOD
    this < 9_400   -> PollutionLevel.MODERATE
    this < 12_400  -> PollutionLevel.UNHEALTHY
    this < 15_400  -> PollutionLevel.VERY_UNHEALTHY
    else           -> PollutionLevel.HAZARDOUS
}
