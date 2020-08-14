package com.oruponu.restsearch.extensions

import com.oruponu.restsearch.data.model.rest.Access

fun Access.getAccess(): String = "$station$stationExit $walk" + "分"
