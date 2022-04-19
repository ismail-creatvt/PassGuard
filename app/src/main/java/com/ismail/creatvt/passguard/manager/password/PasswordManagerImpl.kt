package com.ismail.creatvt.passguard.manager.password

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties.*
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ismail.creatvt.passguard.getPasswordKey
import com.ismail.creatvt.passguard.model.Password

class PasswordManagerImpl internal constructor(val context: Context) : PasswordManager {

    private var keyGenParamSpec = KeyGenParameterSpec.Builder(
        "passguard_encryption",
        PURPOSE_ENCRYPT or PURPOSE_DECRYPT
    ).setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
        .setEncryptionPaddings(ENCRYPTION_PADDING_NONE)
        .setBlockModes(BLOCK_MODE_GCM).build()

    private var masterKey: MasterKey = MasterKey.Builder(context, "passguard_encryption")
        .setKeyGenParameterSpec(keyGenParamSpec)
        .build()

    private var prefs: SharedPreferences = EncryptedSharedPreferences.create(
        context.applicationContext,
        "encrypted_passwords",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun addPassword(password: Password) {
        val passwords = ArrayList(getAllPasswords())
        passwords.add(password)
        savePasswords(passwords)
    }

    override fun updatePassword(password: Password) {
        val passwords = getAllPasswords()
        passwords.forEach {
            if (it.id == password.id) {
                it.username = password.username
                it.password = password.password
                it.website = password.website
                it.extras = password.extras
            }
        }
        savePasswords(passwords)
    }

    private fun savePasswords(passwords: List<Password>) {
        clearPasswords()
        val partsCount = 1 + (passwords.size / 25)
        repeat(partsCount) {
            val from = it * 25
            val to = (from + 25).coerceAtMost(passwords.size)
            val part = passwords.subList(from, to)
            prefs.edit()
                .putString(getPasswordKey(it), Gson().toJson(part))
                .apply()
        }
    }

    private fun clearPasswords() {
        var index = 0
        val prefsEdit = prefs.edit()
        while(prefs.contains(getPasswordKey(index))){
            prefsEdit.remove(getPasswordKey(index))
            index++
        }
        prefsEdit.apply()
    }

    override fun deletePassword(password: Password) {
        val passwords = getAllPasswords()
        savePasswords(passwords.filter { it.id != password.id })
    }

    override fun getPassword(id: Long): Password? {
        return getAllPasswords().firstOrNull { it.id == id }
    }

    override fun getAllPasswords(): List<Password> {
        val passwords = arrayListOf<Password>()
        var index = 0
        while(prefs.contains(getPasswordKey(index))) {
            val passwordsJson = prefs.getString(getPasswordKey(index), null)?:break
            val type = object : TypeToken<List<Password>>() {}.type
            passwords.addAll(Gson().fromJson(passwordsJson, type))
            index++
        }
        return passwords
    }

    override fun close() {

    }
}