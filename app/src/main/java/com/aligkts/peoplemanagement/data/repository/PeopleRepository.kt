package com.aligkts.peoplemanagement.data.repository

import com.aligkts.peoplemanagement.data.local.datasource.DataSource
import com.aligkts.peoplemanagement.data.local.datasource.FetchCompletionHandler

/**
 * Created by Ali Göktaş on 22.07.2022.
 */

class PeopleRepository(
    private val dataSource: DataSource
) {

    fun fetchPeople(next: Int?, completionHandler: FetchCompletionHandler) {
        dataSource.fetch(next.toString(), completionHandler)
    }
}