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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Path extends Shape {
    public static final String NAME = "path";
    public static final short MOVE_TO = 77;
    public static final short LINE_TO = 76;
    public static final short CURVE_TO = 67;
    public static final short QUAD_TO = 81;
    public static final short ARC_TO = 65;
    public static final short CLOSE = 90;
    private static final float sZ = 4.0f;

    private static final ElementFactory sFactory = new ElementFactory() {
        @Override
        public SvgElement newElement(String qName, Attributes attr) {
            return new Path(qName, attr);
        }
    };

    private static final Pattern sPatternCommand = Pattern.compile(
            "^\\s*([MmZzLlHhVvCcSsQqTtAa])([\\s,+-.\\dEe]*)"
    );
    private static final Pattern sPatternParam = Pattern.compile(
            "\\s*([-+]?(?:[0-9]+(?:\\.[0-9]*)?|\\.[0-9]+)(?:[eE][-+]?[0-9]+)?)"
    );
    private String mD;
    private android.graphics.Path mPath;
    private float mX;
    private float mY;
    private float mSx;
    private float mSy;
    private List<float[]> mSegments = new ArrayList<>();

    public Path(String qName, Attributes attr) {
        super(qName, attr);
        mPath = new android.graphics.Path();
        parse(mD);
    }

    public Path(String d, android.graphics.Path path) {
        setAttributeNS(null, "d", d);
        mPath = path;
        parse(mD);
    }

    public Path(String d) {
        this(d, new android.graphics.Path());
    }

    static ElementFactory getElementFactory() {
        return sFactory;
    }

    public String getD() {
        return mD;
    }

    @Override
    public void drawCore(Canvas canvas, Paint paint) {
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
                case "d":
                    mD = value;
                    break;
            }
        }
        super.setAttributeNS(uri, qName, value);
    }

    private void parse(String d) {
        mPath.reset();
        for (; ; ) {
            Matcher m = sPatternCommand.matcher(d);
            if (!m.find()) {
                break;
            }
            String command = m.group(1);
            String params = m.group(2);
            d = d.substring(m.end());

            List<Float> p = new ArrayList<>();
            Matcher mp = sPatternParam.matcher(params);
            while (mp.find()) {
                p.add(Float.parseFloat(mp.group(1)));
            }

            boolean relative = true;
            switch (command) {
                case "M":
                    relative = false;
                case "m":
                    moveTo(p, relative);
                    break;

                case "Z":
                case "z":
                    close(p);
                    break;

                case "L":
                    relative = false;
                case "l":
                    lineTo(p, relative);
                    break;

                case "H":
                    relative = false;
                case "h":
                    hLineTo(p, relative);
                    break;

                case "V":
                    relative = false;
                case "v":
                    vLineTo(p, relative);
                    break;

                case "C":
                    relative = false;
                case "c":
                    curveTo(p, relative);
                    break;

                case "A":
                    relative = false;
                case "a":
                    arcTo(p, relative);
                    break;
            }
        }
    }

    public short getSegment(long cmdIndex) {
        return (short) (mSegments.get((int) cmdIndex)[0]);
    }

    public float getSegmentParameter(long cmdIndex, long paramIndex) {
        return mSegments.get((int) cmdIndex)[1 + (int) paramIndex];
    }

    public void moveTo(float x, float y) {
        mSegments.add(new float[]{MOVE_TO, x, y});
        mPath.moveTo(x * sZ, y * sZ);
        mX = x;
        mY = y;
        mSx = x;
        mSy = y;
    }

    public void close() {
        mSegments.add(new float[]{CLOSE});
        mPath.close();
        mX = mSx;
        mY = mSy;
    }

    public void lineTo(float x, float y) {
        mSegments.add(new float[]{LINE_TO, x, y});
        mPath.lineTo(x * sZ, y * sZ);
        mX = x;
        mY = y;
    }

    public void curveTo(float x1, float y1, float x2, float y2, float x3, float y3) {
        mSegments.add(new float[]{CURVE_TO, x1, y1, x2, y2, x3, y3});
        mPath.cubicTo(x1 * sZ, y1 * sZ, x2 * sZ, y2 * sZ, x3 * sZ, y3 * sZ);
        mX = x3;
        mY = y3;
    }

    public void arcTo(float rx, float ry,
                      float rotation, float largeArc, float sweep, float x, float y) {
        mSegments.add(new float[]{ARC_TO, rx, ry, rotation, largeArc, sweep, x, y});

        boolean fA = largeArc != 0f;
        boolean fS = sweep != 0f;

        if (Math.abs(rotation) < Float.MIN_NORMAL) {
            float x1p = 0.5f * (mX - x);
            float y1p = 0.5f * (mY - y);
            float rx2 = rx * rx;
            float ry2 = ry * ry;
            float x1p2 = x1p * x1p;
            float y1p2 = y1p * y1p;
            double r = Math.sqrt((rx2 * ry2 - rx2 * y1p2 - ry2 * x1p2) / (rx2 * y1p2 + ry2 * x1p2));
            double cxp = fA ^ fS ? r * rx * y1p / ry : -r * rx * y1p / ry;
            double cyp = fA ^ fS ? -r * ry * x1p / rx : r * ry * x1p / rx;
            float cx = (float) cxp + 0.5f * (mX + x);
            float cy = (float) cyp + 0.5f * (mY + y);
            float start = (float) Math.atan2((mY - cy) / ry, (mX - cx) / rx);
            float end = (float) Math.atan2((y - cy) / ry, (x - cx) / rx);
            double delta = (end - start) % (2 * Math.PI);
            if (!fS && delta > 0) {
                delta -= 2 * Math.PI;
            } else if (fS && delta < 0) {
                delta += 2 * Math.PI;
            }
            RectF oval = new RectF((cx - rx) * sZ, (cy - ry) * sZ, (cx + rx) * sZ, (cy + ry) * sZ);
            mPath.arcTo(oval, (float) Math.toDegrees(start), (float) Math.toDegrees(delta));
            mPath.lineTo(x * sZ, y * sZ);
        } else {
            // not supported
        }

        mX = x;
        mY = y;
    }

    private void moveTo(List<Float> params, boolean relative) {
        int n = params.size();
        if (n < 2) {
            return;
        }
        if (n % 2 != 0) {
            return;
        }
        float mx = params.get(0) + (relative ? mX : 0.0f);
        float my = params.get(1) + (relative ? mY : 0.0f);
        moveTo(mx, my);

        for (int i = 2; i < n; ) {
            float x = params.get(i++) + (relative ? mX : 0.0f);
            float y = params.get(i++) + (relative ? mY : 0.0f);
            lineTo(x, y);
        }
    }

    private void close(List<Float> params) {
        if (!params.isEmpty()) {
            return;
        }
        close();
    }

    private void lineTo(List<Float> params, boolean relative) {
        int n = params.size();
        if (n < 2) {
            return;
        }
        if (n % 2 != 0) {
            return;
        }
        for (int i = 0; i < n; ) {
            float x = params.get(i++) + (relative ? mX : 0.0f);
            float y = params.get(i++) + (relative ? mY : 0.0f);
            lineTo(x, y);
        }
    }

    private void hLineTo(List<Float> params, boolean relative) {
        int n = params.size();
        for (int i = 0; i < n; ++i) {
            float x = params.get(i) + (relative ? mX : 0.0f);
            lineTo(x, mY);
        }
    }

    private void vLineTo(List<Float> params, boolean relative) {
        int n = params.size();
        for (int i = 0; i < n; ++i) {
            float y = params.get(i) + (relative ? mY : 0.0f);
            lineTo(mX, y);
        }
    }

    private void curveTo(List<Float> params, boolean relative) {
        int n = params.size();
        if (n < 6) {
            return;
        }
        if (n % 6 != 0) {
            return;
        }
        for (int i = 0; i < n; ) {
            float x1 = params.get(i++) + (relative ? mX : 0.0f);
            float y1 = params.get(i++) + (relative ? mY : 0.0f);
            float x2 = params.get(i++) + (relative ? mX : 0.0f);
            float y2 = params.get(i++) + (relative ? mY : 0.0f);
            float x3 = params.get(i++) + (relative ? mX : 0.0f);
            float y3 = params.get(i++) + (relative ? mY : 0.0f);
            curveTo(x1, y1, x2, y2, x3, y3);
        }
    }

    private void arcTo(List<Float> params, boolean relative) {
        int n = params.size();
        if (n < 7) {
            return;
        }
        if (n % 7 != 0) {
            return;
        }
        for (int i = 0; i < n; ) {
            float rx = params.get(i++);
            float ry = params.get(i++);
            float rot = params.get(i++);
            float fA = params.get(i++);
            float fS = params.get(i++);
            float x = params.get(i++) + (relative ? mX : 0.0f);
            float y = params.get(i++) + (relative ? mY : 0.0f);
            arcTo(rx, ry, rot, fA, fS, x, y);
        }
    }

    @Override
    public String getLocalName() {
        return NAME;
    }
}
