package com.example.agroapp.ui.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import com.example.agroapp.BR;
import com.example.agroapp.R;
import com.example.agroapp.app.Constant;
import com.example.agroapp.databinding.ActivityDetailsBinding;
import com.example.agroapp.model.Article;
import com.example.agroapp.viewModel.DetailViewModel;
import com.just.agentweb.AgentWeb;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * author : jiangxue
 * date : 2023/5/22 15:24
 * description :
 */
public class DetailsActivity extends BaseActivity<ActivityDetailsBinding, DetailViewModel> {

    private AgentWeb mAgentWeb;

    private String mUrl;
    private Article entity;

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(Constant.KEY_URL, url);
        context.startActivity(intent);
    }

    @Override
    public void initParam() {

        Intent intent=getIntent();
        //按调用时捆绑的键名
        Bundle data = intent.getBundleExtra("data");
        mUrl = data.getString("url");
    }
    private void initToolbar() {
        setSupportActionBar(binding.iToolbar.tbToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initWebView() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(binding.llRoot, new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator()
                .setWebChromeClient(mWebChromeClient)
                .createAgentWeb()
                .ready()
                .go(mUrl);
    }

    private WebChromeClient mWebChromeClient = new WebChromeClient() {

        @Override
        public void onReceivedTitle(WebView view, String title) {
            binding.iToolbar.tbToolbar.setTitle(title);
        }
    };


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_details;
    }

    @Override
    public int initVariableId() {
        return com.example.agroapp.BR.viewModel;
    }

    @Override
    public void initData() {
        initToolbar();
        initWebView();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (!mAgentWeb.back()) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
