package com.ismail.creatvt.passguard

import android.os.Bundle
import android.widget.Button
import com.ismail.creatvt.passguard.manager.SecurityManager
import com.ismail.creatvt.passguard.manager.security.AuthenticationResultListener
import com.ismail.creatvt.passguard.manager.security.SecurityManager

class LockedActivity : SecureActivity(), AuthenticationResultListener {

    private var securityManager:SecurityManager?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locked)
        securityManager = SecurityManager(this)

        findViewById<Button>(R.id.unlockButton).setOnClickListener {
            securityManager?.showAuthenticationDialog(this)
        }
    }

    override fun onResume() {
        super.onResume()
        securityManager?.showAuthenticationDialog(this)
    }

    override fun onBackPressed() {

    }

    override fun onAuthenticationSuccess() {
        setResult(RESULT_OK)
        finish()
    }

    override fun onAuthenticationFailure() {
        securityManager?.showAuthenticationDialog(this)
    }
}