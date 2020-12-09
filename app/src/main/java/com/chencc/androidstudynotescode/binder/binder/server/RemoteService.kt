package com.chencc.androidstudynotescode.binder.binder.server

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.chencc.androidstudynotescode.binder.binder.bean.Person
import com.chencc.androidstudynotescode.binder.binder.common.Stub


class RemoteService : Service(){

    private var persons: MutableList<Person>? = null

    override fun onBind(intent: Intent?): IBinder? {
        persons = mutableListOf()
        return iBinder
    }


    private val iBinder = object : Stub(){
        override fun addPerson(person: Person?) {
            person?.let { persons?.add(it) }
        }

        override fun getPersonList(): MutableList<Person>? {
            return persons
        }
    }
}