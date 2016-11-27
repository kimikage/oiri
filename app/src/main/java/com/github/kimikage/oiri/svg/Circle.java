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

public class Circle extends Shape {
    public static final String NAME = "circle";
    private static final ElementFactory sFactory = new ElementFactory() {
        @Override
        public SvgElement newElement(String qName, Attributes attr) {
            return new Circle(qName, attr);
        }
    };
    private float mCx;
    private float mCy;
    private float mR;

    public Circle(String qName, Attributes attr) {
        super(qName, attr);
    }

    public Circle(float cx, float cy, float r) {
        super(NAME, null);
        setAttributeNS(null, "cx", toString(cx));
        setAttributeNS(null, "cy", toString(cy));
        setAttributeNS(null, "r", toString(r));
    }

    static ElementFactory getElementFactory() {
        return sFactory;
    }

    @Override
    public void drawCore(Canvas canvas, Paint paint) {
        if (mR < Float.MIN_NORMAL) {
            return;
        }
        canvas.drawCircle(mCx, mCy, mR, paint);
    }

    @Override
    public void setAttributeNS(String uri, String qName, String value) {
        if (uri == null || uri.isEmpty()) {
            switch (qName) {
                case "cx":
                    mCx = getCoordinate(value);
                    break;
                case "cy":
                    mCy = getCoordinate(value);
                    break;
                case "r":
                    mR = getLength(value);
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
