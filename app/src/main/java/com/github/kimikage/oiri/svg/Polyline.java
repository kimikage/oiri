/*
 * Copyright (c) 2016 Oiri Project
 *
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.svg;

import android.graphics.Canvas;
import android.graphics.Paint;

import org.w3c.dom.svg.SVGPoint;
import org.xml.sax.Attributes;

public class Polyline extends Shape {
    public static final String NAME = "polyline";
    private static final ElementFactory sFactory = new ElementFactory() {
        @Override
        public SvgElement newElement(String qName, Attributes attr) {
            return new Polyline(qName, attr);
        }
    };
    private static final float sZ = 4.0f;
    private float[] mPoints;
    private android.graphics.Path mPath;

    public Polyline(String qName, Attributes attr) {
        super(qName, attr);
    }

    static ElementFactory getElementFactory() {
        return sFactory;
    }

    @Override
    public void drawCore(Canvas canvas, Paint paint) {
        if (mPath == null) {
            if (mPoints == null || mPoints.length < 4) {
                return;
            }
            mPath = new android.graphics.Path();
            mPath.moveTo(mPoints[0] * sZ, mPoints[1] * sZ);
            int n = mPoints.length / 2;
            for (int i = 1; i < n; ++i) {
                mPath.lineTo(mPoints[i * 2] * sZ, mPoints[i * 2 + 1] * sZ);
            }
        }
        canvas.save();
        canvas.scale(1.0f / sZ, 1.0f / sZ);
        float w = paint.getStrokeWidth();
        paint.setStrokeWidth(Math.min(w, 1f) * sZ);
        canvas.drawPath(mPath, paint);
        paint.setStrokeWidth(w);
        canvas.restore();
    }

    @Override
    public void setAttributeNS(String uri, String qName, String value) {
        if (uri == null || uri.isEmpty()) {
            switch (qName) {
                case "points":
                    mPoints = toArray(getPoints(value));
                    break;
            }
        }
        super.setAttributeNS(uri, qName, value);
    }

    @Override
    public String getLocalName() {
        return NAME;
    }

    public SVGPoint getPoint(long index) {
        return new Point(mPoints[(int) index * 2], mPoints[(int) index * 2 + 1]);
    }
}