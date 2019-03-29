package com.dongyangbike.app.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongyangbike.app.R;
import com.dongyangbike.app.util.StringUtil;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import tech.gujin.toast.ToastUtil;

/**
 * Created by xuyun on 2018/6/5.
 */

public class WebviewActivity extends BaseActivity {

    BridgeWebView bridgeWebView;
    private TextView mTitle;
    private ImageView mBack;
    private String mUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        mTitle = findViewById(R.id.title);
        mBack = findViewById(R.id.back);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mUrl = getIntent().getStringExtra("url");
        if(StringUtil.isStringEmpty(mUrl)) {
            mUrl = "http://www.baidu.com";
        }

        bridgeWebView = findViewById(R.id.webview);
        bridgeWebView.setDefaultHandler(new DefaultHandler());
        bridgeWebView.setWebChromeClient(new WebChromeClient());
        bridgeWebView.setWebViewClient(getWebViewClient());
        bridgeWebView.loadUrl(mUrl);

        /**
         * js发送给按住消息   submitFromWeb 是js调用的方法名    安卓\返回给js
         */
        bridgeWebView.registerHandler("submitFromWeb", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                ToastUtil.show(data);
                function.onCallBack( "返回给Toast的alert");
            }
        });
    }

    private WebViewClient getWebViewClient() {
        /**
         * WebViewClient就是帮助WebView处理各种通知、请求事件的
         */
        WebViewClient webViewClient = new WebViewClient() {
            /**
             * 打开网页时不调用系统浏览器， 而是在本WebView中显示
             * @param view
             * @param url
             * @return
             */
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try {
                    url = URLDecoder.decode(url, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                if (url.startsWith("mailto:") || url.startsWith("geo:") || url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                } else {
                    return super.shouldOverrideUrlLoading(view, url);
                }
            }

            /**
             * 开始载入页面调用的，我们可以设定一个loading的页面，告诉用户程序在等待网络响应
             * @param view
             * @param url
             * @param favicon
             */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            /**
             * 在页面加载结束时调用。可以关闭loading 条，切换程序动作
             * @param view
             * @param url
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                mTitle.setText(view.getTitle());
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); //接受证书
            }
        };

        return webViewClient;
    }
}
