package com.rom.garagely.helper

sealed class Resource<T>(
    val data :T?=null,
    val message:String?=null
) {

    val isSuccess : Boolean get() = this is Success
    val isFailure : Boolean get() = this !is Success

    fun getOrNull(): T? {
        return (this as? Success)?.data
    }

    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(message: String?) : Resource<T>(null,message)
    class Loading<T> : Resource<T>()
}