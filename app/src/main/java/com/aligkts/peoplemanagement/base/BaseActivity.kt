package com.aligkts.peoplemanagement.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

/**
 * Created by Ali Göktaş on 22.07.2022.
 */

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {
    @Suppress("UNCHECKED_CAST")
    private val persistentViewModelClass =
        (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
    val viewModel: VM by lazy { ViewModelProvider(this)[persistentViewModelClass] }
}