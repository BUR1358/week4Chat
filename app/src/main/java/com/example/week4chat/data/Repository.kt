package com.example.week4chat.data

import android.os.Build
import androidx.annotation.RequiresApi
import io.github.serpro69.kfaker.Faker
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList

@RequiresApi(Build.VERSION_CODES.O)
class Repository {
    private var numberOfNamesInList = 9
    private var uniqueID = 0
    private lateinit var nameList: ArrayList<String>
    private lateinit var timeMessageNumberList: ArrayList<ArrayList<String>>
    private val faker = Faker()
    private var datetime: LocalDateTime = LocalDateTime.now()

    fun getNameList(): ArrayList<String> {
        nameList = arrayListOf()
        for (i in 0..numberOfNamesInList) {
            nameList.add(faker.name.firstName() + " " + faker.name.lastName())
        }
        return nameList
    }

    fun getTimeAndMessageList(): ArrayList<ArrayList<String>> {
        timeMessageNumberList = arrayListOf()
        val timeList = arrayListOf<String>()
        val messageList = arrayListOf<String>()
        val numberOfMessages: Int = (3..25).random()

        for (i in 0..numberOfMessages) {
            timeList.add(0, datetime.format(DateTimeFormatter.ofPattern("hh:mm")))
            datetime -= Duration.ofMinutes((0..59).random().toLong())
            messageList.add(faker.bigBangTheory.quotes())
        }
        timeMessageNumberList.add(timeList)
        timeMessageNumberList.add(messageList)
        return timeMessageNumberList
    }

    fun getUniqueId(): Long {
        uniqueID += 1
        return uniqueID.toLong()
    }
}