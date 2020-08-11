package com.oruponu.restsearch.ui.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.oruponu.restsearch.R
import com.oruponu.restsearch.data.model.categorylarge.CategoryLargeSearch
import com.oruponu.restsearch.databinding.ActivitySearchOptionsBinding
import com.oruponu.restsearch.databinding.CategorySearchOptionsBinding
import com.oruponu.restsearch.ui.viewmodel.SearchOptionsViewModel
import kotlinx.android.synthetic.main.activity_search_options.*

class SearchOptionsActivity : BaseActivity() {
    companion object {
        private const val SELECTED_CATEGORIES = "selected_categories"

        fun intent(context: Context, selectedCategories: HashMap<String, String>): Intent =
            Intent(context, SearchOptionsActivity::class.java).putExtra(
                SELECTED_CATEGORIES,
                selectedCategories
            )
    }

    private lateinit var binding: ActivitySearchOptionsBinding

    private val viewModel: SearchOptionsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_options)

        @Suppress("UNCHECKED_CAST")
        viewModel.selectedCategories =
            intent.getSerializableExtra(SELECTED_CATEGORIES) as HashMap<String, String>

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_options)

        viewModel.getCategory()
        viewModel.dataCategory.observe(this, Observer {
            setSearchCategory(it)
        })

        viewModel.stringId.observe(this, Observer {
            it.getContentIfNotHandled()?.let { stringId ->
                showSnackbar(
                    findViewById(android.R.id.content),
                    stringId,
                    android.R.string.ok,
                    View.OnClickListener { return@OnClickListener })
            }
        })

        okButton.setOnClickListener {
            val intent = Intent()
            intent.putExtra("selected_categories", viewModel.selectedCategories)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun setSearchCategory(categoryLargeSearch: CategoryLargeSearch) {
        flexboxLayout.removeAllViews()
        categoryLargeSearch.categoryL.forEach { categoryL ->
            val name = categoryL.categoryLName
            val code = categoryL.categoryLCode

            val categoryItem = DataBindingUtil.inflate<CategorySearchOptionsBinding>(
                LayoutInflater.from(this),
                R.layout.category_search_options,
                binding.flexboxLayout,
                true
            )

            categoryItem.chip.text = name
            if (viewModel.selectedCategories.containsKey(code)) {
                categoryItem.chip.setChipBackgroundColorResource(R.color.chipBackgroundChecked)
                categoryItem.chip.setTextColor(resources.getColor(R.color.chipTextChecked, null))
            } else {
                categoryItem.chip.setChipBackgroundColorResource(R.color.chipBackgroundDefault)
                categoryItem.chip.setTextColor(resources.getColor(R.color.chipTextDefault, null))
            }

            categoryItem.chip.setOnClickListener {
                if (viewModel.selectedCategories.containsKey(code)) {
                    categoryItem.chip.setChipBackgroundColorResource(R.color.chipBackgroundDefault)
                    categoryItem.chip.setTextColor(
                        resources.getColor(
                            R.color.chipTextDefault,
                            null
                        )
                    )
                    viewModel.selectedCategories.remove(code)
                } else {
                    if (viewModel.selectedCategories.size >= 10) {
                        showSnackbar(
                            it,
                            R.string.category_limit,
                            android.R.string.ok,
                            View.OnClickListener { return@OnClickListener })
                    } else {
                        categoryItem.chip.setChipBackgroundColorResource(R.color.chipBackgroundChecked)
                        categoryItem.chip.setTextColor(
                            resources.getColor(
                                R.color.chipTextChecked,
                                null
                            )
                        )
                        viewModel.selectedCategories[code] = name
                    }
                }
            }
        }
    }
}
