package com.aligkts.peoplemanagement.scene.main

import androidx.lifecycle.viewModelScope
import com.aligkts.peoplemanagement.R
import com.aligkts.peoplemanagement.base.BaseViewModel
import com.aligkts.peoplemanagement.data.local.datasource.FetchCompletionHandler
import com.aligkts.peoplemanagement.data.local.datasource.FetchError
import com.aligkts.peoplemanagement.data.local.datasource.FetchResponse
import com.aligkts.peoplemanagement.domain.people.GetPeopleUseCase
import com.aligkts.peoplemanagement.internal.util.CommonValue.DEFAULT_PAGE_SIZE
import com.aligkts.peoplemanagement.internal.util.CommonValue.DEFAULT_START_PAGE
import com.aligkts.peoplemanagement.scene.main.peoplelist.*
import com.aligkts.peoplemanagement.view.map.PeopleListUiMapper
import com.aligkts.peoplemanagement.view.model.PeopleItemUiModel
import com.aligkts.peoplemanagement.view.model.TextMessageUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class MainViewModel : BaseViewModel() {

    lateinit var getPeopleUseCase: GetPeopleUseCase
    lateinit var peopleListUiMapper: PeopleListUiMapper

    private val initialPeopleListState = DefaultState(DEFAULT_START_PAGE, false, emptyList())
    private val _peopleListState = MutableStateFlow<PeopleListState>(initialPeopleListState)
    val peopleListState: StateFlow<PeopleListState> = _peopleListState.asStateFlow()

    fun updatePeopleList() {
        val pageNumber = getCurrentPageNum()
        _peopleListState.value = if (pageNumber == DEFAULT_START_PAGE) {
            LoadingState(pageNumber, false, getCurrentData())
        } else {
            PaginationState(pageNumber, false, getCurrentData())
        }
        getPeopleList(pageNumber)
    }

    fun resetPeopleList() {
        _peopleListState.value = LoadingState(DEFAULT_START_PAGE, false, emptyList())
        updatePeopleList()
    }

    fun restorePeopleList() {
        _peopleListState.value = DefaultState(getCurrentPageNum(), false, getCurrentData())
    }

    private fun getPeopleList(
        next: Int? = null
    ) = viewModelScope.launch {
        getPeopleUseCase
            .run(GetPeopleUseCase.Params(next, callback))
    }

    private val callback: FetchCompletionHandler = { response, error ->
        if (fetchSuccess(response, error)) {
            handlePeopleSuccess(response)
        } else {
            handleFailure(error)
        }
    }

    private fun handlePeopleSuccess(response: FetchResponse?) {
        val peopleList = peopleListUiMapper.map(response)

        if (peopleList.isEmpty()) {
            handleEmptyPeopleList()
        } else {
            handlePeopleList(peopleList)
        }
    }

    private fun handlePeopleList(peopleList: List<PeopleItemUiModel>) {
        val currentPeopleList = getCurrentData().toMutableList()
        val currentPageNum = getCurrentPageNum().inc()
        val areAllItemsLoaded = peopleList.size < DEFAULT_PAGE_SIZE
        currentPeopleList.addAll(peopleList)
        _peopleListState.value =
            DefaultState(currentPageNum, areAllItemsLoaded, currentPeopleList)
    }

    private fun handleEmptyPeopleList() {
        _peopleListState.value = EmptyState(
            TextMessageUiModel.StringResource(R.string.empty_list_error),
            getCurrentPageNum(),
            true,
            getCurrentData()
        )
    }

    private fun handleFailure(error: FetchError?) {
        val errorMessageUiModel = error?.let {
            TextMessageUiModel.DynamicString(it.errorDescription)
        } ?: TextMessageUiModel.StringResource(R.string.unknown_error)
        _peopleListState.value = ErrorState(
            errorMessageUiModel,
            getCurrentPageNum(),
            getCurrentLoadedAllItems(),
            getCurrentData()
        )
    }

    private fun fetchSuccess(fetchResponse: FetchResponse?, fetchError: FetchError?): Boolean {
        return fetchResponse != null && fetchError == null
    }

    private fun getCurrentPageNum() = _peopleListState.value.pageNumber

    private fun getCurrentData() = _peopleListState.value.data

    private fun getCurrentLoadedAllItems() = _peopleListState.value.loadedAllItems
}