package com.lygttpod.android.pokemonsearch

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

/**
 * <pre>
 *      author  : Allen
 *      date    : 2023/10/26
 *      desc    :
 * </pre>
 */
class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        countDown(lifecycleScope) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    /**
     * countdown for show welcome text
     */
    private fun countDown(scope: CoroutineScope, duration: Int = 2, onFinished: () -> Unit): Job {
        return flow {
            if (duration <= 0) {
                onFinished()
            }
            for (t in duration downTo 1) {
                emit(t)
                delay(1000L)
            }
        }
            .flowOn(Dispatchers.Main)
            .onCompletion { cause ->
                if (cause == null) {
                    onFinished()
                }
            }
            .launchIn(scope)
    }
}