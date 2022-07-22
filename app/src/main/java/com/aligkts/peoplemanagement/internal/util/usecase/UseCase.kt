/**
 * Copyright (C) 2020 Fernando Cejas Open Source Project
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

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Suppress("GENERIC_NAME")
abstract class UseCase<out Type : Any?, in Params>(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    object None {
        override fun toString() = "UseCase.None"
    }

    protected abstract suspend fun buildUseCase(params: Params): Type

    suspend fun run(params: Params): Either<FailureException, Type> =
        withContext(dispatcher) {
            try {
                Either.Right(buildUseCase(params))
            } catch (failureException: FailureException) {
                Either.Left(failureException)
            }
        }
}
