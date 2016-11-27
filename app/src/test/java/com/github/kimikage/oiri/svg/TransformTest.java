/*
 * Copyright (c) 2016 Oiri Project
 *
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.svg;

import org.junit.Test;

import static org.hamcrest.Matchers.array;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TransformTest {
    private static final double ERR = 1e-6;
    private float[] mValues = new float[9];

    @Test
    @SuppressWarnings("unchecked")
    public void translate() {
        Transform t = new Transform("translate ( 1.2 , -3.4 ) ");
        t.getMatrixValues(mValues);

        assertThat(toDoubleArray(mValues), is(
                array(
                        closeTo(1.0, ERR), closeTo(0.0, ERR), closeTo(1.2, ERR),
                        closeTo(0.0, ERR), closeTo(1.0, ERR), closeTo(-3.4, ERR),
                        closeTo(0.0, ERR), closeTo(0.0, ERR), closeTo(1.0, ERR)
                )));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void scale() {
        Transform t = new Transform(" scale(3) ");
        t.getMatrixValues(mValues);

        assertThat(toDoubleArray(mValues), is(
                array(
                        closeTo(3.0, ERR), closeTo(0.0, ERR), closeTo(0.0, ERR),
                        closeTo(0.0, ERR), closeTo(3.0, ERR), closeTo(0.0, ERR),
                        closeTo(0.0, ERR), closeTo(0.0, ERR), closeTo(1.0, ERR)
                )));

        t = new Transform("scale(-1, 2e-1)");
        t.getMatrixValues(mValues);

        assertThat(toDoubleArray(mValues), is(
                array(
                        closeTo(-1.0, ERR), closeTo(0.0, ERR), closeTo(0.0, ERR),
                        closeTo(0.0, ERR), closeTo(0.2, ERR), closeTo(0.0, ERR),
                        closeTo(0.0, ERR), closeTo(0.0, ERR), closeTo(1.0, ERR)
                )));
    }

    private Double[] toDoubleArray(float[] f) {
        Double[] d = new Double[f.length];
        for (int i = 0; i < f.length; ++i) {
            d[i] = (double) f[i];
        }
        return d;
    }

}