package com.ismail.creatvt.passguard

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.*
import com.ismail.creatvt.passguard.manager.PasswordManager
import com.ismail.creatvt.passguard.manager.password.PasswordManager

class PassGuardApplication: Application(), LifecycleEventObserver {

    lateinit var passwordManager: PasswordManager

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        passwordManager = PasswordManager(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if(event == Lifecycle.Event.ON_DESTROY) {
            passwordManager.close()
        }
    }


}