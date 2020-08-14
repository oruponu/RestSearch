package com.oruponu.restsearch.ui.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.oruponu.restsearch.R
import com.oruponu.restsearch.data.model.rest.Rest
import com.oruponu.restsearch.databinding.ActivityDetailBinding

class DetailActivity : BaseActivity() {
    companion object {
        private const val REST_EXTRA = "rest"

        fun intent(context: Context, rest: Rest): Intent =
            Intent(context, DetailActivity::class.java).putExtra(REST_EXTRA, rest)
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        val rest = intent.getSerializableExtra(REST_EXTRA) as Rest
        binding.rest = rest
    }
}
