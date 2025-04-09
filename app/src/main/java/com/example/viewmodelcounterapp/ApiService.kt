package com.example.viewmodelcounterapp

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("todos")
    suspend fun getTodos(): List<Todo>

    @POST("todos")
    suspend fun createTask(@Body todo: Todo): Todo

    @DELETE("todos/{id}")
    suspend fun deleteTask(@Path("id") id: Int): Response<Unit>
}