/*
 * Copyright (c) 2016 Oiri Project
 *
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.svg;

import android.graphics.Canvas;
import android.graphics.Paint;

import org.xml.sax.Attributes;

public class Line extends Shape {
    public static final String NAME = "line";
    private static final ElementFactory sFactory = new ElementFactory() {
        @Override
        public SvgElement newElement(String qName, Attributes attr) {
            return new Line(qName, attr);
        }
    };
    private float mX1;
    private float mY1;
    private float mX2;
    private float mY2;

    public Line(String qName, Attributes attr) {
        super(qName, attr);
    }

    public Line(float x1, float y1, float x2, float y2) {
        super(NAME, null);
        setAttributeNS(null, "x1", toString(x1));
        setAttributeNS(null, "y1", toString(y1));
        setAttributeNS(null, "x2", toString(x2));
        setAttributeNS(null, "y2", toString(y2));
    }

    static ElementFactory getElementFactory() {
        return sFactory;
    }

    public float getX1() {
        return mX1;
    }

    public float getY1() {
        return mY1;
    }

    public float getX2() {
        return mX2;
    }

    public float getY2() {
        return mY2;
    }

    @Override
    public void drawCore(Canvas canvas, Paint paint) {
        canvas.drawLine(mX1, mY1, mX2, mY2, paint);
    }

    @Override
    public void setAttributeNS(String uri, String qName, String value) {
        if (uri == null || uri.isEmpty()) {
            switch (qName) {
                case "x1":
                    mX1 = getCoordinate(value);
                    break;
                case "y1":
                    mY1 = getCoordinate(value);
                    break;
                case "x2":
                    mX2 = getCoordinate(value);
                    break;
                case "y2":
                    mY2 = getCoordinate(value);
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
