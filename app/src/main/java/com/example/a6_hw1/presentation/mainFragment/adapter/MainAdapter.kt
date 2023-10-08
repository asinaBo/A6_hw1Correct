package com.example.a6_hw1.presentation.mainFragment.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a6_hw1.databinding.ItemMainTaskBinding
import com.example.a6_hw1.model.TaskModel

class MainAdapter(
    private var onLongClickItem: (taskModel: TaskModel) -> Unit,
    private var onDoneClick: (taskModel: TaskModel) -> Unit,
    private var onItemClick: (taskModel: TaskModel) -> Unit
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private val taskList = mutableListOf<TaskModel>()
    private val _taskList get() = taskList

    fun setData(newList: MutableList<TaskModel>) {
        _taskList.clear()
        _taskList.addAll(newList)
        notifyItemRangeInserted(_taskList.size, newList.size - _taskList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMainTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(taskList[position])
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    inner class ViewHolder(private var binding: ItemMainTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(taskModel: TaskModel) {
            taskModel.id = adapterPosition
            binding.tvTitle.text = taskModel.title
            binding.chBox.isChecked = taskModel.checked
            if (binding.chBox.isChecked) {
                binding.tvTitle.setTextColor(Color.GRAY)
            }
            binding.chBox.setOnClickListener {
                onDoneClick(taskModel)
            }
            itemView.setOnClickListener {
                onItemClick(taskModel)
            }
            itemView.setOnLongClickListener {
                onLongClickItem(taskModel)
                true
            }
        }
    }
}