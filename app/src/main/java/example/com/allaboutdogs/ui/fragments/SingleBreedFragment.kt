package example.com.allaboutdogs.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import example.com.allaboutdogs.AllAboutDogsApplication
import example.com.allaboutdogs.R
import example.com.allaboutdogs.managers.BreedManager
import example.com.allaboutdogs.network.Service
import example.com.allaboutdogs.ui.BreedImageAdapter
import example.com.allaboutdogs.ui.fragments.SingleBreedFragment.Companion.BREED_NAME
import example.com.allaboutdogs.ui.viewmodels.ImageFetchSuccess
import example.com.allaboutdogs.ui.viewmodels.SingleBreedViewModel
import example.com.allaboutdogs.ui.viewmodels.SingleBreedViewModelFactory
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

class SingleBreedFragment : Fragment() {


    private lateinit var breedName: String
    private lateinit var singleBreedViewModel: SingleBreedViewModel
    private lateinit var breedImgAdapter: BreedImageAdapter

    @Inject
    lateinit var breedManager: BreedManager

    @Inject
    lateinit var service: Service

    override fun onAttach(context: Context) {
        super.onAttach(context)

        AllAboutDogsApplication.Global.appComponent?.inject(this)

        singleBreedViewModel = ViewModelProviders.of(this, SingleBreedViewModelFactory(service))[SingleBreedViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        breedName = arguments?.getString(BREED_NAME) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridLayoutManager = GridLayoutManager(context, 3)

        breedImgAdapter = BreedImageAdapter(breedManager.singleBreedImageMap[breedName] ?: emptyList())

        recyclerView.adapter = breedImgAdapter
        recyclerView.layoutManager = gridLayoutManager

        fetchImages()
    }

    override fun onResume() {
        super.onResume()

        (activity as AppCompatActivity).supportActionBar?.title = breedName.capitalize()
    }

    private fun fetchImages() {

        singleBreedViewModel.observer.observe(this, Observer {

            when(it) {

                is ImageFetchSuccess -> {
                    if (it.images.isNullOrEmpty()) {
                        emptyView.visibility = View.VISIBLE
                    } else {
                        emptyView.visibility = View.GONE

                        breedManager.singleBreedImageMap[breedName] = it.images

                        breedImgAdapter.imageList = it.images

                        breedImgAdapter.notifyDataSetChanged()
                    }
                }
            }
        })

        singleBreedViewModel.fetchBreedsImages(breedName)

    }


    object Companion {
        const val BREED_NAME = "breedName"

        fun newInstance(breedName: String) : SingleBreedFragment {
            return SingleBreedFragment().also {
                val bundle = Bundle().also {
                    it.putString(BREED_NAME, breedName)
                }

                it.arguments = bundle
            }
        }
    }
}
