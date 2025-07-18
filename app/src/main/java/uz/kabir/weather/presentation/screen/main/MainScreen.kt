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
import androidx.compose.runtime.MutableState
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

    // Internet mavjud emasligini aniqlash
    val noInternet =
        weatherState is WeatherCurrentResult.Error && (weatherState as WeatherCurrentResult.Error).message == "No internet connection"
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
        var sunrise = ""
        var sunset = ""
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
                sunrise = state.data.sunrise.toString()
                sunset = state.data.sunset.toString()
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
                date = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm")
                    .format(java.util.Date(timestamp * 1000))
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
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MainTopBar(
                onAddCityClick = { navController.navigate(AppDestination.Add.route) },
                cityName = city
            )

            Text(
                text = date,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Weather icon and temperature
            Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.Center) {

                Text(
                    text = temperature,
                    fontSize = 72.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground

                )
                Text(
                    text = "°C",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
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
                        AirQualityItem("O3", "12")
                        AirQualityItem("NO2", "34")
                        AirQualityItem("CO", "05")
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        AirQualityItem("PM2.5", "22")
                        AirQualityItem("PM10", "15")
                        AirQualityItem("SO2", "10")
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
                SunInfoItem(R.drawable.ic_sunrise, "Sunrise", sunrise)
                SunInfoItem(R.drawable.ic_sunset, "Sunset", sunset)
            }
        }
    }
}

@Composable
fun AirQualityItem(
    textLeft: String,
    textRight: String,
    modifier: Modifier = Modifier
) {
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
                .background(Color.Blue)
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
fun MainTopBar(onAddCityClick: () -> Unit, cityName: String) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = cityName,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.align(Alignment.Center)
        )
        IconButton(onClick = { onAddCityClick() }, modifier = Modifier.align(Alignment.CenterEnd)) {
            Icon(
                modifier = Modifier
                    .size(56.dp)
                    .padding(2.dp),
                painter = painterResource(id = R.drawable.ic_add),
                tint = Color.Unspecified,
                contentDescription = "Add City"
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
            Text(text = "Try again")
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
