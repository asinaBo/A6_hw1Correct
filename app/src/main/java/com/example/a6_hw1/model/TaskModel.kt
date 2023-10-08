package com.example.a6_hw1.model

import java.io.Serializable

data class TaskModel(
    var id: Int,
    var title: String,
    var checked: Boolean = false
) : Serializable
