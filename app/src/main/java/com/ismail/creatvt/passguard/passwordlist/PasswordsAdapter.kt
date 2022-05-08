package com.ismail.creatvt.passguard.passwordlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ismail.creatvt.passguard.R
import com.ismail.creatvt.passguard.model.Password
import com.ismail.creatvt.passguard.model.WebsiteFactory

class PasswordsAdapter(
    var passwords: List<Password>,
    var onPasswordClick:(Password)->Unit
) : RecyclerView.Adapter<PasswordsAdapter.PasswordsViewHolder>() {

    fun setPasswordsList(passwordsList: List<Password>) {
        val oldList = passwords
        passwords = passwordsList
        DiffUtil.calculateDiff(
            PasswordsDiffCallback(
                oldList, passwordsList
            )
        ).dispatchUpdatesTo(this)
    }

    class PasswordsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordsViewHolder {
        return PasswordsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.password_list_item,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: PasswordsViewHolder, position: Int) {
        holder.itemView.apply {
            setOnClickListener {
                onPasswordClick(passwords[holder.adapterPosition])
            }
            findViewById<ImageView>(R.id.websiteIcon).setImageResource(
                WebsiteFactory.getIcon(passwords[position].website.icon)
            )
            findViewById<TextView>(R.id.websiteText).apply {
                text = passwords[position].website.websiteName
                isSelected = true
            }
            findViewById<TextView>(R.id.usernameText).apply {
                text = passwords[position].username
                isSelected = true
            }
        }
    }

    override fun getItemCount() = passwords.size
}