package zhao.siqi.com.webviewlabelclick;

import android.content.Context;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 自定义webview
 */
class XWebView {

    private Context context;
    private WebView mWebView2;
    private int labelIndex;
    private LinearLayout labelContainer;

    public int getLabelIndex() {
        return labelIndex;
    }

    public void setLabelIndex(int labelIndex) {
        this.labelIndex = labelIndex;
    }

    public XWebView(Context context, LinearLayout labelContainer) {
        this.context = context;
        this.labelContainer = labelContainer;
        init();
    }

    private void init() {
        mWebView2 = new WebView(context);

        //声明WebSettings子类
        WebSettings webSettings = mWebView2.getSettings();

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        // webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        mWebView2.setWebViewClient(new XWebViewClient());

        mWebView2.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

                // 设置标题
                if (labelContainer.getChildAt(labelIndex) instanceof TextView) {
                    ((TextView) labelContainer.getChildAt(labelIndex)).setText(labelIndex + 1 + ":" + title);
                }
            }
        });

        mWebView2.loadUrl(MainActivity.LOADURL);
    }

    public WebView getWebView() {
        return mWebView2;
    }

    class XWebViewClient extends WebViewClient {

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}