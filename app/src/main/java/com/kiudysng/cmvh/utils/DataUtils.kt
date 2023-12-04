package com.kiudysng.cmvh.utils

import java.text.SimpleDateFormat
import java.util.Date

object DataUtils {
    fun formattedDate(curTime: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")
        // 将时间戳转换为 Date 对象
        val date = Date(curTime)
        // 使用 SimpleDateFormat 对象格式化 Date 为字符串
        return sdf.format(date)
    }
}