package com.fktimp.news

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fktimp.news.adapters.OnLoadMoreListener
import com.fktimp.news.adapters.RecyclerViewLoadMoreScroll
import com.fktimp.news.adapters.WallAdapter
import com.fktimp.news.models.VKWallPost
import com.fktimp.news.requests.NewsHelper
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val wallPosts: ArrayList<VKWallPost> = ArrayList()
    lateinit var adapter: WallAdapter
    lateinit var scrollListener: RecyclerViewLoadMoreScroll


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NewsHelper.actualSources = NewsHelper.getSavedStringSets(this)
        initRecycler()
    }


    private fun initRecycler() {
        wallPosts.add(VKWallPost())
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = WallAdapter(this, wallPosts)
        recyclerView.adapter = adapter
        val linearLayoutManager =
            recyclerView.layoutManager as LinearLayoutManager?


        scrollListener = RecyclerViewLoadMoreScroll(linearLayoutManager as LinearLayoutManager)
        scrollListener.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                wallPosts.add(VKWallPost())
                adapter.notifyItemInserted(wallPosts.size - 1)
                NewsHelper.getData(this@MainActivity)
            }
        })
        recyclerView.addOnScrollListener(scrollListener)
        NewsHelper.getData(this)
    }

    fun updateRecycler(items: ArrayList<VKWallPost>) {
        deleteLoading()
        val startPos = wallPosts.size
        wallPosts.addAll(items)
        adapter.notifyItemRangeInserted(startPos, items.size)
    }

    fun deleteLoading() {
        wallPosts.removeAt(wallPosts.size - 1)
        adapter.notifyItemRemoved(wallPosts.size)
        scrollListener.setLoaded()
    }


    companion object {
        fun startFrom(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}
