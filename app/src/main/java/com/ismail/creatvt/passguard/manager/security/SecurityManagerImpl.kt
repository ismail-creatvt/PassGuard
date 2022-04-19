package com.ismail.creatvt.passguard.manager.security

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

class SecurityManagerImpl(val listener: AuthenticationResultListener):SecurityManager, BiometricPrompt.AuthenticationCallback() {

    override fun showAuthenticationDialog(activity: FragmentActivity) {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Master Password")
            .setDescription("Enter master password to view your passwords list")
            .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
            .build()
        val biometricManager = BiometricManager.from(activity)
        when(biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                showBiometricPrompt(activity, promptInfo)
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {

            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {

            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {

            }
            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {

            }
            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {

            }
            BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {

            }
        }
    }

    private fun showBiometricPrompt(activity: FragmentActivity, promptInfo: BiometricPrompt.PromptInfo) {
        BiometricPrompt(
            activity,
            ContextCompat.getMainExecutor(activity),
            this
        ).authenticate(promptInfo)
    }

    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
        super.onAuthenticationSucceeded(result)
        listener.onAuthenticationSuccess()
    }

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
        super.onAuthenticationError(errorCode, errString)
        listener.onAuthenticationFailure()
    }

    override fun onAuthenticationFailed() {
        super.onAuthenticationFailed()
        listener.onAuthenticationFailure()
    }

}