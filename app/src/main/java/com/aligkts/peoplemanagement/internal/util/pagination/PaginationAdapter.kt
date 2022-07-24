package com.aligkts.peoplemanagement.internal.util.pagination

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aligkts.peoplemanagement.base.BaseViewHolder
import com.aligkts.peoplemanagement.databinding.RecyclerviewItemLoadingFooterBinding
import com.aligkts.peoplemanagement.internal.util.CommonValue.ONE
import com.aligkts.peoplemanagement.internal.util.CommonValue.ZERO

/**
 * Created by Ali Göktaş on 24.07.2022.
 */

private const val LOADING_VIEW_TYPE = 0
private const val ITEM_VIEW_TYPE = 1

abstract class PaginationAdapter<D> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    abstract fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    abstract fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int)
    abstract fun addLoadingViewFooter()

    private var isLoadingViewAdded = false
    protected var dataList = mutableListOf<D>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            LOADING_VIEW_TYPE -> {
                val itemBinding = RecyclerviewItemLoadingFooterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                LoadingViewHolder(itemBinding)
            }
            else -> onCreateItemViewHolder(parent, viewType)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) != LOADING_VIEW_TYPE) onBindItemViewHolder(holder, position)
    }

    override fun getItemCount() = dataList.size

    override fun getItemViewType(position: Int) =
        if (position == dataList.size - ONE && isLoadingViewAdded)
            LOADING_VIEW_TYPE
        else
            ITEM_VIEW_TYPE

    fun removeLoadingViewFooter() {
        if (isLoadingViewAdded && dataList.size > ZERO) {
            isLoadingViewAdded = false
            dataList.removeAt(dataList.size - ONE)
            notifyItemRemoved(dataList.size)
        }
    }

    protected fun addLoadingViewFooter(emptyDataObject: D) {
        if (dataList.size > ZERO) {
            isLoadingViewAdded = true
            dataList.add(emptyDataObject)
            notifyItemInserted(dataList.size - ONE)
        }
    }

}

class LoadingViewHolder(binding: RecyclerviewItemLoadingFooterBinding) :
    BaseViewHolder<RecyclerviewItemLoadingFooterBinding>(binding)