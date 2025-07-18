package uz.kabir.weather

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import uz.kabir.weather.di.databaseModule
import uz.kabir.weather.di.networkModule
import uz.kabir.weather.di.repositoryModule
import uz.kabir.weather.di.useCaseModule
import uz.kabir.weather.di.viewModelModule

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@WeatherApp)
            modules(
                networkModule,
                repositoryModule,
                useCaseModule,
                viewModelModule,
                databaseModule
            )
        }
    }
}
