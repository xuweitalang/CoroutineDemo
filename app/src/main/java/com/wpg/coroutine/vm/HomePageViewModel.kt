package com.wpg.coroutine.vm

import androidx.lifecycle.MutableLiveData
import com.wpg.coroutine.data.bean.Article
import com.wpg.coroutine.data.bean.Banner
import com.wpg.coroutine.data.bean.base.ResultData
import com.wpg.coroutine.data.repository.MainRepository
import com.wpg.coroutine.vm.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/16 9:09
 * @Description:
 */
class HomePageViewModel(private val repository: MainRepository) : BaseViewModel() {
    private var mBanner: MutableLiveData<List<Banner>> = MutableLiveData()
    private val mStickArticles: MutableLiveData<List<Article>> = MutableLiveData()
    fun loadBanner() = launchUI {
        val result = withContext(Dispatchers.IO) {
            repository.getBanner()
        }
        if (result is ResultData.Success) {
            mBanner.value = result.data
        }
    }

    fun loadStickArticles() = launchUI {
        val result = withContext(Dispatchers.IO) {
            repository.getStickArticles()
        }
        if (result is ResultData.Success) {
            mStickArticles.value = result.data
        }
    }

}