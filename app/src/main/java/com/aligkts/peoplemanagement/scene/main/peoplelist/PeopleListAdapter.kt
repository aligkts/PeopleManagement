package com.aligkts.peoplemanagement.scene.main.peoplelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aligkts.peoplemanagement.base.BaseViewHolder
import com.aligkts.peoplemanagement.databinding.RecyclerviewItemPeopleBinding
import com.aligkts.peoplemanagement.internal.util.pagination.PaginationAdapter
import com.aligkts.peoplemanagement.view.model.PeopleItemUiModel


class PeopleListAdapter(
    private var onItemClick: ((PeopleItemUiModel) -> Unit)? = null
) : PaginationAdapter<PeopleItemUiModel>() {

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding = RecyclerviewItemPeopleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PeopleItemViewHolder(itemBinding)
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is PeopleItemViewHolder)
            holder.bind(onItemClick, dataList[position])
    }

    override fun addLoadingViewFooter() {
        addLoadingViewFooter(PeopleItemUiModel())
    }

    fun updateData(newData: List<PeopleItemUiModel>) {
        val fromIndex = dataList.size
        dataList = newData.toMutableList()
        notifyItemRangeInserted(fromIndex, newData.size)
    }

    fun clearData() {
        dataList.clear()
        notifyDataSetChanged()
    }
}
