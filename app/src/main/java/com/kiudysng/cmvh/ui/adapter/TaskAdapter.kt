package com.kiudysng.cmvh.ui.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckBox
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.kiudysng.cmvh.R
import com.kiudysng.cmvh.db.entity.TaskEntity
import com.kiudysng.cmvh.utils.DataUtils

class TaskAdapter : BaseQuickAdapter<TaskEntity, QuickViewHolder>() {
    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: TaskEntity?) {
        val checkBox = holder.getView<AppCompatCheckBox>(R.id.ck_statue)
        val taskInfo = holder.getView<TextView>(R.id.tv_task_info)
        val taskCreateTime = holder.getView<TextView>(R.id.tv_task_create_time)
        item?.let {
            taskInfo.text = it.taskInfo
            taskCreateTime.text = DataUtils.formattedDate(it.createTime)
        }

    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder {
        return QuickViewHolder(R.layout.layout_item_task, parent)
    }
}