package nl.miraclethings.tools.ui;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by arjan on 29-2-16.
 */
public class LayoutUtil {

    public static float dpToPx(int dp, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources()
                .getDisplayMetrics());
    }


}
