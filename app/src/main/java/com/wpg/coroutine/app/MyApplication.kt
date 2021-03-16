package com.wpg.coroutine.app

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/16 13:35
 * @Description:
 */
class MyApplication : Application() {
    companion object {
        var CONTEXT: Context by Delegates.notNull()
    }
}