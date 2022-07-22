package com.aligkts.peoplemanagement.scene.main

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.aligkts.peoplemanagement.base.BaseBindingActivity
import com.aligkts.peoplemanagement.databinding.ActivityMainBinding
import com.aligkts.peoplemanagement.internal.injection.DependencyComponent
import com.aligkts.peoplemanagement.internal.util.extension.showIf
import com.aligkts.peoplemanagement.internal.util.extension.toast
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity :
    BaseBindingActivity<MainViewModel, ActivityMainBinding>() {

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeData()
        DependencyComponent.inject(this)
        viewModel.getPeople()
    }

    private fun observeData() = lifecycleScope.launchWhenStarted {
        viewModel.peopleList
            .filterNotNull()
            .onEach { peopleList ->
                toast(peopleList.toString())
            }.launchIn(this)

        viewModel.showProgress
            .onEach { showProgress ->
                changeProgressVisibility(showProgress)
                toast("Show Progress: $showProgress")
            }.launchIn(this)

        viewModel.failurePopup
            .onEach { message ->
                toast("failurePopup: ${message.asString(this@MainActivity)}")
            }.launchIn(this)
    }

    private fun changeProgressVisibility(visible: Boolean) {
        binding.llProgressBar.showIf(visible)
    }
}
