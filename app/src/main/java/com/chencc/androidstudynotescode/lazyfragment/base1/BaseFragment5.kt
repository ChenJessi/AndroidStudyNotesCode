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
    private var isFragmentVisible = false    // �Ƿ�ɼ���״̬

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
            /**
             *  ��ʾ״̬�����ı�Żص�
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
             * ��ǰfragment �ɼ�  ��fragment���ɼ�
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
     * ���fragmentǶ������¿ɼ�����
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
     * �жϸ�fragment �Ƿ񲻿ɼ�
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
        //���ɼ� �� �ɼ� �仯����  ˵���ɼ�
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