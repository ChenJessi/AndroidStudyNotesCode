package com.chencc.androidstudynotescode.lazyfragment.base1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


private const val TAG = "BaseFragment5"
abstract class BaseFragment5 : Fragment(){


    private var rootView : View? = null
    private var isViewCreated = false
    private var isFragmentVisible = false    // 是否可见的状态

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.e(TAG, "onCreateView: " )
        if (rootView == null){
            rootView = inflater.inflate(getLayoutId(), container , false)
        }
        isViewCreated = true
        initView(rootView)
        /**
         * 手动分发一次
         * 解决第一次进入首个fragment 没调用 dispatchUserVisibleHint 的问题
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
         *  setUserVisibleHint 执行在 onCreateView 之前
         *  所以首次进入第一个页面 isViewCreated == false
         */
        if (isViewCreated){
            /**
             *  显示状态发生改变才回调
             */
            if (isVisibleToUser && !isFragmentVisible){
                dispatchUserVisibleHint(true)
            }else if (!isVisibleToUser && isFragmentVisible){
                dispatchUserVisibleHint(false)
            }
        }
    }



    private fun dispatchUserVisibleHint(visible : Boolean){
        isFragmentVisible = visible


        if (visible && isParentInvisible()){
            /**
             * 当前fragment 可见  父fragment不可见
             */
            return
        }

        if (visible){
            load()
            dispatchChildVisible(true)
        }else{
            loadStop()
            dispatchChildVisible(false)
        }
    }

    /**
     * 解决fragment嵌套情况下可见问题
     */
    private fun dispatchChildVisible(visible: Boolean){
        val manager = childFragmentManager
        val fragments = manager.fragments
        fragments?.forEach {
            if (it is BaseFragment5
                && it.isHidden
                && it.userVisibleHint){
                it.dispatchUserVisibleHint(true)
            }
        }
    }


    /**
     * 判断父fragment 是否不可见
     */
    private fun isParentInvisible() : Boolean{
        return if (parentFragment is BaseFragment5){
             !(parentFragment as BaseFragment5).isFragmentVisible
        }else{
            false
        }

    }



    open fun load(){

    }

    open fun loadStop(){

    }


    override fun onResume() {
        super.onResume()
        Log.e(TAG, "onResume: " )
        //不可见 到 可见 变化过程  说明可见
        if (userVisibleHint && !isFragmentVisible) {
            dispatchUserVisibleHint(true);
        }
    }

    override fun onPause() {
        super.onPause()
        Log.e(TAG, "onPause: " )
        if (!userVisibleHint && isFragmentVisible) {
            dispatchUserVisibleHint(false);
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e(TAG, "onDestroyView: " )
    }





    protected abstract fun getLayoutId() : Int
    protected abstract fun initView(rootView : View?)

}