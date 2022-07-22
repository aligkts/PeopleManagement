package com.aligkts.peoplemanagement.application

import android.app.Application
import com.aligkts.peoplemanagement.internal.injection.AppContainer
import com.aligkts.peoplemanagement.internal.injection.DependencyComponent

/**
 * Created by Ali Göktaş on 22.07.2022.
 */

class MainApplication : Application() {

    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer()
        DependencyComponent.inject(this)
    }

}