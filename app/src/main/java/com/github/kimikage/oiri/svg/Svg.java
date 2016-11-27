/*
 * Copyright (c) 2016 Oiri Project
 *
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.svg;

import android.graphics.Canvas;

import org.w3c.dom.Node;
import org.xml.sax.Attributes;

public class Svg extends SvgElement {
    public static final String NAME = "svg";
    private static final ElementFactory sFactory = new ElementFactory() {
        @Override
        public SvgElement newElement(String qName, Attributes attr) {
            Svg svg = new Svg(qName, attr);
            return svg;
        }
    };

    protected Svg(String qName, Attributes attr) {
        super(qName, attr);
    }

    static ElementFactory getElementFactory() {
        return sFactory;
    }

    @Override
    public String getLocalName() {
        return NAME;
    }

    @Override
    public void setAttribute(String name, String value) {
        super.setAttribute(name, value);
    }

    @Override
    public void draw(Canvas canvas) {
        for (int i = 0; i < mChildren.getLength(); ++i) {
            Node n = mChildren.item(i);
            if (n instanceof SvgElement) {
                ((SvgElement) n).draw(canvas);
            }
        }
    }
}
