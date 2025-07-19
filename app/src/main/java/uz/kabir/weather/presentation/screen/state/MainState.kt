package uz.kabir.weather.presentation.screen.state

data class MainState(
    val isLoading: Boolean = true,
    val noInternet: Boolean = false,
    val noCitySelected: Boolean = false,
    val error: String? = null
)