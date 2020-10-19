package com.chencc.androidstudynotescode.lazyfragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chencc.androidstudynotescode.R
import kotlinx.android.synthetic.main.fragment_my.*
import kotlinx.coroutines.*

private const val TAG = "MyFragment1"
class MyFragment1 : BaseFragment1(), CoroutineScope by MainScope() {

    var index = -1
    companion object {
        fun newInstance(index: Int): MyFragment1 {
            val args = Bundle()
            args.putInt("index" , index)
            val fragment = MyFragment1()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e(TAG, "index:  $index  onAttach:  " )
        index = arguments?.getInt("index") ?: -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_my , container , false)
        Log.e(TAG, "index: $index  onCreateView:   " )
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
    }
    override fun onResume() {
        super.onResume()
        Log.e(TAG, "index: $index  onResume:  " )
    }

    override fun onPause() {
        super.onPause()
        Log.e(TAG, "index: $index  onPause:  " )
    }

    override fun onDetach() {
        super.onDetach()
        Log.e(TAG, "index: $index  onDetach:  " )
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "index: $index  onDestroy:  " )
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.e(TAG, "index: $index     setUserVisibleHint  :  $isVisibleToUser  " )
    }



    fun getData(){
        launch {
            delay(3000)
            textView.text = "加载完成"
        }
    }
}