package example.com.allaboutdogs.di

import dagger.Component
import example.com.allaboutdogs.ui.BreedsActivity
import example.com.allaboutdogs.ui.SplashActivity
import example.com.allaboutdogs.ui.fragments.BreedListFragment
import example.com.allaboutdogs.ui.fragments.SingleBreedFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(activity: BreedsActivity)
    fun inject(activity: SplashActivity)
    fun inject(fragment: BreedListFragment)
    fun inject(fragment: SingleBreedFragment)
}