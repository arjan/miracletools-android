package nl.miraclethings.tools.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.TypedValue;
import android.widget.TextView;
import android.view.View;
import android.text.TextUtils;

/**
 * Created by arjan on 29-2-16.
 */
public class LayoutUtil {

    public static float dpToPx(int dp, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources()
                .getDisplayMetrics());
    }

    public static int getActionBarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize});
        int mActionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        return mActionBarSize;

    }
    
    public static void setTextOrHide(TextView textView, String content){
        if(textView != null){
            textView.setText(content);
            textView.setVisibility(TextUtils.isEmpty(content)?View.GONE: View.VISIBLE);
        }
    }
}
