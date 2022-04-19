package com.ismail.creatvt.passguard.passwordlist

import androidx.recyclerview.widget.DiffUtil
import com.ismail.creatvt.passguard.model.Password

class PasswordsDiffCallback(
    private val oldList: List<Password>,
    private val newList: List<Password>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].username == newList[newItemPosition].username &&
                oldList[oldItemPosition].website == newList[newItemPosition].website
    }
}