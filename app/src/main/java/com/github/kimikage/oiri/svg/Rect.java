/*
 * Copyright (c) 2016 Oiri Project
 *
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.svg;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import org.xml.sax.Attributes;

public class Rect extends Shape {
    public static final String NAME = "rect";
    private static final ElementFactory sFactory = new ElementFactory() {
        @Override
        public SvgElement newElement(String qName, Attributes attr) {
            return new Rect(qName, attr);
        }
    };
    private RectF mRect;
    private float mRx;
    private float mRy;

    public Rect(String qName, Attributes attr) {
        super(qName, attr);
    }

    public Rect(float x, float y, float width, float height) {
    }

    static ElementFactory getElementFactory() {
        return sFactory;
    }

    public float getX() {
        return mRect.left;
    }

    public void setX(float x) {
        float w = getWidth();
        mRect.left = x;
        mRect.right = x + w;
    }

    public float getY() {
        return mRect.top;
    }

    public void setY(float y) {
        float h = getHeight();
        mRect.top = y;
        mRect.bottom = y + h;
    }

    public float getWidth() {
        return mRect.width();
    }

    public void setWidth(float width) {
        mRect.right = mRect.left + width;
    }

    public float getHeight() {
        return mRect.height();
    }

    public void setHeight(float height) {
        mRect.bottom = mRect.top + height;
    }

    public float getRx() {
        if (mRx > getWidth() * 0.5f) {
            return getWidth() * 0.5f;
        }
        if (mRx < Float.MIN_NORMAL && mRy > 0f) {
            return mRy;
        }
        return mRx;
    }

    public void setRx(float rx) {
        mRx = rx;
    }

    public float getRy() {
        if (mRy > getHeight() * 0.5f) {
            return getHeight() * 0.5f;
        }
        if (mRy < Float.MIN_NORMAL && mRx > 0f) {
            return mRx;
        }
        return mRy;
    }

    public void setRy(float ry) {
        mRy = ry;
    }

    @Override
    protected void drawCore(Canvas canvas, Paint paint) {
        if (getWidth() < Float.MIN_NORMAL || getHeight() < Float.MIN_NORMAL) {
            return;
        }
        if (getRx() > 0f || getRy() > 0f) {
            canvas.drawRoundRect(mRect, getRx(), getRy(), paint);
        } else {
            canvas.drawRect(mRect, paint);
        }
    }

    @Override
    public void setAttributeNS(String uri, String qName, String value) {
        if (mRect == null) {
            mRect = new RectF();
        }
        if (uri == null || uri.isEmpty()) {
            switch (qName) {
                case "x":
                    setX(getCoordinate(value));
                    break;
                case "y":
                    setY(getCoordinate(value));
                    break;
                case "width":
                    setWidth(getLength(value));
                    break;
                case "height":
                    setHeight(getLength(value));
                    break;
                case "rx":
                    setRx(getLength(value));
                    break;
                case "ry":
                    setRy(getLength(value));
                    break;
            }
        }
        super.setAttributeNS(uri, qName, value);
    }

    @Override
    public String getLocalName() {
        return NAME;
    }
}
