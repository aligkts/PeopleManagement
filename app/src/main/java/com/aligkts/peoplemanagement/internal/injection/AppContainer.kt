package com.aligkts.peoplemanagement.internal.injection

import com.aligkts.peoplemanagement.data.local.datasource.DataSource
import com.aligkts.peoplemanagement.data.repository.PeopleRepository
import com.aligkts.peoplemanagement.domain.people.GetPeopleUseCase
import com.aligkts.peoplemanagement.view.map.PeopleListUiMapper

/**
 * Created by Ali Göktaş on 22.07.2022.
 */

class AppContainer {
    private val peopleLocalDataSource: DataSource = DataSource()
    private val peopleRepository: PeopleRepository = PeopleRepository(peopleLocalDataSource)
    val peopleListUiMapper: PeopleListUiMapper = PeopleListUiMapper()
    val getPeopleUseCase: GetPeopleUseCase = GetPeopleUseCase(peopleRepository)
}