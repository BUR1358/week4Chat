package com.example.week4chat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.week4chat.R
import com.example.week4chat.data.MessageItemModel

class MessageAdapter(private val messageList: ArrayList<MessageItemModel>) :
    RecyclerView.Adapter<MessageAdapter.MessageListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageListViewHolder {
        return if (viewType == 0) {
            val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
            MessageListViewHolder(itemView)
        } else {
            val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.my_message_item, parent, false)
            MessageListViewHolder(itemView)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (messageList[position].MyMessage == false) {
            0 //сообщение не от меня
        } else {
            1 //мое сообщение
        }
    }

    override fun onBindViewHolder(holder: MessageListViewHolder, position: Int) {
        holder.initialize(messageList[position])
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    class MessageListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageText: TextView = itemView.findViewById(R.id.messageTextView)
        private val messageSendingTime: TextView = itemView.findViewById(R.id.messageSendingTimeTextView)


        fun initialize(item: MessageItemModel) {
            messageText.text = item.messageText
            messageSendingTime.text = item.messageSendingTime
        }
    }
}
