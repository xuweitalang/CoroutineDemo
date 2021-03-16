package com.wpg.coroutine.ext

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.wpg.coroutine.app.MyApplication

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/16 15:32
 * @Description:
 */
object CookieClass {
    /**Cookie*/
    val cookiePersistor = SharedPrefsCookiePersistor(MyApplication.CONTEXT)
    val cookieJar = PersistentCookieJar(SetCookieCache(), cookiePersistor)

    /**清除Cookie*/
    fun clearCookie() = cookieJar.clear()

    /**是否有Cookie*/
    fun hasCookie() = cookiePersistor.loadAll().isNotEmpty()
}