/*
 * Copyright (c) 2016 Oiri Project
 *
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.svg;

import android.graphics.PointF;

import org.w3c.dom.svg.SVGMatrix;
import org.w3c.dom.svg.SVGPoint;

public class Point implements SVGPoint {
    private float mX;
    private float mY;

    public Point(float x, float y) {
        mX = x;
        mY = y;
    }

    @Override
    public float getX() {
        return mX;
    }

    @Override
    public void setX(float x) {
        mX = x;
    }

    @Override
    public float getY() {
        return mY;
    }

    @Override
    public void setY(float y) {
        mY = y;
    }

    @Override
    public SVGPoint matrixTransform(SVGMatrix matrix) {
        return new Point(matrix.getA() * mX + matrix.getB() * mY + matrix.getC(),
                matrix.getD() * mX + matrix.getE() * mY + matrix.getF());
    }

    @Override
    public String toString() {
        return "{ " + mX + ", " + mY + " }";
    }

    public void set(float x, float y) {
        mX = x;
        mY = y;
    }

    public PointF toPointF() {
        return new PointF(mX, mY);
    }
}
