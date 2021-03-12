package com.wpg.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.math.log
import kotlin.math.log2

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var thread = object : Thread() {
            override fun run() {
                super.run()


            }
        }


        GlobalScope.launch(start = CoroutineStart.ATOMIC) {

        }
    }

    suspend fun main() {
        log2(1.0)
        try {
            coroutineScope {
                log2(2.0)
                launch {
                    log2(3.0)
                    launch {
                        log2(4.0)
                        delay(100)
                        throw ArithmeticException("Hey!!")
                    }
                    log2(5.0)
                }
                log2(6.0)
                val job = launch {
                    log2(7.0)
                    delay(1000)
                }
                try {
                    log2(8.0)
                    job.join()
                    log2(9.0)
                } catch (e: Exception) {
                    log2(10.0)
                }
            }
            log2(11.0)
        } catch (e: Exception) {
            log2(12.0)
        }
    }
}