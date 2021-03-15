package com.wpg.coroutine.data.bean

import java.io.Serializable

data class WanListResponse<T>(val offset: Int,
                           val size: Int,
                           val total: Int,
                           val pageCount: Int,
                           val curPage: Int,
                           val over: Boolean,
                           val datas: T):Serializable