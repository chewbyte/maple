package com.chewbyte.geogab.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.chewbyte.geogab.common.Calc;

/**
 * Created by Chris on 27/08/2016.
 */
class TriangleShapeView extends View {

    public TriangleShapeView(Context context) {
        super(context);
    }

    public TriangleShapeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TriangleShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path path = new Path();

        float px336 = Calc.convertDpToPixel(252,getContext());
        float px168 = Calc.convertDpToPixel(126,getContext());
        float px144 = Calc.convertDpToPixel(108,getContext());
        float px96 = Calc.convertDpToPixel(72,getContext());
        float px48 = Calc.convertDpToPixel(36,getContext());
        float px102 = Calc.convertDpToPixel(90,getContext());

        path.moveTo(px48,0);
        path.lineTo(px336,0);
        path.lineTo(px336,px96);
        path.lineTo(px168,px96);
        path.lineTo(px168,px102);
        path.lineTo(px144,px96);
        path.lineTo(px48,px96);
        path.lineTo(px48,0);
        path.close();

        Paint p = new Paint();
        p.setColor( Color.LTGRAY);
        canvas.drawPath(path, p);

        float px1 = Calc.convertDpToPixel(1.5f,getContext());

        Path path2 = new Path();

        path2.moveTo(px48+px1,px1);
        path2.lineTo(px336-px1,px1);
        path2.lineTo(px336-px1,px96-px1);
        path2.lineTo(px168-px1,px96-px1);
        path2.lineTo(px168-px1,px102-px1*3);
        path2.lineTo(px144+px1,px96-px1);
        path2.lineTo(px48+px1,px96-px1);
        path2.lineTo(px48+px1,0);
        path2.close();

        Paint p2 = new Paint();
        p2.setColor( Color.WHITE );
        canvas.drawPath(path2, p2);
    }
}