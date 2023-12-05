package com.kiudysng.cmvh.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.kiudysng.cmvh.R
import com.kiudysng.cmvh.TaskApplication
import com.kiudysng.cmvh.db.entity.TaskEntity
import com.kiudysng.cmvh.utils.DataUtils
import androidx.recyclerview.widget.RecyclerView
import com.kiudysng.cmvh.ui.BaseViewModel
import com.kiudysng.cmvh.utils.RxBus


class TaskHistoryAdapter(val vm: BaseViewModel ) : BaseQuickAdapter<TaskEntity, QuickViewHolder>() {

    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: TaskEntity?) {
        val checkBox = holder.getView<TextView>(R.id.ck_statue)
        val taskInfo = holder.getView<TextView>(R.id.tv_task_info)
        val taskCreateTime = holder.getView<TextView>(R.id.tv_task_create_time)
        item?.let { it ->
            taskInfo.text = it.taskInfo
            "create time:${DataUtils.formattedDate(it.createTime)}".also {
                taskCreateTime.text = it
            }
            if (it.confirm == 1) {
                checkBox.setBackgroundResource(R.mipmap.icon_sel)
                taskInfo.setTextColor(TaskApplication.appContext.getColor(R.color.black_hint))
                taskInfo.paintFlags = taskInfo.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                taskCreateTime.setTextColor(TaskApplication.appContext.getColor(R.color.black_hint))
                taskCreateTime.paintFlags = taskCreateTime.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                checkBox.setTextColor(TaskApplication.appContext.getColor(R.color.black_hint))
                checkBox.paintFlags = checkBox.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            } else {
                checkBox.setBackgroundResource(R.mipmap.icon_no_sel)
                taskInfo.setTextColor(TaskApplication.appContext.getColor(R.color.brand_primary))
                taskInfo.paintFlags = taskInfo.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                taskCreateTime.setTextColor(TaskApplication.appContext.getColor(R.color.brand_primary))
                taskCreateTime.paintFlags =
                    taskCreateTime.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                checkBox.setTextColor(TaskApplication.appContext.getColor(R.color.brand_primary))
                checkBox.paintFlags = checkBox.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
            checkBox.setOnClickListener {_->
                Log.e("bLog","setOnCheckedChangeListener  ---- check=  ")
                if (recyclerView.scrollState == RecyclerView.SCROLL_STATE_IDLE && !recyclerView.isComputingLayout) {
                    notifyItemRemoved(position)
                }
                RxBus.post("Confirm")
                vm.updateItemStatue(if (it.confirm == 1) 0 else 1, it.createTime)
            }
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