package com.irsyad.filmku

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.irsyad.filmku.data.response.ResultsItem
import com.irsyad.filmku.databinding.ItemPopulerBinding

class MainAdapter(
    private var listC: List<ResultsItem>,
    private val mContext: Context
) : RecyclerView.Adapter<MainAdapter.ListViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(item: ResultsItem)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    inner class ListViewHolder(private val binding: ItemPopulerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.icImage.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = listC[position]
                    onItemClickListener?.onItemClick(item)
                }
            }
        }

        fun setData(character: ResultsItem, context: Context) {
            // Set judul ke TextView, handle kasus null
            binding.icTitle.text = character.title ?: "Unknown Title"

            // Memuat gambar menggunakan Glide untuk posterPath
            character.posterPath?.let { posterPath ->
                val posterURL = "https://image.tmdb.org/t/p/w500$posterPath"
                Glide.with(context)
                    .load(posterURL)
                    .into(binding.icImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemPopulerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listC.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listC[position]
        holder.setData(data, mContext)
    }

    fun setData(data: List<ResultsItem>) {
        listC = data
        notifyDataSetChanged()
    }
}
