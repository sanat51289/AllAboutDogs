package example.com.allaboutdogs.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import example.com.allaboutdogs.AllAboutDogsApplication
import example.com.allaboutdogs.R
import example.com.allaboutdogs.managers.BreedManager
import example.com.allaboutdogs.network.Service
import example.com.allaboutdogs.ui.viewmodels.*
import javax.inject.Inject


class SplashActivity : AppCompatActivity() {

    @Inject lateinit var breedManager: BreedManager

    @Inject
    lateinit var service: Service

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AllAboutDogsApplication.Global.appComponent?.inject(this)

        setContentView(R.layout.splash_activity)

        val breedViewModel = ViewModelProviders.of(this, BreedListViewModelFactory(service))[BreedListViewModel::class.java]

        breedViewModel.observer.observe(this, Observer {

            when (it) {

                is LoadingResp -> {
                    //no-op
                }

                is SuccessResp -> {

                    breedManager.breedsImageMap = it.breeds

                    val intent = Intent(this@SplashActivity, BreedsActivity::class.java)
                    startActivity(intent)

                    finish()
                }

                is ErrorResp -> {
                    finish()
                }
            }
        })

        breedViewModel.fetchBreeds()
    }
}