package com.oruponu.restsearch.extensions

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.SphericalUtil

fun LatLng.toBounds(radius: Double): LatLngBounds =
    LatLngBounds.builder().include(SphericalUtil.computeOffset(this, radius, 0.0))
        .include(SphericalUtil.computeOffset(this, radius, 90.0))
        .include(SphericalUtil.computeOffset(this, radius, 180.0))
        .include(SphericalUtil.computeOffset(this, radius, 270.0)).build()
