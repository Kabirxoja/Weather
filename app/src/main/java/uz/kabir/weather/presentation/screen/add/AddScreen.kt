package uz.kabir.weather.presentation.screen.add

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import uz.kabir.weather.R
import uz.kabir.weather.domain.model.CityGeoDomain
import uz.kabir.weather.domain.model.CurrentWeatherDomain
import uz.kabir.weather.domain.model.GeoInfoDomain
import uz.kabir.weather.presentation.state.WeatherCurrentResult

@Composable
fun AddScreen() {

    val viewModel: AddViewModel = koinViewModel()

    val state by viewModel.state.collectAsState()

    // Avtomatik holat: qidiruv matni bor yoki yo‘qligiga qarab
    val isFocused = state.query.isNotBlank()

    Log.d("AddScreen", "Current query: ${state.getCurrentWeatherData}")


    var localQuery by remember { mutableStateOf(state.query) }

    // ViewModel'dan kelgan yangi query bilan UI ni sinxronlashtiramiz
    LaunchedEffect(state.query) {
        localQuery = state.query
    }
    Log.d("AddScreen", "savedCityGeoList: ${state.savedCityGeoList}")

    LaunchedEffect(Unit) {
        viewModel.sendIntent(AddIntent.LoadCityGeo)
        viewModel.sendIntent(AddIntent.GetCurrentWeather(""))
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // 🔍 Qidiruv TextField
            SearchSection(
                query = localQuery,
                onValueChange = { newQuery ->
                    localQuery = newQuery
                    viewModel.sendIntent(AddIntent.QueryChanged(newQuery))
                }
            )


            Spacer(modifier = Modifier.height(16.dp))

            if (isFocused) {
                when {

                    state.cityGeoList != null -> {
                        SearchCityList(
                            cityList = state.cityGeoList,
                            onCityClick = { //city, country, lat, lon
                                viewModel.sendIntent(AddIntent.GetCurrentWeather(it.name)) //api request olib kelishi
                                viewModel.sendIntent(AddIntent.AddCityGeo(it)) // room ga saqlidi
                                viewModel.sendIntent(
                                    AddIntent.SaveSelectedCity(
                                        GeoInfoDomain(
                                            city = it.name,
                                            country = it.country,
                                            lat = it.lat,
                                            lon = it.lon
                                        )
                                    )
                                )
                            })
                    }

                    state.isLoading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    }

                    state.errorMessage != null -> {
                        Text(
                            text = "Error: ${state.errorMessage}",
                            color = Color.Red,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }


                }
            } else {


                state.getCurrentWeatherData.forEach { result ->
                    when (result) {
                        is WeatherCurrentResult.Success -> {
                            CityItem(weather = result.data, onCityClick = { //currentweather
                                viewModel.sendIntent(
                                    AddIntent.SaveSelectedCity(
                                        GeoInfoDomain(
                                            city = result.data.cityName,
                                            country = result.data.country,
                                            lat = result.data.lat,
                                            lon = result.data.lon
                                        )
                                    )
                                ) //type is different
                            })
                        }

                        is WeatherCurrentResult.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }

                        is WeatherCurrentResult.Error -> {
                            Text(
                                text = "Error: ${result.message}",
                                color = Color.Red,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SearchSection(
    modifier: Modifier = Modifier,
    hint: String = "Search...",
    query: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = query,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp)
            .border(1.dp, LightGray, RoundedCornerShape(16.dp)),
        placeholder = {
            Text(
                text = hint,
                modifier = Modifier.wrapContentHeight(Alignment.CenterVertically)
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onValueChange("") }) {  // ✅ BU MUHIM
                    Icon(
                        painter = painterResource(id = R.drawable.ic_wind),
                        contentDescription = "Clear search",
                        modifier = Modifier
                            .size(24.dp)
                            .padding(4.dp),
                        tint = Color.Gray
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        )
    )
}


@Composable
fun SearchCityList(
    cityList: List<CityGeoDomain>,
    onCityClick: (CityGeoDomain) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(cityList) { city ->
            CityGeoItem(
                cityName = city.name,
                country = city.country,
                lon = city.lon,
                lat = city.lat,
                onClick = { onCityClick(city) }
            )
        }
    }
}


@Composable
fun CityGeoItem(
    cityName: String,
    country: String,
    lon: Double,
    lat: Double,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = cityName,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = country,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = "Location",
            tint = Color.Unspecified,
            modifier = Modifier
                .size(52.dp)
                .clickable {
                    onClick()
                }
        )
    }
}


@Composable
fun CityItem(weather: CurrentWeatherDomain, onCityClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            .clickable { onCityClick() },
        verticalAlignment = Alignment.CenterVertically

    ) {
        Column {
            Text(
                text = weather.cityName,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = weather.country,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        val image = if (weather.weatherDescription == "Rainy") {
            R.drawable.icon_rainy
        } else if (weather.weatherDescription == "Sunny") {
            R.drawable.icon_sunny
        } else {
            R.drawable.icon_cloudy
        }

        Spacer(modifier = Modifier.weight(1f)) // ← BU MUHIM

        Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
            Text(
                "${weather.temperature} °C",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(end = 8.dp)
            )

            Icon(
                painter = painterResource(id = image),
                contentDescription = "Location",
                tint = Color.Unspecified,
                modifier = Modifier.size(52.dp)
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun AddCountryScreenPreview() {
    AddScreen()
}

