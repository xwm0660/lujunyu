package com.bm.container.module.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bm.container.R;
import com.bm.container.databinding.ActivityWebBinding;
import com.bm.container.module.BaseActivity;
import com.bm.container.view.TextUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 网页
 */
public class WebActivity extends BaseActivity {
    private ActivityWebBinding binding;
    private Context context;
    private String url;
    private String titleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web);
        context = this;

        if (null != getIntent()) {
            url = getIntent().getExtras().getString("url");
            titleName = getIntent().getExtras().getString("titleName");
        }

        setTopbar();
        setLoading();
        initWeb();
        binding.web.loadUrl(url);
    }

    /**
     * 预留js交互
     */
    @JavascriptInterface
    public void back() {
        binding.refresh.setRefreshing(true);
        Observable
                .timer(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    finishAc();
                    binding.refresh.setRefreshing(false);
                });
    }

    /**
     * webview设定
     */
    private void initWeb() {

        WebSettings webSettings = binding.web.getSettings();
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        binding.web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                binding.web.loadUrl(url);
                return true;
            }
        });
//		binding.web.addJavascriptInterface(WebActivity.this, "interface");
    }

    private void setLoading() {
        setLoading(binding.refresh);
        binding.refresh.setEnabled(false);
        binding.refresh.setColorSchemeColors(loadingColors);
    }

    private void setTopbar() {
        binding.topbar.toolbar.setTitle("");
        binding.topbar.toolbar.setNavigationIcon(R.drawable.toolbar_back);
        if (!TextUtils.isEmpty(titleName)) {
            binding.topbar.title.setText(titleName);
        } else {
            binding.topbar.title.setText("网页");
        }
        setSupportActionBar(binding.topbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.topbar.toolbar.setNavigationOnClickListener(view -> finishAc());
    }
}

