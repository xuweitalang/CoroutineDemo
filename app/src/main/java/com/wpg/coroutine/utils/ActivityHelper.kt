package com.wpg.coroutine.utils

import android.app.Activity
import android.app.Application
import com.wpg.coroutine.adapter.ActivityLifecycleCallbacksAdapter

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/18 10:07
 * @Description:
 */
object ActivityHelper {
    var activities = mutableListOf<Activity>()
    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(
            ActivityLifecycleCallbacksAdapter(
                onActivityCreated = { activity, _ ->
                    activities.add(activity)
                },
                onActivityDestroyed = { activity ->
                    activities.remove(activity)
                }
            )
        )
    }

    /**
     * vararg:修饰可变长参数
     * finish指定一个或多个activity
     */
    fun finish(vararg clazz: Class<out Activity>) {
        activities.forEach { activity ->
            if (clazz.contains(activity.javaClass)) {
                activity.finish()
            }
        }
    }
}