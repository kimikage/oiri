/*
 * Copyright (c) 2016 Oiri Project
 *
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.svg;

import android.graphics.Canvas;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGElement;
import org.xml.sax.Attributes;

import java.util.List;
import java.util.Locale;

public abstract class SvgElement extends Unknown implements SVGElement {
    protected String mXmlBase;

    public SvgElement() {
    }

    public SvgElement(String qName, Attributes attr) {
        super(SvgParser.NS_SVG, qName, attr);
    }

    protected static float getLength(String value) {
        return SvgParser.parseLength(value);
    }

    protected static float getCoordinate(String value) {
        return SvgParser.parseCoordinate(value);
    }

    protected static List<Float> getPoints(String value) {
        return SvgParser.parsePoints(value);
    }

    protected static float[] toArray(List<Float> list) {
        int n = list.size();
        float[] array = new float[n];
        for (int i = 0; i < n; ++i) {
            array[i] = list.get(i);
        }
        return array;
    }

    protected static String toString(float value) {
        return String.format(Locale.US, "%.8g", value);
    }

    @Override
    public Node getParentNode() {
        return mParent;
    }

    @Override
    public NodeList getChildNodes() {
        return mChildren;
    }

    @Override
    public boolean hasChildNodes() {
        return mChildren.getLength() > 0;
    }

    @Override
    public Node cloneNode(boolean deep) {
        return null;
    }

    @Override
    public void normalize() {

    }

    @Override
    public boolean isSupported(String feature, String version) {
        return false;
    }

    @Override
    public String getNamespaceURI() {
        return SvgParser.NS_SVG;
    }

    @Override
    public SVGElement getViewportElement() {
        return null;
    }

    @Override
    public String getId() {
        return getAttributeNS(null, "id");
    }

    @Override
    public void setId(String id) {
        setAttributeNS(null, "id", id);
    }

    @Override
    public String getXMLbase() {
        return mXmlBase;
    }

    @Override
    public void setXMLbase(String XMLbase) {
        mXmlBase = XMLbase;
    }

    public void draw(Canvas canvas) {

    }
}
