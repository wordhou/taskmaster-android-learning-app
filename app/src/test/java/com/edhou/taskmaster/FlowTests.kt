package com.edhou.taskmaster

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.junit.Test

class FlowTests {

    fun naturalNumbersSlowFlow(): Flow<Int> = flow {
        var i = 0;
        while(true) {
            delay(500)
            emit(i)
            i++
        }
    }

    @Test
    fun flowSuspension() {
        runBlocking {
            val job = launch {
                naturalNumbersSlowFlow().collect { println(it) }
            }
            delay(5000)
            job.cancel()
            println("After cancel")
        }
    }

    @Test
    fun coroutineThread() {
        val x : Flow<Int> = flow {
            emit(basicSuspend(1))
            emit(basicSuspend(2))
            emit(basicSuspend(3))
        }

        runBlocking {
            val job = launch {
                x.collect { println(it) }
            }
            println(Thread.currentThread().name)

        }
    }

    suspend fun basicSuspend(x: Int): Int {
        delay(1000)
        return x + 42
    }

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