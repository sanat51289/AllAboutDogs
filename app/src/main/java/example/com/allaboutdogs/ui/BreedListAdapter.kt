package example.com.allaboutdogs.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import example.com.allaboutdogs.R
import example.com.allaboutdogs.interfaces.BreedListClickListener


class BreedListAdapter(private val breedMap: Map<String, String>) :
    RecyclerView.Adapter<BreedListAdapter.BreedListViewHolder>() {

    private var listener: BreedListClickListener? = null
    private val breedList = breedMap.keys.toList()

    fun setItemClickListener(listener: BreedListClickListener) {
        this.listener = listener
    }

    private val clickListener = View.OnClickListener {
        listener?.onClick(it.tag as String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.breed_list_item, parent, false)

        view.setOnClickListener(clickListener)

        return BreedListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return breedMap.size
    }

    override fun onBindViewHolder(holder: BreedListViewHolder, position: Int) {
        val breed = breedList[position]
        holder.setItem(breed)
    }

    inner class BreedListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val breedImage = itemView.findViewById<ImageView>(R.id.breedFace)
        val breedName = itemView.findViewById<TextView>(R.id.breedName)

        fun setItem(breed: String) {
            itemView.tag = breed
            breedName.text = breed

            Glide.with(itemView.context)
                .load(breedMap[breed])
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(breedImage)
        }
    }

}
