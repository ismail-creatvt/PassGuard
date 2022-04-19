package com.ismail.creatvt.passguard.viewpassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ismail.creatvt.passguard.R
import com.ismail.creatvt.passguard.databinding.ActivityViewPasswordBinding
import com.ismail.creatvt.passguard.manager.SecurityManager
import com.ismail.creatvt.passguard.manager.security.SecurityManager
import com.ismail.creatvt.passguard.model.Password
import com.ismail.creatvt.passguard.model.Password.Companion.PASSWORD

class ViewPasswordActivity : AppCompatActivity(), ViewPasswordViewModel.ViewPasswordView {

    private var binding: ActivityViewPasswordBinding? = null
    private var securityManager: SecurityManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_view_password
        )

        val password = intent?.getParcelableExtra<Password>(PASSWORD)

        val viewModel = ViewModelProvider(this)[ViewPasswordViewModel::class.java]
        viewModel.password = password
        viewModel.viewContract = this

        securityManager = SecurityManager(viewModel)

        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel
        binding?.owner = this
    }

    override fun onBackPressed() {
        setResult(RESULT_OK, Intent(Intent.ACTION_VIEW))
        finish()
    }

    override fun requestAuthentication() {
        securityManager?.showAuthenticationDialog(this)
    }

    override fun showDeleteConfirmationDialog(onDeleteConfirm: () -> Unit) {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.delete_password)
            .setMessage(R.string.delete_confirmation_message)
            .setPositiveButton(R.string.yes){dialog, action ->
                onDeleteConfirm()
            }.setNegativeButton(R.string.no) {_,_->}
            .show()
    }

    override fun showEditPasswordActivity() {
        val password = intent.getParcelableExtra<Password>(PASSWORD)
        if(password == null) {
            finish()
            return
        }
        setResult(RESULT_OK, Intent(Intent.ACTION_EDIT).apply {
            putExtra(PASSWORD, password)
        })
        finish()
    }

    override fun finishWithSuccess() {
        setResult(RESULT_OK)
        finish()
    }

    override fun onPause() {
        super.onPause()
        binding?.viewModel?.hideConfidentialData()
    }
}