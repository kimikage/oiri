/*
 * Copyright (c) 2016 Oiri Project
 *
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.svg;

import android.graphics.Color;

import org.w3c.dom.DOMException;
import org.w3c.dom.svg.SVGColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SvgColor implements SVGColor {
    private static final int R = 0;
    private static final int G = 1;
    private static final int B = 2;
    private static final Pattern sPatternRrggbb = Pattern.compile("^\\s*#([0-9a-fA-F]{6})\\s*$");
    private static final Pattern sPatternRgb = Pattern.compile("^\\s*#([0-9a-fA-F]{3})\\s*$");
    private int mColor;
    private float[] mRgb;
    private int mColorType = SVG_COLORTYPE_RGBCOLOR;

    protected SvgColor() {
    }

    public SvgColor(int color) {
        setIntColor(color);
    }

    public static SvgColor fromLinearRgb(float r, float g, float b) {
        SvgColor c = new SvgColor();
        c.mRgb = linearRgbToSRgb(new float[]{r, g, b});
        c.mColor = generateIntColor(c.mRgb);
        return c;
    }

    public static SvgColor fromSRgb(float r, float g, float b) {
        SvgColor c = new SvgColor();
        c.mRgb = new float[]{r, g, b};
        c.mColor = generateIntColor(c.mRgb);
        return c;
    }

    private static int generateIntColor(float[] sRgb) {
        int r8 = Math.round(sRgb[R] * 255f);
        int g8 = Math.round(sRgb[G] * 255f);
        int b8 = Math.round(sRgb[B] * 255f);
        return 0xFF000000 | (r8 << 16) | (g8 << 8) | b8;
    }

    private static float[] sRgbToLinearRgb(float[] sRgb) {
        return new float[]{
                degamma(sRgb[R]),
                degamma(sRgb[G]),
                degamma(sRgb[B])
        };
    }

    private static float[] linearRgbToSRgb(float[] linear) {
        return new float[]{
                gamma(linear[R]),
                gamma(linear[G]),
                gamma(linear[B])
        };
    }

    public static float degamma(float v) {
        return v <= 4.045e-2f ? v / 12.92f : (float) Math.pow((v + 0.055f) / 1.055f, 2.4);
    }

    public static float gamma(float v) {
        return v <= 3.1308e-3f ? v * 12.92f : (float) Math.pow(v, 1 / 2.4) * 1.055f - 0.055f;
    }

    public static SvgColor interpolateInSRgb(SvgColor c1, SvgColor c2, float rate) {
        if (rate <= 0.0f) {
            return new SvgColor(c1.getIntColor());
        }
        if (rate >= 1.0f) {
            return new SvgColor(c2.getIntColor());
        }
        float r = c1.mRgb[R] * (1.0f - rate) + c2.mRgb[R] * rate;
        float g = c1.mRgb[G] * (1.0f - rate) + c2.mRgb[G] * rate;
        float b = c1.mRgb[B] * (1.0f - rate) + c2.mRgb[B] * rate;
        return fromSRgb(r, g, b);
    }

    public static SvgColor interpolateInLinearRgb(SvgColor c1, SvgColor c2, float rate) {
        if (rate <= 0.0f) {
            return new SvgColor(c1.getIntColor());
        }
        if (rate >= 1.0f) {
            return new SvgColor(c2.getIntColor());
        }
        float[] linear1 = sRgbToLinearRgb(c1.mRgb);
        float[] linear2 = sRgbToLinearRgb(c2.mRgb);
        float r = linear1[R] * (1.0f - rate) + linear2[R] * rate;
        float g = linear1[G] * (1.0f - rate) + linear2[G] * rate;
        float b = linear1[B] * (1.0f - rate) + linear2[B] * rate;
        return fromLinearRgb(r, g, b);
    }

    // D65
    public float getY() {
        float[] linear = sRgbToLinearRgb(mRgb);
        return 0.212639f * linear[R] + 0.715169f * linear[G] + 0.072192f * linear[B];
    }

    public float getLabLightness() {
        float v = getY();
        return v <= 8.856451679e-3f ? 903.2962963f * v : 116f * (float) Math.pow(v, 1f / 3f) - 16f;
    }

    public int getIntColor() {
        return mColor;
    }

    public void setIntColor(int color) {
        mColor = color;
        mRgb = new float[]{
                ((color >> 16) & 0xFF) / 255f,
                ((color >> 8) & 0xFF) / 255f,
                (color & 0xFF) / 255f
        };
    }

    @Override
    public void setColor(short colorType, String rgbColor, String iccColor) {
        setRGBColor(rgbColor);
        mColorType = colorType;
    }

    @Override
    public short getColorType() {
        return (short) mColorType;
    }

    @Override
    public void setRGBColor(String rgbColor) {
        Matcher m = sPatternRrggbb.matcher(rgbColor);
        if (m.matches()) {
            setIntColor(Integer.parseInt(m.group(1), 16) | 0xFF000000);
            return;
        }
        m = sPatternRgb.matcher(rgbColor);
        if (m.matches()) {
            int rgb = Integer.parseInt(m.group(1), 16);
            int rrggbb = (rgb & 0xF00) << 12 | (rgb & 0xF00) << 8 |
                    (rgb & 0x0F0) << 8 | (rgb & 0x0F0) << 4 |
                    (rgb & 0x00F) << 4 | (rgb & 0x00F);
            setIntColor(rrggbb | 0xFF000000);
            return;
        }
        setIntColor(Color.parseColor(rgbColor));
    }

    @Override
    public void setRGBColorICCColor(String rgbColor, String iccColor) {
        setRGBColor(rgbColor);
    }

    @Override
    public String getCssText() {
        return null;
    }

    @Override
    public void setCssText(String cssText) throws DOMException {

    }

    @Override
    public short getCssValueType() {
        return CSS_CUSTOM;
    }
}
