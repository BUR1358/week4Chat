package com.example.week4chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.week4chat.databinding.ActivityMainBinding
import com.example.week4chat.fragments.chatListFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        replaceFragment(chatListFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.add(binding.mainFrameContainer.id, fragment)
        fragmentManager.commit()
    }

}
