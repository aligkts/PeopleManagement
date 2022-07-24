package com.aligkts.peoplemanagement.view.map

import com.aligkts.peoplemanagement.base.Mapper
import com.aligkts.peoplemanagement.data.local.datasource.FetchResponse
import com.aligkts.peoplemanagement.view.model.PeopleItemUiModel

/**
 * Created by Ali Göktaş on 22.07.2022.
 */

class PeopleListUiMapper : Mapper<List<PeopleItemUiModel>, FetchResponse?> {
    override fun map(input: FetchResponse?): List<PeopleItemUiModel> {
        return input?.let {
            input.people.map { person ->
                PeopleItemUiModel(
                    id = person.id.toString(),
                    name = person.fullName,
                )
            }.distinctBy { it.id }
        } ?: emptyList()
    }
}
