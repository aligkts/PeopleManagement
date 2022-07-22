package com.aligkts.peoplemanagement.internal.injection

import com.aligkts.peoplemanagement.application.MainApplication
import com.aligkts.peoplemanagement.scene.main.MainActivity

/**
 * Created by Ali Göktaş on 22.07.2022.
 */

object DependencyComponent {

    fun inject(application: MainApplication) {
        application.appContainer
    }

    fun inject(activity: MainActivity) {
        val application: MainApplication = activity.application as MainApplication
        with(activity.viewModel) {
            getPeopleUseCase = application.appContainer.getPeopleUseCase
            peopleListUiMapper = application.appContainer.peopleListUiMapper
        }
    }
}