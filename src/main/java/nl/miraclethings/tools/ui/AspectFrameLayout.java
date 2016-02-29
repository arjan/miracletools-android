package nl.miraclethings.tools.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import nl.miraclethings.tools.R;

/**
 * Created by arjan on 29-2-16.
 */
public class AspectFrameLayout extends FrameLayout {

    private ViewAspectRatioMeasurer varm;

    public AspectFrameLayout(Context context) {
        super(context);
    }

    public AspectFrameLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.AspectLayout,
                0, 0);

        try {
            float aspectRatio = a.getFloat(R.styleable.AspectLayout_aspectRatio, 1.0f);
            varm = new ViewAspectRatioMeasurer(aspectRatio);
        } finally {
            a.recycle();
        }
    }

    public void setAspectRatio(float r) {
        varm = new ViewAspectRatioMeasurer(r);
        requestLayout();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        varm.measure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(varm.getMeasuredWidth(), varm.getMeasuredHeight());
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(
                MeasureSpec.makeMeasureSpec(varm.getMeasuredWidth(), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(varm.getMeasuredHeight(), MeasureSpec.EXACTLY));
    }
}
