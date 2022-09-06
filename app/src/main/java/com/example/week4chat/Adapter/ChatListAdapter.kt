package com.example.week4chat.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.week4chat.R
import com.example.week4chat.data.ChatListItemModel

class ChatListAdapter(private val chatList: ArrayList<ChatListItemModel>) :
    RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.chat_item, parent, false)
        return ChatListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        val currentItem = chatList[position]
        holder.senderName.text = currentItem.senderName
        holder.messagePreview.text = currentItem.messagePreview
        holder.messageSendingTime.text = currentItem.messageSendingTime
        holder.unreadMessageCount.text = currentItem.unreadMessageCount
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    class ChatListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val senderName: TextView = itemView.findViewById(R.id.senderName)
        val messagePreview: TextView = itemView.findViewById(R.id.messagePreview)
        val messageSendingTime: TextView = itemView.findViewById(R.id.messageSendingTime)
        val unreadMessageCount: TextView = itemView.findViewById(R.id.unreadMessageCount)
    }
}