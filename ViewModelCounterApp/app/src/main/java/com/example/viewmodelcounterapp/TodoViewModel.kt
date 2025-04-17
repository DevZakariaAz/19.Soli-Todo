package com.example.viewmodelcounterapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
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
                val result = RetrofitClient.api.getTodos().take(15) // limit for testing
                _todos.value = result
            } catch (e: Exception) {
                _todos.value = listOf(
                    Todo(id = -1, title = "Error: ${e.message}", completed = false)
                )
            }
        }
    }

    fun toggleTaskCompletion(id: Int) {
        _todos.update { currentTodos ->
            currentTodos.map { todo ->
                if (todo.id == id) {
                    todo.copy(completed = !todo.completed)
                } else {
                    todo
                }
            }
        }
    }

    fun addTask(todo: Todo) {
        viewModelScope.launch {
            try {
                val newTask = RetrofitClient.api.createTask(todo)
                _todos.value += newTask
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun deleteTask(id: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.deleteTask(id)
                if (response.isSuccessful) {
                    _todos.value = _todos.value.filter { it.id != id }
                }
            } catch (e: Exception) {
                // handle error
            }
        }
    }
}
