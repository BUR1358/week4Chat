package com.example.week4chat.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.week4chat.R
import com.example.week4chat.data.ChatListItemModel

class ChatListAdapter(
    private val chatList: ArrayList<ChatListItemModel>,
    var clickListener: OnChatItemClickListener
) :
    RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.chat_item, parent, false)
        return ChatListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        holder.initialize(chatList.get(position), clickListener)
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    class ChatListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val senderName: TextView = itemView.findViewById(R.id.senderName)
        val messagePreview: TextView = itemView.findViewById(R.id.messagePreview)
        val messageSendingTime: TextView = itemView.findViewById(R.id.messageSendingTime)
        val unreadMessageCount: TextView = itemView.findViewById(R.id.unreadMessageCount)

        fun initialize(item: ChatListItemModel, action: OnChatItemClickListener) {
            senderName.text = item.senderName
            messagePreview.text = item.messagePreview
            messageSendingTime.text = item.messageSendingTime
            unreadMessageCount.text = item.unreadMessageCount
            itemView.setOnClickListener { action.OnItemClick(item, adapterPosition) }
        }
    }
}

interface OnChatItemClickListener {
    fun OnItemClick(item: ChatListItemModel, position: Int)
}