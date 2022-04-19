package com.ismail.creatvt.passguard.manager.password

import com.ismail.creatvt.passguard.model.Password

interface PasswordManager {

    fun addPassword(password: Password)

    fun updatePassword(password: Password)

    fun deletePassword(password: Password)

    fun getPassword(id: Long): Password?

    fun getAllPasswords():List<Password>

    fun close()

}