package zhao.siqi.com.webviewlabelclick;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Bill on 2017/9/27.
 */
public class Utils {

    public static int getScreenWidth(Context context) {
        DisplayMetrics metrics = getDisplayMetrics(context);
        return metrics.widthPixels;
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }
}
