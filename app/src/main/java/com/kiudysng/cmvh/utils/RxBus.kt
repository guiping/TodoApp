package com.kiudysng.cmvh.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

object RxBus {
    private val msgLiveData = MutableLiveData<String>()
    fun post(msg: String) {
        msgLiveData.postValue(msg)
    }

    fun observerEvent(owner: LifecycleOwner, observer: Observer<String>) {
        msgLiveData.observe(owner, observer)
    }

}