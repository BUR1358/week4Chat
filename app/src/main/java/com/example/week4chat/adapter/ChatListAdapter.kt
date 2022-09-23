package com.example.week4chat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.week4chat.R
import com.example.week4chat.data.ChatDiffCallback
import com.example.week4chat.data.ChatListItemModel


class ChatListAdapter(
    private val chatList: ArrayList<ChatListItemModel>,
    private var clickListener: OnChatItemClickListener
) :
    RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder>() {


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

    fun addChatList(
        newChatList: ArrayList<ChatListItemModel>
    ) {
        val chatDiffCallback = ChatDiffCallback(chatList, newChatList)
        val diffResult = DiffUtil.calculateDiff(chatDiffCallback)
        chatList.addAll(newChatList)
        diffResult.dispatchUpdatesTo(this)
    }

    class ChatListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val senderName: TextView = itemView.findViewById(R.id.senderName)
        private val messagePreview: TextView = itemView.findViewById(R.id.messagePreview)
        private val messageSendingTime: TextView = itemView.findViewById(R.id.messageSendingTime)
        private val unreadMessageCount: TextView = itemView.findViewById(R.id.unreadMessageCount)
        private val avatarImageResource: ImageView = itemView.findViewById(R.id.avatarImage)

        fun initialize(item: ChatListItemModel, action: OnChatItemClickListener) {
            senderName.text = item.senderName
            messagePreview.text = item.messagePreview
            messageSendingTime.text = item.messageSendingTime
            unreadMessageCount.text = item.unreadMessageCount
            avatarImageResource.setImageResource(item.avatarImageResource!!)
            itemView.setOnClickListener { action.onItemClick(item, adapterPosition) }
        }
    }
}

interface OnChatItemClickListener {
    fun onItemClick(item: ChatListItemModel, position: Int)
}