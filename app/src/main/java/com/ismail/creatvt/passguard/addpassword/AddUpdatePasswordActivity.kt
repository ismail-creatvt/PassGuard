package com.ismail.creatvt.passguard.addpassword

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ismail.creatvt.passguard.R
import com.ismail.creatvt.passguard.SecureActivity
import com.ismail.creatvt.passguard.databinding.ActivityAddUpdatePasswordBinding
import com.ismail.creatvt.passguard.model.Password
import com.ismail.creatvt.passguard.model.Password.Companion.PASSWORD

class AddUpdatePasswordActivity : SecureActivity(), ViewContract {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = setUpDataBinding()
        setExistingPasswordToViewModel(binding)
        setUpWebsiteSpinnerFieldAdapter(binding)
    }

    private fun setExistingPasswordToViewModel(binding: ActivityAddUpdatePasswordBinding) {
        val password = intent.getParcelableExtra<Password>(PASSWORD)
        binding.viewModel?.originalPassword = password
    }

    private fun setUpDataBinding() = DataBindingUtil.setContentView<ActivityAddUpdatePasswordBinding>(
            this,
            R.layout.activity_add_update_password
        ).also {
            it.lifecycleOwner = this
            it.owner = this
            it.viewModel = getViewModel()
            it.viewModel?.viewContract = this
        }

    private fun getViewModel() = ViewModelProvider(this)[AddUpdatePasswordViewModel::class.java]

    private fun setUpWebsiteSpinnerFieldAdapter(
        binding: ActivityAddUpdatePasswordBinding
    ) {
        val adapter = WebsiteArrayAdapter(this, binding.viewModel?.websiteList ?: listOf())
        binding.websiteSpinnerField.apply {
            setAdapter(adapter)
            onItemClickListener = adapter
            listSelection = 0
        }
    }

    override fun finishWithSuccess() {
        setResult(RESULT_OK)
        finish()
    }

}


