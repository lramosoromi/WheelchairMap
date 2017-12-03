package com.rolithunderbird.wheelchairmap.javaClasses;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

/**
 * Created by lucasramosoromi on 10/17/17.
 */
public class MyImageView extends SubsamplingScaleImageView {

    private int strokeWidth;
    private PointF locationOnMap;
    private PointF pathFromMap;
    private Boolean paintLocation = false;
    private Boolean paintPath = false;

    public MyImageView(Context context) {
        this(context, null);
    }

    public MyImageView(Context context, AttributeSet attr) {
        super(context, attr);
        initialise();
    }

    public void setLocationOnMap(PointF pointF) {
        this.locationOnMap = pointF;
    }

    public void setPathFromMap(PointF pointF) {
        this.pathFromMap = pointF;
    }

    public void setPaintLocation(Boolean paintLocation) {
        this.paintLocation = paintLocation;
    }

    public void setPaintPath(Boolean paintPath) {
        this.paintPath = paintPath;
    }

    private void initialise() {
        float density = getResources().getDisplayMetrics().densityDpi;
        strokeWidth = (int)(density/60f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Don't draw pin before image is ready so it doesn't move around during setup.
        if (!isReady()) {
            return;
        }

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        if (paintLocation) {
            drawLocation(paint, canvas);
        }

        if (paintPath) {
            drawDirection(paint, canvas);
        }
    }

    /**
     * Draw the location of the variable locationOnMap on the map
     * @param paint
     * @param canvas
     */
    private void drawLocation (Paint paint, Canvas canvas) {
        //Position of the Circle
        PointF sCenter = new PointF(getSWidth() * locationOnMap.x, getSHeight() * locationOnMap.y);
        PointF vCenter = sourceToViewCoord(sCenter);
        float radius = (getScale() * getSWidth()) * 0.025f;

        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(Color.argb(255, 51, 214, 255));
        canvas.drawCircle(vCenter.x, vCenter.y, radius, paint);
    }

    /**
     * Draw the path for the user on the map
     * @param paint
     * @param canvas
     */
    private void drawDirection (Paint paint, Canvas canvas) {
        Path vPath = new Path();
        PointF vPrev = sourceToViewCoord(getSWidth() * pathFromMap.x, getSHeight() * pathFromMap.y);
        vPath.moveTo(vPrev.x, vPrev.y);
        vPath.lineTo(vPrev.x, vPrev.y * 90);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(Color.argb(255, 236, 31, 31));
        canvas.drawPath(vPath, paint);
    }
}

