package example.com.allaboutdogs

import android.app.Application
import example.com.allaboutdogs.AllAboutDogsApplication.Global.appComponent
import example.com.allaboutdogs.di.AppComponent
import example.com.allaboutdogs.di.AppModule
import example.com.allaboutdogs.di.DaggerAppComponent


class AllAboutDogsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    object Global {
        var appComponent: AppComponent? = null
    }
}