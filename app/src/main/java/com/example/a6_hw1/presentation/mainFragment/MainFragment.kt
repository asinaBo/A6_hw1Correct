package com.example.a6_hw1.presentation.mainFragment

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.core.os.bundleOf
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.a6_hw1.R
import com.example.a6_hw1.core.base.BaseFragment
import com.example.a6_hw1.databinding.FragmentMainBinding
import com.example.a6_hw1.model.TaskModel
import com.example.a6_hw1.presentation.mainFragment.adapter.MainAdapter


class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>() {
    private var adapter = MainAdapter(this::onLongClickItem, this::onDoneClick, this::onItemClick)
    override val viewModel: MainViewModel
        get() = ViewModelProvider(this)[MainViewModel::class.java]

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMainBinding.inflate(inflater, container, false)

    override fun initData() { // here i get data from second fragment after clickking or updated
        setFragmentResultListener(REQUEST_KEY_SECOND_TO_FIRST) { _, bundle ->
            bundle.getString(ADD_NEW_TASK)?.let { viewModel.addTask(it) }
            bundle.getSerializable(UPDATE_TASK)
                ?.let { viewModel.updateTask(it as TaskModel) }
        }
    }

    override fun initRcView() {
        viewModel.list.observe(viewLifecycleOwner) {
            adapter.setData(it)
            binding.rvTask.adapter = adapter
        }
    }

    override fun initListeners() {
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.secondFragment)
        }
    }

    private fun onItemClick(taskModel: TaskModel) { //kogda click na item otkryvaetsya secfr i obnovl
        setFragmentResult(
            REQUEST_KEY_FIRST_TO_SECOND,
            bundleOf(SET_TASK to taskModel)
        )
        findNavController().navigate(R.id.secondFragment)
    }

    private fun onLongClickItem(taskModel: TaskModel) {
        val alertBuilder = AlertDialog.Builder(requireContext())
        alertBuilder.setTitle(getString(R.string.delete))
        alertBuilder.setMessage(getString(R.string.delete_message))
        alertBuilder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            viewModel.deleteTask(taskModel)

        }
        alertBuilder.setNeutralButton(getString(R.string.cancel))
        { _, _ ->
        }
        alertBuilder.show()
    }

    private fun onDoneClick(taskModel: TaskModel) {
        viewModel.setTaskDone(taskModel)
    }

    override fun initMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.id_selected -> {
                        viewModel.selectedTask()
                        return true
                    }

                    R.id.id_un_selected -> {
                        viewModel.unSelectedTasks()
                        return true
                    }

                    R.id.id_show_all -> {
                        viewModel.getAllTasks()
                        return true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    companion object {
        const val REQUEST_KEY_FIRST_TO_SECOND = "request.key.to.second"
        const val REQUEST_KEY_SECOND_TO_FIRST = "request.key.to.first"
        const val ADD_NEW_TASK = "new.task"
        const val UPDATE_TASK = "update.task"
        const val SET_TASK = "set.task"
    }
}


