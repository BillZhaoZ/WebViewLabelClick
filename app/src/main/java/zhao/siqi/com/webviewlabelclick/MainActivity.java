package zhao.siqi.com.webviewlabelclick;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页
 */
public class MainActivity extends Activity implements View.OnClickListener {

    public static final String LOADURL = "file:///android_asset/app.html";
    //public static final String LOADURL = "http://www.baidu.com";

    /**
     * 存放WebView集合
     */
    private List<XWebView> mWebViewsList;
    private List<TextView> mLabelList;

    /**
     * 存放WebView的布局
     */
    private FrameLayout mWebViewContainer;

    /**
     * 存放底部Button的布局
     */
    private LinearLayout mLabelContainer;

    private WebView mWebViewClick;
    private int mIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
        mWebViewsList = new ArrayList<>();
        mWebViewContainer = (FrameLayout) findViewById(R.id.web_container);
        mLabelContainer = (LinearLayout) findViewById(R.id.label_container);

        mIndex = 0;
        mLabelList = new ArrayList<TextView>();

        addWebView(mIndex);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (mWebViewClick.canGoBack()) {
                mWebViewClick.goBack();
            }
        }
        return false;
    }

    /**
     * 添加标签
     *
     * @param view
     */
    public void addLabel(View view) {

        if (null != mWebViewsList) {
            mIndex = mWebViewsList.size();
        } else {
            mIndex = 0;
        }

        addWebView(mIndex);
    }

    /**
     * 添加网页和标签
     *
     * @param index
     */
    private void addWebView(final int index) {
        final XWebView webview = new XWebView(this, mLabelContainer);
        webview.setLabelIndex(index);

        mWebViewClick = webview.getWebView();
        mWebViewContainer.addView(mWebViewClick, index);
        mWebViewsList.add(index, webview);
        mWebViewClick.setTag(index);

        TextView labelBtn = new TextView(this);
        labelBtn.setTag(index);
        labelBtn.setOnClickListener(this);
        labelBtn.setSingleLine(true);
        labelBtn.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
        labelBtn.setGravity(Gravity.CENTER_VERTICAL);//垂直居中
        labelBtn.setTextSize(20);
        labelBtn.setBackgroundColor(Color.GRAY);
        labelBtn.setTextColor(Color.WHITE);

        labelBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                // 长安删除
                closeSingleLabel(v, webview);
                return true;
            }
        });

        mLabelContainer.addView(labelBtn, index);
        mLabelList.add(labelBtn);
    }

    /**
     * 给底部button设置点击事件，切换标签
     *
     * @param view
     */
    public void onClick(View view) {
        int index = mLabelContainer.indexOfChild(view);

        WebView tempWebView = mWebViewsList.get(index).getWebView();
        mWebViewContainer.removeView(tempWebView);
        mWebViewContainer.addView(tempWebView);
        mWebViewClick = tempWebView;

        int count = index + 1;
        Toast.makeText(this, "点击了第" + count + "个", Toast.LENGTH_SHORT).show();

        // 当前标签  点击变黑
        for (int i = 0; i < mLabelList.size(); i++) {

            TextView view1 = mLabelList.get(i);

            // 当前变黑
            if (i == index) {
                view1.setTextColor(Color.BLACK);
            } else {
                // 其他复原
                view1.setTextColor(Color.WHITE);
            }
        }
    }

    /**
     * 长按删除单个标签
     *
     * @param v
     * @param webview
     */
    public void closeSingleLabel(View v, XWebView webview) {

        mLabelContainer.removeView(v);
        mWebViewContainer.removeView(webview.getWebView());
        mWebViewsList.remove(webview);
        mLabelList.remove(v);

        if (mWebViewsList.size() > 0) {
            mWebViewClick = (WebView) mWebViewContainer.getChildAt(mWebViewsList.size() - 1);
        }

        for (int i = 0; i < mWebViewsList.size(); i++) {
            XWebView view = mWebViewsList.get(i);
            view.setLabelIndex(i);
        }

        mIndex--;

        // 当前标签删除    下一个变黑
        for (int i = 0; i < mLabelList.size(); i++) {
            TextView view1 = mLabelList.get(i);

            // 当前集合的第一个  就是下一个  变黑
            if (i == 0) {
                view1.setTextColor(Color.BLACK);
            }
        }
    }

    /**
     * 关闭所有标签
     *
     * @param view
     */
    public void closeLabel(View view) {
        mWebViewsList.clear();
        mLabelContainer.removeAllViews();
        mWebViewContainer.removeAllViews();
        mLabelList.clear();

        mIndex = 0;
        addWebView(mIndex);
    }

}
