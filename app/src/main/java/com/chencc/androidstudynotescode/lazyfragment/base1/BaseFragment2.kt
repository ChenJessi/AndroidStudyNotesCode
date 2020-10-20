package com.chencc.androidstudynotescode.lazyfragment.base1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


private const val TAG = "BaseFragment2"
abstract class BaseFragment2 : Fragment(){


    var rootView : View? = null
    var isViewCreated = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.e(TAG, "onCreateView: " )
        if (rootView == null){
            rootView = inflater.inflate(getLayoutId(), container , false)
        }
        isViewCreated = true
        initView(rootView)
        /**
         * �ֶ��ַ�һ��
         * �����һ�ν����׸�fragment û���� dispatchUserVisibleHint ������
         */
        if (userVisibleHint){
            userVisibleHint = true
        }
        return rootView
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.e(TAG, "setUserVisibleHint:    isVisibleToUser  $isVisibleToUser   isViewCreated  $isViewCreated" )
        /**
         *  setUserVisibleHint ִ���� onCreateView ֮ǰ
         *  �����״ν����һ��ҳ�� isViewCreated == false
         */
        if (isViewCreated){
            if (isVisibleToUser){
                dispatchUserVisibleHint(true)
            }else{
                dispatchUserVisibleHint(false)
            }
        }
    }



    private fun dispatchUserVisibleHint(visible : Boolean){
        if (visible){
            load()
        }else{
            loadStop()
        }
    }

    open fun load(){

    }

    open fun loadStop(){

    }


    override fun onResume() {
        super.onResume()
        Log.e(TAG, "onResume: " )
    }

    override fun onPause() {
        super.onPause()
        Log.e(TAG, "onPause: " )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e(TAG, "onDestroyView: " )
    }





    protected abstract fun getLayoutId() : Int
    protected abstract fun initView(rootView : View?)

}