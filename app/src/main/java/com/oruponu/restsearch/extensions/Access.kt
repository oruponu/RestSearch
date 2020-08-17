package com.oruponu.restsearch.extensions

import com.oruponu.restsearch.data.model.rest.Access

fun Access.getAccess(): String = if (this.station.isNotEmpty()) {
    this.station + this.stationExit + " " + this.walk + "分"
} else {
    ""
}
