package com.edhou.taskmaster.taskList

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.Status
import com.amplifyframework.datastore.generated.model.TaskData
import com.edhou.taskmaster.R
import com.edhou.taskmaster.utils.StatusDisplayer

class TasksAdapter(private val onClick: (TaskData) -> Unit,
                   private val resources: Resources) : ListAdapter<TaskData, TasksAdapter.TaskViewHolder>(TaskDiffCallback) {

    class TaskViewHolder(itemView: View, val onClick: (TaskData) -> Unit, private val resources: Resources)
        : RecyclerView.ViewHolder(itemView) {
        private val taskNameTextView: TextView = itemView.findViewById(R.id.taskName)
        private val taskDescriptionTextView: TextView = itemView.findViewById(R.id.taskDescription)
        private val taskStatusTextView: TextView = itemView.findViewById(R.id.taskStatus)
        private var currentTask: TaskData? = null

        init {
            itemView.setOnClickListener {
                currentTask?.let { onClick(it) }
            }
        }

        fun bind(task: TaskData) {
            currentTask = task
            taskNameTextView.text = task.name
            taskDescriptionTextView.text = task.description
            taskStatusTextView.text = StatusDisplayer.statusToString(task.status, resources)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view, onClick, resources)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task)
    }
}

object TaskDiffCallback : DiffUtil.ItemCallback<TaskData>() {
    override fun areItemsTheSame(oldItem: TaskData, newItem: TaskData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: TaskData, newItem: TaskData): Boolean {
        return oldItem.id == newItem.id
    }
}