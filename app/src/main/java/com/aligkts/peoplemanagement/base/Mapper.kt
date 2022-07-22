package com.aligkts.peoplemanagement.base

interface Mapper<Output, Input> {
    fun map(input: Input): Output
}

interface ListMapper<Output, Input> : Mapper<List<Output>, List<Input>>
