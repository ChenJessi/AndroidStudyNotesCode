package com.chencc.androidstudynotescode.lazyfragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.lazyfragment.base1.BaseFragment1
import com.chencc.androidstudynotescode.lazyfragment.base1.BaseFragment2
import com.chencc.androidstudynotescode.lazyfragment.base1.BaseFragment3
import com.chencc.androidstudynotescode.lazyfragment.base1.BaseFragment5
import com.chencc.androidstudynotescode.lazyfragment.base2.BaseFragment
import kotlinx.android.synthetic.main.fragment_my.*
import kotlinx.coroutines.*

private const val TAG = "MyFragment2"
class MyFragment2 : BaseFragment(), CoroutineScope by MainScope() {

    var index = -1
    companion object {
        fun newInstance(index: Int): MyFragment2 {
            val args = Bundle()
            args.putInt("index" , index)
            val fragment = MyFragment2()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my, container , false)
    }

    override fun loadData() {
        super.loadData()
        Log.e(TAG, "index:  $index  loadData:  " )
        getData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        index = arguments?.getInt("index") ?: -1
        Log.e(TAG, "index: $index  onCreate:  " )
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
//        Log.e(TAG, "index:  $index  onAttach:  " )
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
//        Log.e(TAG, "index: $index  onDetach:  " )
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "index: $index  onDestroy:  " )
    }



    private fun getData(){
        launch {
            delay(2000)
            textView?.text = "加载完成"
        }
    }
}