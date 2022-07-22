package com.aligkts.peoplemanagement.internal.util.extension

import androidx.fragment.app.Fragment
import com.aligkts.peoplemanagement.scene.main.MainActivity

/**
 * Created by Ali Göktaş on 22.07.2022.
 */

fun Fragment.mainActivity(): MainActivity? = requireActivity() as? MainActivity
