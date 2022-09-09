package com.example.week4chat.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.week4chat.R
import com.example.week4chat.data.MessageItemModel

class messageAdapter(private val messageList: ArrayList<MessageItemModel>) :
    RecyclerView.Adapter<messageAdapter.messageListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): messageListViewHolder {
        val itemView: RecyclerView.ViewHolder? = null
        if (viewType == 0) {
            val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
            return messageListViewHolder(itemView)
        } else {
            val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.my_message_item, parent, false)
            return messageListViewHolder(itemView)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (messageList[position].MyMessage == false) {
            return 0 //сообщение не от меня
        } else {
            return 1 //мое сообщение
        }
    }

    override fun onBindViewHolder(holder: messageListViewHolder, position: Int) {
        holder.initialize(messageList[position])
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    class messageListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageText: TextView = itemView.findViewById(R.id.messageTextView)
        val messageSendingTime: TextView = itemView.findViewById(R.id.messageSendingTimeTextView)


        fun initialize(item: MessageItemModel) {
            messageText.text = item.messageText
            messageSendingTime.text = item.messageSendingTime
        }
    }
}
