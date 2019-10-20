package example.com.allaboutdogs.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import example.com.allaboutdogs.R


class BreedImageAdapter(var imageList: List<String>) :
    RecyclerView.Adapter<BreedImageAdapter.BreedImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedImageViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.breed_images, parent, false)

        return BreedImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: BreedImageViewHolder, position: Int) {
        val imageUrl = imageList[position]
        holder.setItem(imageUrl)
    }


    inner class BreedImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageView = itemView.findViewById<ImageView>(R.id.breedFace)

        fun setItem(imageUrl: String) {
            Glide.with(itemView.context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageView)
        }
    }

}