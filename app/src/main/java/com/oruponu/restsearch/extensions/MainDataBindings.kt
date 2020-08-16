package com.oruponu.restsearch.extensions

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.google.android.flexbox.FlexboxLayout
import com.oruponu.restsearch.R
import com.oruponu.restsearch.databinding.CategoryMainBinding

@BindingAdapter("category", "textView")
fun setCategoryChip(
    flexboxLayout: FlexboxLayout,
    selectedCategories: HashMap<String, String>?,
    textView: TextView
) {
    if (selectedCategories == null) {
        return
    }
    flexboxLayout.removeAllViews()
    textView.visibility = View.GONE
    selectedCategories.forEach { category ->
        val categoryItem = DataBindingUtil.inflate<CategoryMainBinding>(
            LayoutInflater.from(flexboxLayout.context),
            R.layout.category_main,
            flexboxLayout,
            true
        )
        categoryItem.chip.text = category.value
        categoryItem.chip.setOnCloseIconClickListener {
            selectedCategories.remove(category.key)
            categoryItem.chip.visibility = View.GONE
            if (selectedCategories.isEmpty()) {
                textView.visibility = View.VISIBLE
            }
        }
    }
}
