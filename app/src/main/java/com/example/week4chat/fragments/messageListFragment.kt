package com.example.week4chat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week4chat.Adapter.chatListAdapter
import com.example.week4chat.Adapter.messageAdapter
import com.example.week4chat.R
import com.example.week4chat.data.ChatListItemModel
import com.example.week4chat.data.MessageItemModel
import com.example.week4chat.databinding.FragmentMessageListBinding


class messageListFragment : Fragment() {
    lateinit var binding: FragmentMessageListBinding
    private lateinit var adapter: messageAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageArrayList: ArrayList<MessageItemModel>

    var senderName: String? = null
    var personAvatarID: Int? = null
    var personMessagesText: ArrayList<String>? = null
    var personMessagesTime: ArrayList<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessageListBinding.inflate(inflater, container, false)
        senderName = requireArguments().getString("senderName")
        personAvatarID = requireArguments().getInt("personAvatarID")
        personMessagesText = requireArguments().getStringArrayList("personMessagesText")
        personMessagesTime = requireArguments().getStringArrayList("personMessagesTime")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.senderNamePreview.text = senderName
        binding.avatarPreviewImage.setImageResource(personAvatarID!!)

        dataInitialize()
        recyclerView = view.findViewById(R.id.messageRecycleView)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        adapter = messageAdapter(messageArrayList)
        recyclerView.adapter = adapter
    }

    private fun dataInitialize() {
        val MyMessage = arrayListOf<Boolean>()
        messageArrayList = arrayListOf<MessageItemModel>()
        for (i in personMessagesText!!){
            MyMessage.add(false)
        }


        for (i in personMessagesText!!.indices) {
            val messages = MessageItemModel(
                personMessagesText!![i],
                personMessagesTime!![i],
                MyMessage[i]
            )
            messageArrayList.add(messages)
        }
    }
}