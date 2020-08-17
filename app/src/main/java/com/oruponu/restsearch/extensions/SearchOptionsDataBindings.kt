package com.oruponu.restsearch.extensions

import android.view.LayoutInflater
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.google.android.flexbox.FlexboxLayout
import com.oruponu.restsearch.R
import com.oruponu.restsearch.data.model.categorylarge.CategoryLargeSearch
import com.oruponu.restsearch.databinding.CategorySearchOptionsBinding
import com.oruponu.restsearch.ui.view.BaseActivity

@BindingAdapter("category", "selectedCategory")
fun setCategoryChip(
    flexboxLayout: FlexboxLayout,
    categoryLargeSearch: CategoryLargeSearch?,
    selectedCategories: HashMap<String, String>?
) {
    categoryLargeSearch ?: return
    selectedCategories ?: return
    flexboxLayout.removeAllViews()
    categoryLargeSearch.categoryL.forEach { categoryL ->
        val name = categoryL.categoryLName
        val code = categoryL.categoryLCode

        val categoryItem = DataBindingUtil.inflate<CategorySearchOptionsBinding>(
            LayoutInflater.from(flexboxLayout.context),
            R.layout.category_search_options,
            flexboxLayout,
            true
        )

        categoryItem.chip.text = name
        if (selectedCategories.containsKey(code)) {
            categoryItem.chip.isChecked = true
        }

        categoryItem.chip.setOnClickListener {
            if (selectedCategories.containsKey(code)) {
                selectedCategories.remove(code)
            } else {
                if (selectedCategories.size >= 10) {
                    categoryItem.chip.isChecked = false
                    BaseActivity.showSnackbar(
                        it,
                        R.string.category_limit,
                        android.R.string.ok,
                        View.OnClickListener { return@OnClickListener })
                } else {
                    selectedCategories[code] = name
                }
            }
        }
    }
}
