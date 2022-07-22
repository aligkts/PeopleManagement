package com.aligkts.peoplemanagement.internal.util.usecase

import java.io.IOException

sealed class FailureException : IOException() {
    object UnknownException : FailureException()
}
