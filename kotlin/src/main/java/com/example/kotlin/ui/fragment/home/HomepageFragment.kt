package com.example.kotlin.ui.fragment.home

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.kotlin.R
import com.example.kotlin.adapter.ArticleListAdapter
import com.example.kotlin.adapter.TestBannerAdapter
import com.example.kotlin.data.Banner
import com.example.kotlin.databinding.FragmentHomepageBindingImpl
import com.example.kotlin.ui.HomeViewModel
import com.example.kotlin.util.InjectorUtil.getHomeViewModelFactory
import com.youth.banner.config.BannerConfig
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.indicator.RoundLinesIndicator
import com.youth.banner.util.BannerUtils
import kotlinx.android.synthetic.main.fragment_homepage.*

/**
 *  author : jiangxue
 *  date : 2023/6/2 10:10
 *  description :
 */
class HomepageFragment : Fragment() {


    private val viewModel by lazy { ViewModelProviders.of(this, getHomeViewModelFactory()).get(HomeViewModel::class.java) }
    private var progressDialog: ProgressDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_homepage, container, false)

        val binding = DataBindingUtil.bind<FragmentHomepageBindingImpl>(view)
        binding?.viewModel= viewModel
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
        initBanner()
        initRecyclerView()
        observe()
    }

    private fun observe() {
        viewModel.dataChanged.observe(viewLifecycleOwner, Observer {

            viewModel.dataList?.let {
                articleListAdapter.run {
                    // 列表总数
                    val total = viewModel.article.total
                    // 当前总数
                    if (viewModel.article.offset >= total || data.size >= total) {
                        loadMoreEnd()
                        return@let
                    }
                    if (swipeRefreshLayout.isRefreshing) {
                        replaceData(it)
                    } else {
                        addData(it)
                    }
                    loadMoreComplete()
                    setEnableLoadMore(true)
                }
            }
           swipeRefreshLayout.isRefreshing = false


        })

        viewModel.bannerChanged.observe(viewLifecycleOwner, Observer {
            viewModel.banner?.let {
                var banner = (b_banner as com.youth.banner.Banner<Banner, TestBannerAdapter>)
                banner.apply {
                    addBannerLifecycleObserver(this@HomepageFragment)
                    setBannerRound(20f)
                    setIndicator(RoundLinesIndicator(activity))
                    setAdapter(TestBannerAdapter(it))
                }

            }
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) showProgressDialog()
            else closeProgressDialog()
        })
    }
    /**
     * LoadMoreListener
     */
    private val onRequestLoadMoreListener = BaseQuickAdapter.RequestLoadMoreListener {
       viewModel.loadMoreData()
    }
    /**
     * ArticleListAdapter
     */
    private val articleListAdapter: ArticleListAdapter by lazy {
        ArticleListAdapter(requireActivity(),viewModel.dataList)
    }

    /**
     * Banner RecyclerView adapter
     */
    private val bannerAdapter: TestBannerAdapter by lazy {
        TestBannerAdapter( viewModel.banner)
    }
    /**
     * ItemChildClickListener
     */
    private val onItemChildClickListener =
            BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
                /*if (datas.size != 0) {
                    val data = datas[position]
                    when (view.id) {
                        R.id.homeItemLike -> {
                            if (isLogin) {
                                val collect = data.collect
                                data.collect = !collect
                                typeArticleAdapter.setData(position, data)
                                typeArticlePresenter.collectArticle(data.id, !collect)
                            } else {
                                Intent(activity, LoginActivity::class.java).run {
                                    startActivity(this)
                                }
                                activity.toast(getString(R.string.login_please_login))
                            }
                        }
                    }
                }*/
            }



    /**
     * ItemClickListener
     */
    private val onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
       /* if (datas.size != 0) {
            Intent(activity, ContentActivity::class.java).run {
                putExtra(Constant.CONTENT_URL_KEY, datas[position].link)
                putExtra(Constant.CONTENT_ID_KEY, datas[position].id)
                putExtra(Constant.CONTENT_TITLE_KEY, datas[position].title)
                startActivity(this)
            }
        }*/
    }

     fun initData() {

        viewModel.getHomeData()
    }
    private fun initBanner() {
        b_banner.setIndicator(CircleIndicator(activity))
        b_banner.setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
        b_banner.setIndicatorMargins(IndicatorConfig.Margins(0, 0,
                BannerConfig.INDICATOR_MARGIN, BannerUtils.dp2px(12f)))
    }
    /*
    let函数可以先行判空，然后再调用，但在函数体内还是需要使用it来指代调用者
    with函数没有先行判空，但好处是可以再函数体内直接使用传入参数的方法和变量
    run 集合了两者的优点加以运用（节约了中间值 ）
    */

    private fun initRecyclerView() {
        recyclerView.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = articleListAdapter
        }
        articleListAdapter.run {
            setOnLoadMoreListener(onRequestLoadMoreListener, recyclerView)
            onItemClickListener = this@HomepageFragment.onItemClickListener
            onItemChildClickListener = this@HomepageFragment.onItemChildClickListener
            //setEmptyView()
        }

        articleListAdapter.setNewData(viewModel.dataList)
        recyclerView.adapter=articleListAdapter

        bannerAdapter.run {
            //onItemClickListener = this@HomepageFragment.onBannerItemClickListener
        }
    }



    /**
     * 显示进度对话框
     */
    private fun showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(activity)
            progressDialog?.setMessage("正在加载...")
            progressDialog?.setCanceledOnTouchOutside(false)
        }
        progressDialog?.show()
    }

    /**
     * 关闭进度对话框
     */
    private fun closeProgressDialog() {
        progressDialog?.dismiss()
    }
}









