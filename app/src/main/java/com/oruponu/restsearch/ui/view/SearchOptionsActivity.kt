package com.oruponu.restsearch.ui.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.oruponu.restsearch.R
import com.oruponu.restsearch.databinding.ActivitySearchOptionsBinding
import com.oruponu.restsearch.ui.viewmodel.SearchOptionsViewModel
import kotlinx.android.synthetic.main.activity_search_options.*

class SearchOptionsActivity : BaseActivity() {
    companion object {
        private const val SELECTED_CATEGORIES_EXTRA = "selected_categories"

        fun intent(context: Context, selectedCategories: HashMap<String, String>): Intent =
            Intent(context, SearchOptionsActivity::class.java).putExtra(
                SELECTED_CATEGORIES_EXTRA,
                selectedCategories
            )
    }

    private lateinit var binding: ActivitySearchOptionsBinding

    private val viewModel: SearchOptionsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_options)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_options)
        binding.also {
            it.viewModel = viewModel
            it.lifecycleOwner = this
        }

        @Suppress("UNCHECKED_CAST")
        viewModel.selectedCategories.value =
            intent.getSerializableExtra(SELECTED_CATEGORIES_EXTRA) as HashMap<String, String>
        viewModel.getCategory()

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
            intent.putExtra("selected_categories", viewModel.selectedCategories.value)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
