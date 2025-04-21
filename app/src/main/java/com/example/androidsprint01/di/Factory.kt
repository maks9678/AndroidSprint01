package com.example.androidsprint01.di

interface Factory<T> {
    fun create(): T
}