package com.aligkts.peoplemanagement.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Created by Ali Göktaş on 24.07.2022.
 */

abstract class BaseViewHolder<T : ViewBinding>(
    binding: T
) : RecyclerView.ViewHolder(binding.root)