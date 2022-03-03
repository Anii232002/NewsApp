package com.example.newsapp

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter( private val listener : NewsItemClicked): RecyclerView.Adapter<NewsViewHolder>() {

    private val items: ArrayList<News> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.newsitem,parent,false)
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
       return items.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
     val currentItem = items[position]
        holder.titleView.text = currentItem.title
        holder.author.text = currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.urlimage).into(holder.image)
    }

    fun updateNews(updatedNews : ArrayList<News>)   // update news is used to update the news on a timely basis or else the news would be the same everythime
    {
        items.clear()                // first all the items would be needed to be clear
        items.addAll(updatedNews)    //then the updated news will be added from updatedNews Array to items

        notifyDataSetChanged()     // this will notify adapter to change the news
    }

}


class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val titleView:TextView = itemView.findViewById(R.id.title)
    val image : ImageView = itemView.findViewById(R.id.img)
    val author:TextView = itemView.findViewById(R.id.author)
}

interface NewsItemClicked
{

    fun onItemClicked(item:News)
}