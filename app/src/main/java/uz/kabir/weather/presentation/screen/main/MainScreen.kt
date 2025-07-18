package uz.kabir.weather.presentation.screen.main


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import uz.kabir.weather.R
import uz.kabir.weather.presentation.navigation.AppDestination
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import uz.kabir.weather.domain.model.GeoInfoDomain
import uz.kabir.weather.presentation.state.AirCurrentResult
import uz.kabir.weather.presentation.state.WeatherCurrentResult

@Composable
fun MainScreen(
    modifier: Modifier = Modifier, navController: NavController
) {

    val mainViewModel: MainViewModel = koinViewModel()

    LaunchedEffect(Unit) {
        mainViewModel.onIntent(MainIntent.LoadSelectedCity)
    }


    val cityState by mainViewModel.cityState.collectAsState()
    val weatherState by mainViewModel.weatherState.collectAsState()
    val airPollutionState by mainViewModel.airPollutionState.collectAsState()

    val pollutionLevels by mainViewModel.pollutionLevels.collectAsState()
    Log.d("ERTY", "pollutionLevels: $pollutionLevels")

    // Internet mavjud emasligini aniqlash
    val noInternet = weatherState is WeatherCurrentResult.Error && (weatherState as WeatherCurrentResult.Error).message == "No internet connection"
                || airPollutionState is AirCurrentResult.Error && (airPollutionState as AirCurrentResult.Error).message == "No internet connection"

    val noCitySelected = cityState?.city.isNullOrBlank()
    Log.d("FFFR", "No city selected: $noCitySelected")
    Log.d("FFFR", "cityState: $cityState")


    if (noInternet) {
        ShowMessage(R.drawable.ic_wifi, "No internet connection", onClick = {})
    } else if (noCitySelected) {
        ShowMessage(R.drawable.ic_empty, "No city selected", onClick = { navController.navigate(AppDestination.Add.route) })
    } else {


        var city = cityState?.city ?: ""
        var timestamp = 0L
        var temp = ""
        var feelsLike = ""
        var wind = ""
        var humidity = ""
        var visibility = ""
        var pressure = ""
        var cloud = ""
        var sunrise = 0L
        var sunset = 0L
        var weatherDesc = ""
        var temperature = ""

        var aqi = ""
        var co = ""
        var no = ""
        var no2 = ""
        var o3 = ""
        var so2 = ""
        var pm2_5 = ""
        var pm10 = ""
        var nh3 = ""

        var date by remember { mutableStateOf("") }
        var sunriseFormatted by  remember { mutableStateOf("") }
        var sunsetFormatted by remember { mutableStateOf("") }


        when (val state = weatherState) {
            is WeatherCurrentResult.Loading -> {
                // Show loading state
                Log.d("MainScreen", "Loading...")
            }

            is WeatherCurrentResult.Success -> {
                // Update UI with weather data
                Log.d("MainScreen", "WEATHER ${state.data}")
                temp = state.data.temperature.toString()
                feelsLike = state.data.feelsLike.toString()
                wind = state.data.windSpeed.toString()
                humidity = state.data.humidity.toString()
                visibility = state.data.cloudiness.toString()
                pressure = state.data.pressure.toString()
                cloud = state.data.cloudiness.toString()
                sunrise = state.data.sunrise
                sunriseFormatted = java.text.SimpleDateFormat("HH:mm").format(java.util.Date(sunrise * 1000))
                sunset = state.data.sunset
                sunsetFormatted = java.text.SimpleDateFormat("HH:mm").format(java.util.Date(sunset * 1000))
                weatherDesc = state.data.weatherDescription
                temperature = state.data.temperature.toString()
                city = state.data.cityName
            }

            is WeatherCurrentResult.Error -> {
                // Show error state
                Log.d("MainScreen", "ERROR W ${state.message}")

            }
        }

        when (val state = airPollutionState) {
            is AirCurrentResult.Loading -> {
                Log.d("MainScreen", "Loading...")
            }

            is AirCurrentResult.Success -> {
                Log.d("MainScreen", "AIR ${state.data}")
                timestamp = state.data.timestamp
                date = java.text.SimpleDateFormat("HH:mm").format(java.util.Date(timestamp * 1000))
                aqi = state.data.aqi.toString()
                co = state.data.co.toString()
                no = state.data.no.toString()
                no2 = state.data.no2.toString()
                o3 = state.data.o3.toString()
                so2 = state.data.so2.toString()
                pm2_5 = state.data.pm2_5.toString()
                pm10 = state.data.pm10.toString()
                nh3 = state.data.nh3.toString()
            }

            is AirCurrentResult.Error -> {
                Log.d("MainScreen", "ERROR A ${state.message}")
            }
        }

        val geoInfoDomain: GeoInfoDomain? = cityState


        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MainTopBar(
                onAddCityClick = { navController.navigate(AppDestination.Add.route) },
                cityName = city,
                countryName = geoInfoDomain?.country ?: "",
                currentTime = date
            )



            Spacer(modifier = Modifier.height(16.dp))

            // Weather icon and temperature
            Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.Center) {

                Text(
                    text = temperature,
                    fontSize = 72.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary

                )
                Text(
                    text = "°C",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Image(painter = painterResource(id = R.drawable.icon_sunny), contentDescription = null)

            Spacer(modifier = Modifier.height(16.dp))

            // Weather Details Card
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(color = MaterialTheme.colorScheme.onSecondary)
            ) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    WeatherDetail(R.drawable.ic_temperature, "Feels Like", feelsLike)
                    WeatherDetail(R.drawable.ic_wind, "Wind", wind)
                    WeatherDetail(R.drawable.ic_humidity, "Humidity", humidity)
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    WeatherDetail(R.drawable.ic_visibility, "Visibility", visibility)
                    WeatherDetail(R.drawable.ic_pressure, "Pressure", pressure)
                    WeatherDetail(R.drawable.ic_clouds, "Cloud", cloud)
                }

                Text(
                    text = "more",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .align(
                            Alignment.End
                        )
                        .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                        .clickable {
                            TODO()
                        }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            //Air Quality
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(color = MaterialTheme.colorScheme.onSecondary)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 0.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        AirQualityItem("O₃", o3, pollutionLevels["O₃"] ?: PollutionLevel.GOOD)
                        AirQualityItem("NO₂", no2, pollutionLevels["NO₂"] ?: PollutionLevel.GOOD)
                        AirQualityItem("CO", co, pollutionLevels["CO"] ?: PollutionLevel.GOOD)
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        AirQualityItem("PM2.5", pm2_5, pollutionLevels["PM2.5"] ?: PollutionLevel.GOOD)
                        AirQualityItem("PM10", pm10, pollutionLevels["PM10"] ?: PollutionLevel.GOOD)
                        AirQualityItem("SO₂", so2, pollutionLevels["SO₂"] ?: PollutionLevel.GOOD)
                    }
                }


                Text(
                    text = "more",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .align(
                            Alignment.End
                        )
                        .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                        .clickable {
                            TODO()
                        }
                )
            }


            // Sunrise & Sunset
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SunInfoItem(R.drawable.ic_sunrise, "Sunrise", sunriseFormatted)
                SunInfoItem(R.drawable.ic_sunset, "Sunset", sunsetFormatted)
            }
        }
    }
}

@Composable
fun AirQualityItem(
    textLeft: String,
    textRight: String,
    level: PollutionLevel,
    modifier: Modifier = Modifier
) {
    val barColor = when(level){
        PollutionLevel.GOOD -> Color(0xFF4CAF50)
        PollutionLevel.MODERATE -> Color(0xFFFFC107)
        PollutionLevel.UNHEALTHY_SENSITIVE -> Color(0xFFFF9800)
        PollutionLevel.UNHEALTHY -> Color(0xFFF44336)
        PollutionLevel.VERY_UNHEALTHY -> Color(0xFF9C27B0)
        PollutionLevel.HAZARDOUS -> Color(0xFFB71C1C)
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left text
        Text(
            text = textLeft,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f)
        )

        // Center rectangle
        Box(
            modifier = Modifier
                .size(width = 32.dp, height = 8.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(barColor) // i want to change collor her
                .align(Alignment.CenterVertically)
        )

        // Right text
        Text(
            text = textRight,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.End)
        )
    }
}


@Composable
fun WeatherDetail(iconRes: Int, label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(48.dp)
        )
        Text(
            text = label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = value, fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun SunInfoItem(iconRes: Int, label: String, time: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(48.dp)
        )
        Text(text = label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onBackground)
        Text(text = time, fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground)

    }
}


@Composable
fun MainTopBar(
    onAddCityClick: () -> Unit,
    cityName: String,
    countryName: String,
    currentTime: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Left-aligned city and country
        Column(
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Text(
                text = cityName,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = countryName,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.tertiary
            )
        }

        // Centered time
        Text(
            text = currentTime,
            fontWeight = FontWeight.Normal,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.align(Alignment.Center)
        )

        // Right-aligned add button
        IconButton(
            onClick = onAddCityClick,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(56.dp) // Optional size adjustment
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "Add City",
                tint = Color.Unspecified,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
fun ShowMessage(image: Int?, message: String?, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = image!!),
            contentDescription = message,
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = message!!,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { onClick() }) {
            Text(text = "")
        }
    }
}


@Preview
@Composable
fun MainScreenPreview() {
    Surface {
        MainScreen(navController = rememberNavController())
    }
}
