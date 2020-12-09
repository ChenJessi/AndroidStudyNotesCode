package com.chencc.androidstudynotescode.binder.binder.common

import android.os.IBinder
import android.os.Parcel
import android.util.Log
import com.chencc.androidstudynotescode.binder.binder.bean.Person

private const val DESCRIPTOR = "com.chencc.androidstudynotescode.binder.binder.common.IPersonManager"
private const val TAG = "Proxy"
class Proxy(var mRemote : IBinder) : IPersonManager {

    override fun addPerson(person: Person?) {
        val data = Parcel.obtain()
        val reply = Parcel.obtain()
        try {
            data.writeInterfaceToken(DESCRIPTOR)
            if (person != null){
                data.writeInt(1)
                person.writeToParcel(data, 0)
            }else{
                data.writeInt(0)
            }
            Log.e(TAG, "addPerson:  ${Thread.currentThread()}" )
            mRemote.transact(TRANSACTION_addPerson, data, reply, 0)
            reply.readException()
        } finally {
            reply.recycle()
            data.recycle()
        }
    }

    override fun getPersonList(): MutableList<Person>? {
        val data = Parcel.obtain()
        val reply = Parcel.obtain()
        var result : MutableList<Person>? = null

        try {
            data.writeInterfaceToken(DESCRIPTOR)
            mRemote.transact(TRANSACTION_getPersonList, data, reply, 0)
            reply.readException()
            result = reply.createTypedArrayList(Person.CREATOR)
        } finally {
            reply.recycle()
            data.recycle()
        }
        return  result
    }

    override fun asBinder(): IBinder  = mRemote
}