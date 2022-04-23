package com.ismail.creatvt.passguard.passwordlist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ismail.creatvt.passguard.LockedActivity
import com.ismail.creatvt.passguard.PassGuardApplication
import com.ismail.creatvt.passguard.R
import com.ismail.creatvt.passguard.SecureActivity
import com.ismail.creatvt.passguard.addpassword.AddUpdatePasswordActivity
import com.ismail.creatvt.passguard.model.Password
import com.ismail.creatvt.passguard.model.Password.Companion.PASSWORD
import com.ismail.creatvt.passguard.viewpassword.ViewPasswordActivity

class PasswordListActivity : SecureActivity() {

    private var adapter: PasswordsAdapter? = null
    private var lockActivityLauncher: ActivityResultLauncher<Intent>? = null
    private var passwordActivityLauncher: ActivityResultLauncher<Intent>? = null
    private var isFirstLaunch: Boolean = false
    private var isPaused: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isFirstLaunch = true
        isPaused = false
        setContentView(R.layout.activity_main)

        setUpPasswordsListRecyclerView()

        lockActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    isPaused = false
                }
            }

        passwordActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    if (it.data?.action == Intent.ACTION_EDIT) {
                        showEditActivity(it?.data)
                    } else if (it.data?.action == Intent.ACTION_VIEW) {
                        isPaused = false
                    } else {
                        adapter?.setPasswordsList(getPasswords())
                    }
                }
            }
    }

    private fun showEditActivity(data: Intent?) {
        val password = data?.getParcelableExtra<Password>(PASSWORD) ?: return
        passwordActivityLauncher?.launch(
            Intent(
                this,
                AddUpdatePasswordActivity::class.java
            ).apply {
                putExtra(PASSWORD, password)
            })
    }

    private fun setUpPasswordsListRecyclerView() {
        val passwordListRecyclerView = findViewById<RecyclerView>(R.id.passwordsList)
        adapter = PasswordsAdapter(getPasswords(), this::onPasswordItemClick)
        passwordListRecyclerView.adapter = adapter
        passwordListRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun onPasswordItemClick(password: Password) {
        passwordActivityLauncher?.launch(Intent(this, ViewPasswordActivity::class.java).apply {
            putExtra(PASSWORD, password)
        })
    }

    override fun onResume() {
        super.onResume()
        if (isFirstLaunch || isPaused) {
            isFirstLaunch = false
            lockActivityLauncher?.launch(Intent(this, LockedActivity::class.java))
        }
        isPaused = false
    }

    override fun onRestart() {
        super.onRestart()
        isFirstLaunch = false
    }

    override fun onPause() {
        super.onPause()
        isPaused = true
    }

    override fun onStop() {
        super.onStop()
    }

    fun onPasswordButtonClick(view: View) {
        passwordActivityLauncher?.launch(Intent(this, AddUpdatePasswordActivity::class.java))
    }

    private fun getPasswords(): List<Password> {
        return (application as PassGuardApplication).passwordManager.getAllPasswords()
    }
}