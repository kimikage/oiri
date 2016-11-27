/*
 * Copyright (c) 2016 Oiri Project
 *
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.svg;

import org.w3c.dom.DOMException;
import org.w3c.dom.svg.SVGPaint;

import java.util.HashMap;
import java.util.Map;

public class SvgStyle extends StyleDeclaration {
    private static final Map<String, PropertySetter> sProperties;

    static {
        sProperties = new HashMap<>();
        sProperties.put("fill", new PropertySetter() {
            @Override
            public void set(SvgStyle style, String value) {
                style.mFill = new SvgPaint(value);
            }
        });
        sProperties.put("stroke", new PropertySetter() {
            @Override
            public void set(SvgStyle style, String value) {
                style.mStroke = new SvgPaint(value);
            }
        });

    }

    private SvgPaint mStroke;
    private float mStrokeOpacity = 1.0f;
    private float mStrokeWidth;
    private SvgPaint mFill;
    private float mFillOpacity = 1.0f;

    public SvgStyle() {

    }

    public SvgStyle(String cssText) {
        setCssText(cssText);
    }

    public static boolean isStyleProperty(String propertyName) {
        return sProperties.containsKey(propertyName);
    }

    public SvgPaint getStrokePaint() {
        return mStroke;
    }

    public void setStrokePaint(SvgPaint stroke) {
        mStroke = stroke;
    }

    public int getStrokeColor() {
        return mStroke.getIntColor();
    }

    public float getStrokeWidth() {
        return mStrokeWidth;
    }

    public SvgPaint getFillPaint() {
        return mFill;
    }

    public void setFillPaint(SvgPaint fill) {
        mFill = fill;
    }

    public int getFillColor() {
        return applyOppacity(mFill.getIntColor(), mFillOpacity);
    }

    public boolean isUnited() {
        return false;
    }

    public boolean hasFill() {
        if (mFill == null) {
            return false;
        }
        if (mFill.getPaintType() == SVGPaint.SVG_PAINTTYPE_NONE ||
                mFill.getPaintType() == SVGPaint.SVG_PAINTTYPE_URI_NONE) {
            return false;
        }
        if (mFillOpacity <= Float.MIN_NORMAL) {
            return false;
        }
        return true;
    }

    public boolean hasStroke() {
        if (mStroke == null) {
            return false;
        }
        if (mStroke.getPaintType() == SVGPaint.SVG_PAINTTYPE_NONE ||
                mStroke.getPaintType() == SVGPaint.SVG_PAINTTYPE_URI_NONE) {
            return false;
        }
        if (mStrokeOpacity <= Float.MIN_NORMAL) {
            return false;
        }
        return true;
    }


    @Override
    public void setProperty(String propertyName, String value, String priority)
            throws DOMException {
        if (sProperties.containsKey(propertyName)) {
            sProperties.get(propertyName).set(this, value);
        }
        super.setProperty(propertyName, value, priority);
    }

    private int applyOppacity(int color, float opacity) {
        float a = ((color & 0xFF000000) >> 24) * opacity;
        return (Math.round(a) << 24) | (color & 0x00FFFFFF);
    }

    private interface PropertySetter {
        public void set(SvgStyle style, String value);
    }

}
