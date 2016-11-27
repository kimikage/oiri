/*
 * Copyright (c) 2016 Oiri Project
 *
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.svg;

import android.graphics.Canvas;
import android.graphics.Paint;

import org.w3c.dom.Node;
import org.xml.sax.Attributes;


public class Group extends Shape {
    public static final String NAME = "g";
    private static final ElementFactory sFactory = new ElementFactory() {
        @Override
        public SvgElement newElement(String qName, Attributes attr) {
            Group g = new Group(qName, attr);
            return g;
        }
    };

    public Group(String qName, Attributes attr) {
        super(qName, attr);
    }

    static ElementFactory getElementFactory() {
        return sFactory;
    }

    @Override
    public void drawCore(Canvas canvas, Paint paint) {
        for (Node node : mChildren.getList()) {
            if (node instanceof Shape) {
                Shape s = (Shape) node;
                s.draw(canvas);
            }
        }
    }

    @Override
    public String getLocalName() {
        return NAME;
    }
}
