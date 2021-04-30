package com.edhou.taskmaster.taskList

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.edhou.taskmaster.R
import com.edhou.taskmaster.models.Task

class TasksAdapter(private val onClick: (Task) -> Unit,
                   private val resources: Resources) : ListAdapter<Task, TasksAdapter.TaskViewHolder>(TaskDiffCallback) {

    class TaskViewHolder(itemView: View, val onClick: (Task) -> Unit, private val resources: Resources)
        : RecyclerView.ViewHolder(itemView) {
        private val taskNameTextView: TextView = itemView.findViewById(R.id.taskName)
        private val taskDescriptionTextView: TextView = itemView.findViewById(R.id.taskDescription)
        private val taskStatusTextView: TextView = itemView.findViewById(R.id.taskStatus)
        private var currentTask: Task? = null

        init {
            itemView.setOnClickListener {
                currentTask?.let { onClick(it) }
            }
        }

        fun bind(task: Task) {
            currentTask = task
            taskNameTextView.text = task.name
            taskDescriptionTextView.text = task.description
            taskStatusTextView.text = task.status.getString(resources)
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

object TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }
}