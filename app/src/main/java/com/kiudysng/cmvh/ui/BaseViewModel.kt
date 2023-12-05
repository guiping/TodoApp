package com.kiudysng.cmvh.ui

import androidx.lifecycle.ViewModel

abstract class BaseViewModel:ViewModel() {
    abstract fun updateItemStatue(confirm:Int,createTime:Long)
}