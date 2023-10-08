package com.example.a6_hw1.presentation.mainFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a6_hw1.model.TaskModel

class MainViewModel : ViewModel() {
    private var tasks = mutableListOf<TaskModel>()
    private val _list = MutableLiveData<MutableList<TaskModel>>()
    val list: LiveData<MutableList<TaskModel>> get() = _list

    fun getAllTasks() {
        _list.value = tasks
    }

    fun addTask(title: String) {
        tasks.add(TaskModel(id = tasks.size, title = title))
        getAllTasks()
    }

    fun deleteTask(task: TaskModel) {
        tasks.removeAt(task.id)
    }

    fun setTaskDone(task: TaskModel) {
        tasks[task.id].checked = true
        getAllTasks()
    }

    fun updateTask(task: TaskModel) {
        deleteTask(task)
        tasks.add(task.id, task)
    }

    fun unSelectedTasks() {
        val sortedList = ArrayList<TaskModel>()
        tasks.forEach { task ->
            if (!task.checked)
                sortedList.add(task)
        }
        _list.value = sortedList
    }

    fun selectedTask() {
        val sortedList = ArrayList<TaskModel>()
        tasks.forEach { task ->
            if (task.checked)
                sortedList.add(task)
        }
        _list.value = sortedList
    }
}