/**
 * Copyright (C) 2019 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * ```
 *      http://www.apache.org/licenses/LICENSE-2.0
 * ```
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.aligkts.peoplemanagement.internal.util.usecase

import com.aligkts.peoplemanagement.internal.util.usecase.Either.Left
import com.aligkts.peoplemanagement.internal.util.usecase.Either.Right

/**
 * Represents a value of one of two possible types (a disjoint union). Instances of [Either] are
 * either an instance of [Left] or [Right]. FP Convention dictates that [Left] is used for "failure"
 * and [Right] is used for "success".
 *
 * @see Left
 * @see Right
 */
@Suppress(
    "IDENTIFIER_LENGTH",
)
sealed class Either<out L, out R> {
    val isRight
        get() = this is Right<R>
    val isLeft
        get() = this is Left<L>

    fun <L> left(a: L) = Left(a)
    fun <R> right(b: R) = Right(b)

    @Suppress("MemberNameEqualsClassName")
    fun either(fnL: (L) -> Any, fnR: (R) -> Any): Any =
        when (this) {
            is Left -> fnL(a)
            is Right -> fnR(b)
        }

    @Suppress("MemberNameEqualsClassName")
    fun either(fnL: (L) -> Any, fnR: () -> Unit): Any =
        when (this) {
            is Left -> fnL(a)
            is Right -> fnR()
        }

    fun <T> transform(fnR: (R) -> T): Either<L, T> {
        return when (this) {
            is Left -> this
            is Right -> Right(fnR(b))
        }
    }

    fun getRightHandleLeft(fnL: (L) -> Any): R? {
        when (this) {
            is Left -> fnL(a)
            is Right -> return b
        }
        return null
    }

    fun ifRight(block: (R) -> Unit) {
        if (this is Right) {
            block(b)
        } else {
            return
        }
    }

    fun ifLeft(block: (L) -> Unit) {
        if (this is Left) {
            block(a)
        } else {
            return
        }
    }

    /** * Represents the left side of [Either] class which by convention is a "Failure". */
    data class Left<out L>(val a: L) : Either<L, Nothing>()

    /** * Represents the right side of [Either] class which by convention is a "Success". */
    data class Right<out R>(val b: R) : Either<Nothing, R>()
}

// Credits to Alex Hart -> https://proandroiddev.com/kotlins-nothing-type-946de7d464fb
// Composes 2 functions

@Suppress(
    "IDENTIFIER_LENGTH",
)
fun <A, B, C> ((A) -> B).compose(f: (B) -> C): (A) -> C = { f(this(it)) }

fun <T, L, R> Either<L, R>.flatMap(fn: (R) -> Either<L, T>): Either<L, T> =
    when (this) {
        is Left -> Left(a)
        is Right -> fn(b)
    }

fun <T, L, R> Either<L, R>.map(fn: (R) -> (T)): Either<L, T> = this.flatMap(fn.compose(::right))
