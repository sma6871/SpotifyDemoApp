package com.spotify.demo.ui.list

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.spotify.demo.R
import com.spotify.demo.data.models.ArtistItem
import com.spotify.demo.extensions.*
import kotlinx.android.synthetic.main.list_item.view.*

class ArtistListAdapter(val itemClickListener: (itemData: String) -> Unit) :
    RecyclerView.Adapter<ArtistListAdapter.ViewHolder>() {
    private var artists: List<ArtistItem> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            parent.inflate(
                R.layout.list_item,
                parent
            )
        )
    }

    override fun getItemCount() = artists.size

    /**
     * call this method to update artists list
     **/
    fun updateData(artists: List<ArtistItem>) {
        this.artists = artists
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        artists.getOrNull(position)?.let { artist ->
            holder.txtName.text = artist.name
            if (artist.images?.isNotEmpty() == true) {
                holder.imgThumbnail.loadImageWithGlide(
                    artist.images[0].url ?: "",
                    scaleType = GlideScaleType.CenterCrop,
                    diskCacheStrategy = DiskCacheStrategy.ALL
                )
            }
            holder.itemView.click {
                itemClickListener.invoke(artist.toJson())
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.txtName
        val imgThumbnail: ImageView = itemView.imgThumbnail
    }
}