package com.example.a6_hw1.presentation.addFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.a6_hw1.R
import com.example.a6_hw1.core.base.BaseFragment
import com.example.a6_hw1.databinding.FragmentAddNewTaskBinding
import com.example.a6_hw1.model.TaskModel
import com.example.a6_hw1.presentation.mainFragment.MainFragment.Companion.ADD_NEW_TASK
import com.example.a6_hw1.presentation.mainFragment.MainFragment.Companion.REQUEST_KEY_FIRST_TO_SECOND
import com.example.a6_hw1.presentation.mainFragment.MainFragment.Companion.REQUEST_KEY_SECOND_TO_FIRST
import com.example.a6_hw1.presentation.mainFragment.MainFragment.Companion.SET_TASK
import com.example.a6_hw1.presentation.mainFragment.MainFragment.Companion.UPDATE_TASK

class AddNewTaskFragment : BaseFragment<FragmentAddNewTaskBinding, ViewModel>() {
    override val viewModel: ViewModel
        get() = TODO("Not yet implemented")

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAddNewTaskBinding.inflate(inflater, container, false)

    private var _task: TaskModel? = null

    override fun initData() {
        setFragmentResultListener(REQUEST_KEY_FIRST_TO_SECOND) { _, bundle ->
            bundle.getSerializable(SET_TASK)?.let { task ->
                _task = task as TaskModel
                loadData()
            }
        }
    }

    private fun loadData() {
        _task?.let { task ->
            binding.btnAdd.text = getString(R.string.update)
            binding.etTitle.setText(task.title)
            binding.cbState.isChecked = task.checked
            binding.cbState.visibility = View.VISIBLE
        }
    }

    override fun initListeners() {
        binding.btnAdd.setOnClickListener {
            if (_task == null)
                addNewTask()
            else {
                _task!!.title = binding.etTitle.text.toString()
                _task!!.checked = binding.cbState.isChecked
                updateTask()
            }
        }
    }

    private fun addNewTask() {
        setFragmentResult(
            REQUEST_KEY_SECOND_TO_FIRST,
            bundleOf(ADD_NEW_TASK to binding.etTitle.text.toString()))
        findNavController().navigateUp()
    }

    private fun updateTask() {
        setFragmentResult(
            REQUEST_KEY_SECOND_TO_FIRST,
            bundleOf(UPDATE_TASK to _task)
        )
        findNavController().navigateUp()
    }
}
