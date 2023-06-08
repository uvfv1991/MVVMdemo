package com.example.kotlin.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.kotlin.R
import com.example.kotlin.databinding.ActivityMainBinding
import com.example.kotlin.ui.fragment.home.HomepageFragment
import me.majiajie.pagerbottomtabstrip.NavigationController
import java.util.*

//public class MainActivity extends BaseActivity<ActivityMainBinding, BaseViewModel> {
class MainActivity : AppCompatActivity() {
    protected final var TAG : String = "MainActivity";
    private var mFragments: ArrayList<Fragment>? = null
   //在使用lateinit 定义的变量前 一定会给他一个实例 保证他不会是空对象 而 by lazy 则是在第一次使用时 初始化对象
    private val binding by lazy { DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //初始化Fragment
        initFragment()
        //initToolbar();
        //初始化底部Button
        initBottomTab()
    }




    private fun initFragment() {
        mFragments = ArrayList()
        mFragments!!.add(HomepageFragment())
        mFragments!!.add(HomepageFragment())
        mFragments!!.add(HomepageFragment())
        mFragments!!.add(HomepageFragment())

        //默认选中第一个
        commitAllowingStateLoss(0)
    }

    private fun initToolbar() {

        //setSupportActionBar(binding.iToolbar.tbToolbar);
        val actionBar = actionBar

        //actionBar.setHomeAsUpIndicator(R.drawable.ic_menu)
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        //actionBar.setTitle(R.string.app_name);
        actionBar.title = "测试APP"
    }

    private fun initBottomTab() {
        val navigationController: NavigationController = binding!!.pagerBottomTab.material()
                .addItem(R.drawable.ic_homepage, "首页")
                .addItem(R.drawable.ic_system, "体系")
                .addItem(R.drawable.ic_we_chat, "公众号")
                .addItem(R.drawable.ic_navigation, "导航")
                .addItem(R.drawable.ic_project, "项目")
                .build()
    }

    private fun commitAllowingStateLoss(position: Int) {
        hideAllFragment()
        val transaction = supportFragmentManager.beginTransaction()
        var currentFragment = supportFragmentManager.findFragmentByTag(position.toString() + "")
        if (currentFragment != null) {
            transaction.show(currentFragment)
        } else {
            currentFragment = mFragments!![position]
            transaction.add(R.id.frameLayout, currentFragment, position.toString() + "")
        }
        transaction.commitAllowingStateLoss()
    }

    //隐藏所有Fragment
    private fun hideAllFragment() {
        val transaction = supportFragmentManager.beginTransaction()

//personList.indices，可以得到所得对象的下标值
        for (i in mFragments!!.indices) {
            val currentFragment = supportFragmentManager.findFragmentByTag(i.toString() + "")
            if (currentFragment != null) {
                transaction.hide(currentFragment)
            }
        }
        transaction.commitAllowingStateLoss()
    }
}


