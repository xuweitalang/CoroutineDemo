package com.wpg.coroutine.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/12 16:44
 * @Description:
 */
class MyFragmentPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val fragments: List<Fragment>
) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}