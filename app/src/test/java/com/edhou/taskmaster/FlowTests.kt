package com.edhou.taskmaster

import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.Test

class FlowTests {
    @InternalCoroutinesApi
    @Test
    fun flowStuff() {

        val flow : Flow<Int> = flow {
            for(i in 1..5) {
                emit(i)
                delay(300)
            }

        }
        runBlocking {
            flow.take(2).collect { println(it) }
        }
    }
}