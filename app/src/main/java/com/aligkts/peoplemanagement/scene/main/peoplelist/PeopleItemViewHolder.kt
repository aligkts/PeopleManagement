package com.aligkts.peoplemanagement.scene.main.peoplelist

import com.aligkts.peoplemanagement.base.BaseViewHolder
import com.aligkts.peoplemanagement.databinding.RecyclerviewItemPeopleBinding
import com.aligkts.peoplemanagement.view.model.PeopleItemUiModel

/**
 * Created by Ali Göktaş on 24.07.2022.
 */

class PeopleItemViewHolder(
    private val binding: RecyclerviewItemPeopleBinding
) : BaseViewHolder<RecyclerviewItemPeopleBinding>(binding) {

    fun bind(onItemClick: ((PeopleItemUiModel) -> Unit)? = null, item: PeopleItemUiModel) {
        binding.apply {
            txtPeopleItem.text = item.getCombinedIdName()
            root.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }
    }
}