package example.com.allaboutdogs.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import example.com.allaboutdogs.AllAboutDogsApplication
import example.com.allaboutdogs.R
import example.com.allaboutdogs.managers.BreedManager
import example.com.allaboutdogs.ui.BreedListAdapter
import example.com.allaboutdogs.ui.BreedsActivity
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject


class BreedListFragment : Fragment() {

    @Inject
    lateinit var breedManager: BreedManager

    override fun onAttach(context: Context) {
        super.onAttach(context)

        AllAboutDogsApplication.Global.appComponent?.inject(this)
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

        val linearLayoutManager = LinearLayoutManager(context)

        val breedListAdapter = BreedListAdapter(breedManager.breedsImageMap)
        breedListAdapter.setItemClickListener(activity as BreedsActivity)

        recyclerView.adapter = breedListAdapter
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addItemDecoration(DividerItemDecoration(context, linearLayoutManager.orientation))
    }

    override fun onResume() {
        super.onResume()

        (activity as AppCompatActivity).supportActionBar?.title = "Breeds"
    }
}