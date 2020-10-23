package com.chencc.androidstudynotescode.lazyfragment.base2

import androidx.fragment.app.Fragment

/**
 *
 */
abstract class BaseFragment : Fragment(){

    private var isFirst = true

    override fun onResume() {
        super.onResume()
        if (isFirst){
            isFirst = false
            loadData()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isFirst = true
    }

    open fun loadData(){

    }


}