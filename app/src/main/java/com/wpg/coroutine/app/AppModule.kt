package com.wpg.coroutine.app

import com.wpg.coroutine.data.repository.ArticleUserCase
import com.wpg.coroutine.data.repository.MainRepository
import com.wpg.coroutine.data.repository.datasource.RemoteDataSource
import com.wpg.coroutine.view.loadpage.BasePageViewForStatus
import com.wpg.coroutine.view.loadpage.SimplePageViewForStatus
import com.wpg.coroutine.vm.HomePageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/18 10:48
 * @Description:
 */
val viewModelModule = module {
    viewModel { HomePageViewModel(get(), get()) }
}

val repositoryModule = module {
    single { RemoteDataSource() }
    single { MainRepository(get()) }
    single { ArticleUserCase(get()) }
    single<BasePageViewForStatus> { SimplePageViewForStatus() }
}

val appModule = listOf(viewModelModule, repositoryModule)