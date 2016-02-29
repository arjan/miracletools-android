package nl.miraclethings.tools.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import nl.miraclethings.tools.R;

/**
 * Created by arjan on 29-2-16.
 */
public class TooltipFrameLayout extends FrameLayout {
    private Paint mBG;
    private RectF mMainRect;
    private float mCornerRadius;

    public TooltipFrameLayout(Context context) {
        super(context);
        init();
    }

    public TooltipFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TooltipFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBG = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBG.setColor(Color.WHITE);
        mBG.setStyle(Paint.Style.FILL);
        mMainRect = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mMainRect.left = getPaddingLeft();
        mMainRect.top = getPaddingTop();
        mMainRect.right = getWidth() - getPaddingRight();
        mMainRect.bottom = getHeight() - getPaddingBottom();

        mCornerRadius = LayoutUtil.dpToPx(10, getContext());
    }

    @Override
    public void dispatchDraw(Canvas canvas) {
        canvas.save();

        // draw the tooltip body
        canvas.drawRoundRect(mMainRect, mCornerRadius, mCornerRadius, mBG);

        // draw the tip
        float d = getPaddingTop() + 2;
        float c = mMainRect.left + (mMainRect.right - mMainRect.left) / 2;

        Path path = new Path();
        path.moveTo(c-d, d);
        path.lineTo(c, 0);
        path.lineTo(c+d, d);
        path.close();
        canvas.drawPath(path, mBG);

        canvas.restore();

        super.dispatchDraw(canvas);
    }

}
