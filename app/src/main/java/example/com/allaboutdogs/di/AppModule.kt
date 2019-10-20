package example.com.allaboutdogs.di

import android.content.Context
import dagger.Module
import dagger.Provides
import example.com.allaboutdogs.managers.BreedManager
import example.com.allaboutdogs.network.Service
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideBreedManager() : BreedManager {
        return BreedManager()
    }

    @Provides
    @Singleton
    fun provideService(): Service {
        return Service(context)
    }
}