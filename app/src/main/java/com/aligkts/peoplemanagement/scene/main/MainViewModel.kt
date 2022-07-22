package com.aligkts.peoplemanagement.scene.main

import androidx.lifecycle.viewModelScope
import com.aligkts.peoplemanagement.base.BaseViewModel
import com.aligkts.peoplemanagement.data.local.datasource.FetchCompletionHandler
import com.aligkts.peoplemanagement.data.local.datasource.FetchError
import com.aligkts.peoplemanagement.data.local.datasource.FetchResponse
import com.aligkts.peoplemanagement.domain.people.GetPeopleUseCase
import com.aligkts.peoplemanagement.view.map.PeopleListUiMapper
import com.aligkts.peoplemanagement.view.model.PeopleItemUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class MainViewModel : BaseViewModel() {

    lateinit var getPeopleUseCase: GetPeopleUseCase
    lateinit var peopleListUiMapper: PeopleListUiMapper

    private val _peopleList = MutableStateFlow<List<PeopleItemUiModel>?>(null)
    val peopleList: StateFlow<List<PeopleItemUiModel>?> = _peopleList.asStateFlow()

    private val callback: FetchCompletionHandler = { response, error ->
        hideProgress()
        if (fetchSuccess(response, error)) {
            handlePeopleSuccess(response)
        } else {
            handleFailure(error)
        }
    }

    fun getPeople(
        next: String? = null
    ) = viewModelScope.launch {
        showProgress()
        getPeopleUseCase
            .run(GetPeopleUseCase.Params(next, callback))
    }

    private fun handlePeopleSuccess(response: FetchResponse?) {
        _peopleList.value = peopleListUiMapper.map(response)
    }

    private fun fetchSuccess(fetchResponse: FetchResponse?, fetchError: FetchError?): Boolean {
        return fetchResponse != null && fetchError == null
    }
}