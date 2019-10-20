package example.com.allaboutdogs.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import example.com.allaboutdogs.AllAboutDogsApplication
import example.com.allaboutdogs.R
import example.com.allaboutdogs.interfaces.BreedListClickListener
import example.com.allaboutdogs.ui.fragments.BreedListFragment
import example.com.allaboutdogs.ui.fragments.SingleBreedFragment
import example.com.allaboutdogs.ui.viewmodels.SingleBreedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BreedsActivity : AppCompatActivity(), BreedListClickListener {
    private val rootFragmentTag = "BreedListFragment"
    private val childFragmentTag = "SingleBreedFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AllAboutDogsApplication.Global.appComponent?.inject(this)

        setContentView(R.layout.activity_breeds)

        supportFragmentManager.beginTransaction()
            .replace(R.id.rootView, BreedListFragment(), rootFragmentTag)
            .commit()
    }

    override fun onClick(breed: String) {

        supportFragmentManager.beginTransaction()
            .replace(R.id.rootView, SingleBreedFragment.Companion.newInstance(breed), childFragmentTag)
            .addToBackStack(childFragmentTag)
            .commit()
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 0)  {
            supportFragmentManager.popBackStackImmediate()

            return
        }

        super.onBackPressed()
    }
}
