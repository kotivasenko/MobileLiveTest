package com.ivasenko.mobilelivetest.utils

import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

fun Fragment.hasPermission(vararg permissions: String): Boolean {
    return permissions.all { singlePermission ->
        this.requireActivity().checkSelfPermission(singlePermission) == PackageManager.PERMISSION_GRANTED
    }
}

fun Fragment.askPermission(vararg permissions: String, requestCode: Int) =
    ActivityCompat.requestPermissions(this.requireActivity(), permissions, requestCode)