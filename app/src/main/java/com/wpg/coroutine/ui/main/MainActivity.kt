package com.wpg.coroutine.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.coder.zzq.smartshow.snackbar.SmartSnackbar
import com.jeremyliao.liveeventbus.LiveEventBus
import com.wpg.coroutine.R
import com.wpg.coroutine.ext.SET_THEME
import com.wpg.coroutine.ui.base.BaseActivity
import com.wpg.coroutine.ui.homepage.MainFragment
import com.wpg.coroutine.ui.homesystem.HomeSystemFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {
    private var hideFragment: Fragment? = null //要隐藏的fragment
    private var currentFragment: Fragment? = null //要显示的fragment
    private val fragments = arrayListOf<Fragment>()
    private var lastPosition = 0
    private var mPosition = 0

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //activity重建是保存页面的位置
        outState.putInt("last_position", lastPosition)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        lastPosition = savedInstanceState.getInt("last_position") //获取重建时保存的也没的位置
        setSelectFragment(lastPosition)
        bottom_nav_view.selectedItemId = bottom_nav_view.menu.getItem(lastPosition).itemId
    }

    override fun initView(savedInstanceState: Bundle?) {
        LiveEventBus.get(SET_THEME).observe(this, Observer {
            recreate()
        })
        fragments.apply {
            add(MainFragment())
            add(HomeSystemFragment())
        }
        if (savedInstanceState == null) {
            //根据传入的Bundle对象判断是正常启动还是重建 true表示正常启动，false表示重建
            setSelectFragment(0)
        }

        //BottomNavigationView 点击事件监听
        bottom_nav_view.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.home_mine -> setSelectFragment(0)
                R.id.home_system -> setSelectFragment(1)
            }
            true
        }
    }

    override fun initData() {
    }

    /**
     * 根据位置选择fragment
     * @param position 被选中的fragment的位置
     */
    private fun setSelectFragment(position: Int) {
        bottom_nav_view.menu.getItem(position).isChecked = true
        val fragmentManger = supportFragmentManager
        val beginTransaction = fragmentManger.beginTransaction()
        currentFragment =
            fragmentManger.findFragmentByTag("fragment$position")//要显示的fragment(解决了activity重建时新建实例的问题)
        hideFragment =
            fragmentManger.findFragmentByTag("fragment$lastPosition")//要隐藏的fragment(解决了activity重建时新建实例的问题)
        if (position != lastPosition) { //位置不同
            hideFragment?.let { beginTransaction.hide(it) } // 如果要隐藏的fragment存在，则隐藏
            if (currentFragment == null) {//如果要显示的fragment不存在，则新加并提交事务
                currentFragment = fragments[position]
                currentFragment?.let {
                    beginTransaction.add(R.id.fl_container, it, "fragment$position")
                }
            } else { //如果要显示的fragment存在则直接显示
                currentFragment?.let { beginTransaction.show(it) }
            }
        } else { //位置相同
            if (currentFragment == null) {//如果fragment不存在(第一次启动应用的时候)
                currentFragment = fragments[position]
                currentFragment?.let {
                    beginTransaction.add(R.id.fl_container, it, "fragment$position")
                }
            }//如果位置相同，且fragment存在，则不作任何操作
        }
        beginTransaction.commit()
        mPosition = position
        lastPosition = position //更新要隐藏的fragment的位置
    }

    override fun recreate() {
        val beginTransaction = supportFragmentManager.beginTransaction()
        for (index in 0..2) {
            supportFragmentManager.findFragmentByTag("fragment$index")?.let {
                beginTransaction.remove(it)
            }
        }
        beginTransaction.commitAllowingStateLoss()
        super.recreate()
    }

    private var previousTimeMillis = 0L
    override fun onBackPressed() {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - previousTimeMillis < 2000) {
            super.onBackPressed()
        } else {
            SmartSnackbar.get(this).show(R.string.press_again_to_exit)
            previousTimeMillis = currentTimeMillis
        }

    }
}
