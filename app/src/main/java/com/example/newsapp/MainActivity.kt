package com.example.newsapp

import android.net.Uri
import android.os.Bundle

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), NewsItemClicked {
     private lateinit var recyclerView: RecyclerView
    private lateinit var mAdapter: NewsListAdapter //the keyword m is added infront of a variable if you ant to make ti accessible in any of the functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerview)

        recyclerView.layoutManager = LinearLayoutManager(this)

         fetchData()
         mAdapter = NewsListAdapter(this)
        recyclerView.adapter = mAdapter


    }

   private  fun fetchData(){

         // Instantiate the RequestQueue.
         val url = "http://api.mediastack.com/v1/news?access_key=d75b097d9e4e1c49f48e34d3f73f1b9a&countries=us&languages=en"

// Request a string response from the provided URL.
         val jsonObjectRequest = JsonObjectRequest(
                 Request.Method.GET,
                 url,
                 null,
                 {
                val newsJsonArray = it.getJSONArray("data")  // in our api there is a array of jsongroups(group of news articles) in which there are many jsonitems(each news) further in which..
                     //.. each item has its own 4 attributes called title ,author ,url of the website and the image as a string in urlToImage . Each news has all these attributes
                     val newsArray = ArrayList<News>()   // here a newsArray is created of type Array list of NEws. News is the data class  which has the capacity to hold 4 attributes..
                     // of each news  which  are parsed below
                     for(i in 0 until newsJsonArray.length())
                     {
                         val newsJsonObject = newsJsonArray.getJSONObject(i) //  each news item is received by the . operator  as each item is in newsJsonArray (JSONArray ie "articles")..
                         // now newsJsonObject has the attributes of each news article
                         val news = News(
                                 newsJsonObject.getString("title"),   // attribute of each news article is received by again . operator in newsJsonObject.getString("ATTRIBUTE NAME")
                                         newsJsonObject.getString("author"),
                                         newsJsonObject.getString("url"),
                                         newsJsonObject.getString("image")
                         )
                         newsArray.add(news)
                     }
                     mAdapter.updateNews(newsArray) // here we are updating the news when the updated news comes
                 },
                 {


                 }

         )


// Add the request to the RequestQueue.
         MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

     }


    override fun onItemClicked(item :News)
    { // when you click on  a news article it should open in app iteslef in chrome custom tab feature..below is the code for this..do add customtab dependency in gradle

        val builder = CustomTabsIntent.Builder()
      val  customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url));
    }

}