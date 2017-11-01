package com.rolithunderbird.wheelchairmap.javaClasses;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

/**
 * Created by lucasramosoromi on 10/17/17.
 */
public class CircleView extends SubsamplingScaleImageView {

    private int strokeWidth;
    private Boolean paintCircle = false;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attr) {
        super(context, attr);
        initialise();
    }

    public void setPaintCircle(Boolean paintCircle) {
        this.paintCircle = paintCircle;
    }

    private void initialise() {
        float density = getResources().getDisplayMetrics().densityDpi;
        strokeWidth = (int)(density/60f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (paintCircle) {

            // Don't draw pin before image is ready so it doesn't move around during setup.
            if (!isReady()) {
                return;
            }

            //Position of the Circle
            PointF sCenter = new PointF(getSWidth()/4, getSHeight()/4);
            PointF vCenter = sourceToViewCoord(sCenter);
            float radius = (getScale() * getSWidth()) * 0.025f;

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeWidth(strokeWidth * 2);
            paint.setColor(Color.BLACK);
            canvas.drawCircle(vCenter.x, vCenter.y, radius, paint);
            paint.setStrokeWidth(strokeWidth);
            paint.setColor(Color.argb(255, 51, 214, 255));
            canvas.drawCircle(vCenter.x, vCenter.y, radius, paint);
        }
    }
}

