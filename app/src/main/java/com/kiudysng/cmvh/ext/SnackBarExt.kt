package com.kiudysng.cmvh.ext

import android.app.Activity
import android.view.View
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.kiudysng.cmvh.R

fun Activity.makeLongSnackBar(message: String,binding:ViewBinding): Snackbar {
    return Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
}