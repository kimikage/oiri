/*
 * Copyright (c) 2016 Oiri Project
 *
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.svg;

import org.w3c.dom.svg.SVGPaint;

public class SvgPaint extends SvgColor implements SVGPaint {
    private int mPaintType;
    private String mUri;

    public SvgPaint(String paint) {
        switch (paint) {
            case "none":
                mPaintType = SVG_PAINTTYPE_NONE;
                return;
            case "currentColor":
                mPaintType = SVG_PAINTTYPE_CURRENTCOLOR;
                return;
            case "inherit":
                mPaintType = -1;
                return;
        }
        setRGBColor(paint);
        if (getColorType() == SVG_COLORTYPE_RGBCOLOR) {
            mPaintType = SVG_PAINTTYPE_RGBCOLOR;
        }
    }

    @Override
    public short getPaintType() {
        return (short) mPaintType;
    }

    @Override
    public String getUri() {
        return mUri;
    }

    @Override
    public void setUri(String uri) {
        mUri = uri;
    }

    @Override
    public void setPaint(short paintType, String uri, String rgbColor, String iccColor) {
        mPaintType = paintType;
        switch (paintType) {
            case SVG_PAINTTYPE_NONE:
            case SVG_PAINTTYPE_URI_NONE:
                break;
            case SVG_COLORTYPE_RGBCOLOR:
                setRGBColor(rgbColor);
                break;
        }
    }

    @Override
    public short getCssValueType() {
        return mPaintType < 0 ? CSS_INHERIT : CSS_CUSTOM;
    }
}
