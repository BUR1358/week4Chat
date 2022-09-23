package com.example.week4chat.data

data class ChatListItemModel(
    val id: Long,
    val senderName: String?,
    val messagePreview: String?,
    val messageSendingTime: String?,
    val unreadMessageCount: String?,
    val avatarImageResource: Int?,
    val personMessagesAndTime: ArrayList<ArrayList<String>>
)