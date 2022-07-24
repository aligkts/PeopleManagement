package com.aligkts.peoplemanagement.scene.main.peoplelist

import com.aligkts.peoplemanagement.view.model.PeopleItemUiModel
import com.aligkts.peoplemanagement.view.model.TextMessageUiModel

/**
 * Created by Ali Göktaş on 24.07.2022.
 */

sealed class PeopleListState {
    abstract val pageNumber: Int
    abstract val loadedAllItems: Boolean
    abstract val data: List<PeopleItemUiModel>
}

data class LoadingState(
    override val pageNumber: Int,
    override val loadedAllItems: Boolean,
    override val data: List<PeopleItemUiModel>
) : PeopleListState()

data class PaginationState(
    override val pageNumber: Int,
    override val loadedAllItems: Boolean,
    override val data: List<PeopleItemUiModel>
) : PeopleListState()

data class DefaultState(
    override val pageNumber: Int,
    override val loadedAllItems: Boolean,
    override val data: List<PeopleItemUiModel>
) : PeopleListState()

data class EmptyState(
    val errorMessageUiModel: TextMessageUiModel,
    override val pageNumber: Int,
    override val loadedAllItems: Boolean,
    override val data: List<PeopleItemUiModel>
) : PeopleListState()

data class ErrorState(
    val errorMessageUiModel: TextMessageUiModel,
    override val pageNumber: Int,
    override val loadedAllItems: Boolean,
    override val data: List<PeopleItemUiModel>
) : PeopleListState()