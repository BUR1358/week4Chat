package com.example.week4chat.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.week4chat.R
import com.example.week4chat.data.ChatListItemModel

class chatListAdapter(
    private val chatList: ArrayList<ChatListItemModel>,
    var clickListener: OnChatItemClickListener
) :
    RecyclerView.Adapter<chatListAdapter.ChatListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.chat_item, parent, false)
        return ChatListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        holder.initialize(chatList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    class ChatListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val senderName: TextView = itemView.findViewById(R.id.senderName)
        val messagePreview: TextView = itemView.findViewById(R.id.messagePreview)
        val messageSendingTime: TextView = itemView.findViewById(R.id.messageSendingTime)
        val unreadMessageCount: TextView = itemView.findViewById(R.id.unreadMessageCount)
        val avatarImageResource: ImageView = itemView.findViewById(R.id.avatarImage)

        fun initialize(item: ChatListItemModel, action: OnChatItemClickListener) {
            senderName.text = item.senderName
            messagePreview.text = item.messagePreview
            messageSendingTime.text = item.messageSendingTime
            unreadMessageCount.text = item.unreadMessageCount
            avatarImageResource.setImageResource(item.avatarImageResource!!)

            itemView.setOnClickListener { action.OnItemClick(item, adapterPosition) }
        }
    }
}

interface OnChatItemClickListener {
    fun OnItemClick(item: ChatListItemModel, position: Int)
}