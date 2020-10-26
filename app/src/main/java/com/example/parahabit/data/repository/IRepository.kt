package com.example.parahabit.data.repository

interface IRepository {
    fun getHabitRepository(): IHabitRepository
    fun getExecutionRepository(): IExecutionRepository
}