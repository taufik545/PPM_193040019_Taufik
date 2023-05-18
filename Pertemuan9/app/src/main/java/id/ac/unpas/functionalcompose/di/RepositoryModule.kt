package id.ac.unpas.functionalcompose.di
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import id.ac.unpas.functionalcompose.networks.SetoranSampahApi
import id.ac.unpas.functionalcompose.persistences.SetoranSampahDao
import id.ac.unpas.functionalcompose.repositories.SetoranSampahRepository
@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideSetoranSampahRepository(
        api: SetoranSampahApi,
        dao: SetoranSampahDao
    ): SetoranSampahRepository {
        return SetoranSampahRepository(api, dao)
    }
}
