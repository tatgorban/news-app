package com.fktimp.news.adapters

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

interface OnLoadMoreListener {
    fun onLoadMore()
    fun extraCondition(): Boolean
}

class RecyclerViewLoadMoreScroll(layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    private var visibleThreshold = 5
    private lateinit var mOnLoadMoreListener: OnLoadMoreListener
    var isLoading: Boolean = false
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0
    private var mLayoutManager: RecyclerView.LayoutManager = layoutManager


    fun setLoaded() {
        isLoading = false
    }

    fun setOnLoadMoreListener(mOnLoadMoreListener: OnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener
    }

    fun callOnLoadMore() {
        isLoading = true
        mOnLoadMoreListener.onLoadMore()
    }


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy <= 0) return
        totalItemCount = mLayoutManager.itemCount
        lastVisibleItem = (mLayoutManager as LinearLayoutManager).findLastVisibleItemPosition()
//        Log.d("M_SearchActivity", "$totalItemCount last = $lastVisibleItem")
        if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold && mOnLoadMoreListener.extraCondition()) {
            callOnLoadMore()
        }
    }
}