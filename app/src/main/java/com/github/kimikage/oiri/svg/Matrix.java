/*
 * Copyright (c) 2016 Oiri Project
 *
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.svg;

import org.w3c.dom.svg.SVGMatrix;

public class Matrix implements SVGMatrix {
    private float[] mE;

    public Matrix() {
        mE = new float[9];
        mE[0] = 1.0f;
        mE[4] = 1.0f;
        mE[8] = 1.0f;
    }

    public Matrix(float[] matrix) {
        mE = matrix;
    }

    public Matrix(float a, float b, float c, float d, float e, float f) {
        mE = new float[]{a, b, c, d, e, f, 0f, 0f, 1f};
    }

    @Override
    public float getA() {
        return mE[0];
    }

    @Override
    public void setA(float a) {
        mE[0] = a;
    }

    @Override
    public float getB() {
        return mE[1];
    }

    @Override
    public void setB(float b) {
        mE[1] = b;
    }

    @Override
    public float getC() {
        return mE[2];
    }

    @Override
    public void setC(float c) {
        mE[2] = c;
    }

    @Override
    public float getD() {
        return mE[3];
    }

    @Override
    public void setD(float d) {
        mE[3] = d;
    }

    @Override
    public float getE() {
        return mE[4];
    }

    @Override
    public void setE(float e) {
        mE[4] = e;
    }

    @Override
    public float getF() {
        return mE[5];
    }

    @Override
    public void setF(float f) {
        mE[5] = f;
    }

    @Override
    public SVGMatrix multiply(SVGMatrix secondMatrix) {
        SVGMatrix s = secondMatrix;
        return new Matrix(
                mE[0] * s.getA() + mE[1] * s.getD(),
                mE[0] * s.getB() + mE[1] * s.getE(),
                mE[0] * s.getC() + mE[1] * s.getF() + mE[2],
                mE[3] * s.getA() + mE[4] * s.getD(),
                mE[3] * s.getB() + mE[4] * s.getE(),
                mE[3] * s.getC() + mE[4] * s.getF() + mE[5]
        );
    }

    @Override
    public SVGMatrix inverse() {
        return null;
    }

    @Override
    public SVGMatrix translate(float x, float y) {
        return new Matrix(
                mE[0],
                mE[1],
                mE[0] * x + mE[1] * y + mE[2],
                mE[3],
                mE[4],
                mE[3] * x + mE[4] * y + mE[5]
        );
    }

    @Override
    public SVGMatrix scale(float scaleFactor) {
        return scaleNonUniform(scaleFactor, scaleFactor);
    }

    @Override
    public SVGMatrix scaleNonUniform(float scaleFactorX, float scaleFactorY) {
        return new Matrix(
                mE[0] * scaleFactorX,
                mE[1] * scaleFactorY,
                mE[2],
                mE[3] * scaleFactorX,
                mE[4] * scaleFactorY,
                mE[5]
        );
    }

    @Override
    public SVGMatrix rotate(float angle) {
        double r = angle * Math.PI / 180.0;
        double sin = Math.sin(r);
        double cos = Math.cos(r);
        return new Matrix(
                (float) (mE[0] * cos + mE[1] * sin),
                (float) (mE[1] * cos - mE[0] * sin),
                mE[2],
                (float) (mE[3] * cos + mE[4] * sin),
                (float) (mE[4] * cos - mE[3] * sin),
                mE[5]
        );
    }

    @Override
    public SVGMatrix rotateFromVector(float x, float y) {
        return null;
    }

    @Override
    public SVGMatrix flipX() {
        return null;
    }

    @Override
    public SVGMatrix flipY() {
        return null;
    }

    @Override
    public SVGMatrix skewX(float angle) {
        return null;
    }

    @Override
    public SVGMatrix skewY(float angle) {
        return null;
    }

    public float[] getValues() {
        return mE;
    }

    public void getValues(float[] dest) {
        System.arraycopy(mE, 0, dest, 0, 9);
    }
}
