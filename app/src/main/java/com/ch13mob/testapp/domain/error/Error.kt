package com.ch13mob.testapp.domain.error

sealed class Error {
    object General : Error()
}

val Throwable.toError: Error
    get() = Error.General