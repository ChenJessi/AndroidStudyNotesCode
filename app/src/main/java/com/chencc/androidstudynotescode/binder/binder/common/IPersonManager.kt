package com.chencc.androidstudynotescode.binder.binder.common

import android.os.IInterface
import android.os.RemoteException
import com.chencc.androidstudynotescode.binder.binder.bean.Person

/**
 * @author Created by CHEN on 2020/12/8
 * @email 188669@163.com
 */
interface IPersonManager : IInterface {
    @Throws(RemoteException::class)
    fun addPerson(person: Person?)
    @Throws(RemoteException::class)
    fun getPersonList() : MutableList<Person>?
}