package com.example.betbracket.players.playerForm

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.betbracket.R
import com.google.android.material.imageview.ShapeableImageView

class AvatarDialogAdapter(
    private val imageList: List<String>,
    private val onClickListener:(Int)-> Unit) :
    RecyclerView.Adapter<AvatarDialogAdapter.AvatarDialogViewHolder>() {

    inner class AvatarDialogViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ShapeableImageView>(R.id.player_image)
        fun render(item: String, onClickListener: (Int) -> Unit) {
            Glide.with(image.context).load(item).into(image)
            image.setOnClickListener{
                Log.i("IMAGE", "Click listened")
                onClickListener(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvatarDialogViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AvatarDialogViewHolder(layoutInflater.inflate(R.layout.view_avatar, parent, false))
    }

    override fun getItemCount(): Int = imageList.size

    override fun onBindViewHolder(holder: AvatarDialogViewHolder, position: Int) {
        val item = imageList[position]
        return holder.render(item, onClickListener)
    }
}