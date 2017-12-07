package edu.ateneo.cie199.finalproject;


import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

/**
 * Created by John, Duke and JV on 11/7/2017.
 * This class is mainly used to change the typeface of the dropdown menu of the Manager Activity
 */

public class CustomTypefaceSpan extends TypefaceSpan {

    private final Typeface newType;

    /**
     * Initialize the used extension
     * @param family
     * @param type
     */
    public CustomTypefaceSpan(String family, Typeface type) {
        super(family);
        newType = type;
    }

    /**
     * partly  used to update and apply the custom type face assigned
     * @param ds
     */
    @Override
    public void updateDrawState(TextPaint ds) {
        applyCustomTypeFace(ds, newType);
    }

    /**
     * partly used to update and apply the custom type face assigned
     * @param paint
     */
    @Override
    public void updateMeasureState(TextPaint paint) {
        applyCustomTypeFace(paint, newType);
    }

    /**
     * update and apply the custom type face assigned
     * @param paint
     * @param tf
     */
    private static void applyCustomTypeFace(Paint paint, Typeface tf) {
        int oldStyle;
        Typeface old = paint.getTypeface();
        if (old == null) {
            oldStyle = 0;
        } else {
            oldStyle = old.getStyle();
        }

        int fake = oldStyle & ~tf.getStyle();
        if ((fake & Typeface.BOLD) != 0) {
            paint.setFakeBoldText(true);
        }

        if ((fake & Typeface.ITALIC) != 0) {
            paint.setTextSkewX(-0.25f);
        }

        paint.setTypeface(tf);
    }
}
