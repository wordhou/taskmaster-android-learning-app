package com.edhou.taskmaster

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import org.junit.Test

class FlowTests {
    @Test
    fun coRoutinesBasic() {
        runBlocking {
            val x : Job = launch {
                delay(2000L)
                println("Job!")
            }
            x.join()
            launch {
                delay(1000L)
                println("Hello?")
            }
            println("World!")
        }
    }

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