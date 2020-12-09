package com.chencc.androidstudynotescode.binder.binder.common

import android.os.Binder
import android.os.IBinder
import android.os.Parcel
import android.util.Log
import com.chencc.androidstudynotescode.binder.binder.bean.Person

private const val DESCRIPTOR = "com.chencc.androidstudynotescode.binder.binder.common.IPersonManager"
const val TRANSACTION_addPerson = IBinder.FIRST_CALL_TRANSACTION
const val TRANSACTION_getPersonList = IBinder.FIRST_CALL_TRANSACTION + 1

private const val TAG = "Stub"
abstract class Stub : Binder(), IPersonManager {

    init {
        attachInterface(this, DESCRIPTOR)
    }

    companion object{
        fun asInterface(binder: IBinder?) : IPersonManager?{
            if (binder == null){
                return null
            }
            val iin = binder.queryLocalInterface(DESCRIPTOR)
            if (iin != null && iin is IPersonManager){
                return iin
            }
            return Proxy(binder)
        }
    }

    override fun asBinder(): IBinder = this

    override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
        when(code){
            INTERFACE_TRANSACTION -> {
                reply?.writeString(DESCRIPTOR)
                return true
            }
            TRANSACTION_addPerson -> {
                Log.e(TAG, "onTransact: TRANSACTION_addPerson  : ${Thread.currentThread()} " )
                data.enforceInterface(DESCRIPTOR)
                val arg0 = if (0 != data.readInt()) Person.CREATOR.createFromParcel(data) else null
                addPerson(arg0)
                reply?.writeNoException()
                return true
            }
            TRANSACTION_getPersonList -> {
                data.enforceInterface(DESCRIPTOR)
                val result = getPersonList()
                reply?.writeNoException()
                reply?.writeTypedList(result)
                return true
            }
        }
        return super.onTransact(code, data, reply, flags)
    }
}