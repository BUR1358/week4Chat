package com.example.week4chat.data

import androidx.recyclerview.widget.DiffUtil

class ChatDiffCallback(
    private val oldList: List<ChatListItemModel>,
    private val newList: List<ChatListItemModel>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldChat = oldList[oldItemPosition]
        val newChat = newList[newItemPosition]
        return oldChat.id == newChat.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldChat = oldList[oldItemPosition]
        val newChat = newList[oldItemPosition]
        return oldChat == newChat
    }

}