/*
 * Copyright (c) 2016 Oiri Project
 *
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.svg;

import android.graphics.Canvas;
import android.graphics.Paint;

import org.w3c.dom.css.CSSValue;
import org.w3c.dom.svg.SVGPaint;
import org.xml.sax.Attributes;

public abstract class Shape extends SvgElement {
    private static final Paint sDefaultStrokePaint;
    private static final Paint sDefaultFillPaint;

    static {
        sDefaultStrokePaint = new Paint();
        sDefaultFillPaint = new Paint();
        try {
            sDefaultStrokePaint.setColor(0xFF000000);
            sDefaultStrokePaint.setStyle(Paint.Style.STROKE);

            sDefaultFillPaint.setColor(0xFF000000);
            sDefaultFillPaint.setStyle(Paint.Style.FILL);

        } catch (UnsatisfiedLinkError e) {
        }
    }

    protected Transform mTransform;
    protected SvgStyle mStyle;
    protected Paint mFillPaint;
    protected Paint mStrokePaint;

    public Shape(String qName, Attributes attr) {
        super(qName, attr);
    }

    protected Shape() {
        super();
    }

    public Paint getStrokePaint() {
        if (mStrokePaint != null) {
            return mStrokePaint;
        }
        if (mStyle != null && mStyle.getStrokePaint() != null) {
            SvgPaint p = mStyle.getStrokePaint();
            if (p.getCssValueType() != CSSValue.CSS_INHERIT) {
                if (p.getPaintType() == SVGPaint.SVG_PAINTTYPE_RGBCOLOR) {
                    mStrokePaint = new Paint();
                    mStrokePaint.setColor(p.getIntColor());
                    mStrokePaint.setStyle(Paint.Style.STROKE);
                    mStrokePaint.setStrokeWidth(1f);
                    mStrokePaint.setAntiAlias(true);
                    return mStrokePaint;
                }
            }
        }
        if (mParent instanceof Shape) {
            return ((Shape) mParent).getStrokePaint();
        }
        return sDefaultStrokePaint;
    }

    public Paint getFillPaint() {
        if (mFillPaint != null) {
            return mFillPaint;
        }
        if (mStyle != null && mStyle.getFillPaint() != null) {
            SvgPaint p = mStyle.getFillPaint();
            if (p.getCssValueType() != CSSValue.CSS_INHERIT) {
                if (p.getPaintType() == SVGPaint.SVG_PAINTTYPE_RGBCOLOR) {
                    mFillPaint = new Paint();
                    mFillPaint.setColor(p.getIntColor());
                    mFillPaint.setStyle(
                            mStyle.isUnited() ? Paint.Style.FILL_AND_STROKE : Paint.Style.FILL);
                    return mFillPaint;
                }
            }
        }
        if (mParent instanceof Shape) {
            return ((Shape) mParent).getFillPaint();
        }
        return sDefaultFillPaint;
    }


    public final void draw(Canvas canvas) {
        if (mTransform != null) {
            canvas.save();
            canvas.concat(mTransform.getAndroidMatrix());
        }

        if (mStyle != null) {
            if (mStyle.hasFill()) {
                Paint fillPaint = getFillPaint();
                if (fillPaint != null) {
                    drawCore(canvas, fillPaint);
                }
            }
            if (mStyle.hasStroke() && !mStyle.isUnited()) {
                drawCore(canvas, getStrokePaint());
            }
        } else {
            drawCore(canvas, getStrokePaint());
        }

        if (mTransform != null) {
            canvas.restore();
        }
    }

    protected abstract void drawCore(Canvas canvas, Paint paint);

    public void setTransform(String list) {
        mTransform = new Transform(list);
    }

    @Override
    public void setAttributeNS(String uri, String qName, String value) {
        if (uri == null || uri.isEmpty()) {
            switch (qName) {
                case "transform":
                    setTransform(value);
                    break;
                default:
                    if (SvgStyle.isStyleProperty(qName)) {
                        if (mStyle == null) {
                            mStyle = new SvgStyle();
                        }
                        mStyle.setProperty(qName, value, "");
                    }
                    break;
            }

        }
        super.setAttributeNS(uri, qName, value);
    }
}
