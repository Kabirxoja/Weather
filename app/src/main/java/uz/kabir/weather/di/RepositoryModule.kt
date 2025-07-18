package uz.kabir.weather.di

import org.koin.dsl.module
import uz.kabir.weather.data.repository.LocalRepositoryImp
import uz.kabir.weather.data.repository.NetworkRepositoryImp
import uz.kabir.weather.domain.repository.LocalRepository
import uz.kabir.weather.domain.repository.NetworkRepository

val repositoryModule = module {
    single<NetworkRepository> { NetworkRepositoryImp(get()) }
    single<LocalRepository> { LocalRepositoryImp(get(), get()) }
}