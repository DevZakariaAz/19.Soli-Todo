package com.example.lab_view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab_view_model.models.RetrofitClient
import com.example.lab_view_model.models.Todo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodoViewModel : ViewModel() {
    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos

    init {
        fetchTodos()
    }

    fun fetchTodos() {
        viewModelScope.launch {
            try {
                val todoList = RetrofitClient.api.getTodos()
                _todos.value = todoList
            } catch (e: Exception) {
                _todos.value = listOf(Todo(0, "Erreur : ${e.message}", false))
            }
        }
    }
}
