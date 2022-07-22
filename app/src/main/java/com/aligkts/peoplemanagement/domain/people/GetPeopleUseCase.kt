package com.aligkts.peoplemanagement.domain.people

import com.aligkts.peoplemanagement.data.local.datasource.FetchCompletionHandler
import com.aligkts.peoplemanagement.data.repository.PeopleRepository
import com.aligkts.peoplemanagement.internal.util.usecase.UseCase

/**
 * Created by Ali Göktaş on 22.07.2022.
 */

class GetPeopleUseCase(
    private val peopleRepository: PeopleRepository
) : UseCase<Unit, GetPeopleUseCase.Params>() {

    override suspend fun buildUseCase(params: Params) {
        peopleRepository.fetchPeople(params.next, params.callback)
    }

    data class Params(
        val next: String?,
        val callback: FetchCompletionHandler
    )

    /*override fun invoke(fetchResponse: FetchResponse?, fetchError: FetchError?) {
        if (fetchSuccess(fetchResponse, fetchError)) {
            fetchResponse?.let {
                peopleUiMapper.map(it)
            }
        } else {
            fetchError?.let {
                val errorMessage = it.errorDescription
            }
        }
    }*/
}