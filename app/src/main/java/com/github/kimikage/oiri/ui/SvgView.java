/*
 * Copyright (c) 2016 Oiri Project
 *
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

import com.github.kimikage.oiri.svg.SvgDocument;
import com.github.kimikage.oiri.svg.SvgParser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.xml.transform.TransformerException;

public class SvgView extends View {
    private static final String KEY_PARENT = "parent";
    private static final String KEY_SVG = "svg";
    private SvgDocument mSvg;

    public SvgView(Context context) {
        super(context);
        init(null, 0);
    }

    public SvgView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public SvgView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        if (isInEditMode() || true) {

            String xml = "<svg xmlns='http://www.w3.org/2000/svg'>\n"
                    + "  <rect width='192' height='192' fill='#F5F5F5'/>\n"
                    + "  <g transform='translate(96,96)'>\n"
                    + "    <circle r='38' fill='#acb4ee' cy='52'/>\n"
                    + "    <circle r='38' fill='#b8ecca' cx='46' cy='-26'/>\n"
                    + "    <circle r='38' fill='#ffdaa6' cx='-46' cy='-26'/>\n"
                    + "    <circle r='40' fill='#99f4f8' cy='-52'/>\n"
                    + "    <circle r='40' fill='#f8adb5' cx='46' cy='26'/>\n"
                    + "    <circle r='40' fill='#f2f8ab' cx='-46' cy='26'/>\n"
                    + "    <circle r='44' fill='#f7f7f7'/>\n"
                    + "  </g>\n"
                    + "  <rect y='192' width='192' height='48' fill='#666'/>\n"
                    + "  <g fill='none' stroke='#fff'>\n"
                    + "    <rect x='68' y='200' width='16' height='24' rx='8' ry='11'/>\n"
                    + "    <circle cx='96' cy='202' r='1'/>\n"
                    + "    <polyline points='-2,0 0,0 0,16' transform='translate(96,208)'/>\n"
                    + "    <line x1='108' y1='208' x2='108' y2='224'/>\n"
                    + "    <path d='m108,212 c2,-4 4,-6 8,-4'/>\n"
                    + "    <path d='m0,-7a1,1 0 0,0 0,2 1,1 0 0,0 0-2zM-2,0h2v16' transform='translate(124,208)'/>\n"
                    + "  </g>\n"
                    + "</svg>";
            SvgParser parser = new SvgParser();

            try {
                parser.setInput(new ByteArrayInputStream(xml.getBytes("utf-8")));
                mSvg = parser.parse();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mSvg == null) {
            return;
        }
        canvas.save();
        mSvg.draw(canvas);
        canvas.restore();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle b = new Bundle();
        b.putParcelable(KEY_PARENT, super.onSaveInstanceState());
        ByteArrayOutputStream xml = new ByteArrayOutputStream();
        if (mSvg != null) {
            try {
                mSvg.writeXml(xml);
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        }
        b.putByteArray(KEY_SVG, xml.toByteArray());
        return b;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {

        if (!(state instanceof Bundle)) {
            return;
        }

        Bundle b = (Bundle) state;

        super.onRestoreInstanceState(b.getParcelable(KEY_PARENT));
        ByteArrayInputStream xml = new ByteArrayInputStream(b.getByteArray(KEY_SVG));

        SvgParser parser = new SvgParser();
        mSvg = null;

        try {
            parser.setInput(xml);
            mSvg = parser.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSvg(SvgDocument svg) {
        mSvg = svg;
    }

}
