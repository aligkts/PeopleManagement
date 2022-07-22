package com.aligkts.peoplemanagement.base

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.aligkts.peoplemanagement.application.MainApplication
import com.aligkts.peoplemanagement.internal.injection.AppContainer

/**
 * Created by Ali Göktaş on 22.07.2022.
 */

abstract class BaseBindingActivity<VM : BaseViewModel, VB : ViewBinding> : BaseActivity<VM>() {
    private lateinit var appContainer: AppContainer

    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        appContainer = (application as MainApplication).appContainer
    }

    abstract fun getViewBinding(): VB
}