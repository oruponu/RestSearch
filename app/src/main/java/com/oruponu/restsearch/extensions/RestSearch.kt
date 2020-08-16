package com.oruponu.restsearch.extensions

import com.oruponu.restsearch.data.model.rest.RestSearch
import kotlin.math.ceil

fun RestSearch.countTotalPage(): Int {
    if (this.totalHitCount > 1000) {
        return ceil(1000 / this.hitPerPage.toDouble()).toInt()
    }
    return ceil(this.totalHitCount / this.hitPerPage.toDouble()).toInt()
}
