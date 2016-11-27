/*
 * Copyright (c) 2016 Oiri Project
 *
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.svg;

import org.junit.Test;
import org.w3c.dom.svg.SVGMatrix;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MatrixTest {
    private static final double ERR = 1e-6;

    @Test
    public void construct() {
        Matrix m = new Matrix();

        assertThat((double) m.getA(), is(closeTo(1.0, ERR)));
        assertThat((double) m.getB(), is(closeTo(0.0, ERR)));
        assertThat((double) m.getC(), is(closeTo(0.0, ERR)));
        assertThat((double) m.getD(), is(closeTo(0.0, ERR)));
        assertThat((double) m.getE(), is(closeTo(1.0, ERR)));
        assertThat((double) m.getF(), is(closeTo(0.0, ERR)));
    }

    @Test
    public void translate() {
        SVGMatrix m = new Matrix().translate(2.0f, -0.5f);

        assertThat((double) m.getA(), is(closeTo(1.0, ERR)));
        assertThat((double) m.getB(), is(closeTo(0.0, ERR)));
        assertThat((double) m.getC(), is(closeTo(2.0, ERR)));
        assertThat((double) m.getD(), is(closeTo(0.0, ERR)));
        assertThat((double) m.getE(), is(closeTo(1.0, ERR)));
        assertThat((double) m.getF(), is(closeTo(-0.5, ERR)));
    }

    @Test
    public void scaleNonUniform() {
        SVGMatrix m = new Matrix().scaleNonUniform(2.0f, -0.5f);

        assertThat((double) m.getA(), is(closeTo(2.0, ERR)));
        assertThat((double) m.getB(), is(closeTo(0.0, ERR)));
        assertThat((double) m.getC(), is(closeTo(0.0, ERR)));
        assertThat((double) m.getD(), is(closeTo(0.0, ERR)));
        assertThat((double) m.getE(), is(closeTo(-0.5, ERR)));
        assertThat((double) m.getF(), is(closeTo(0.0, ERR)));
    }

    @Test
    public void rotate() {
        SVGMatrix m = new Matrix().rotate(120f);

        assertThat((double) m.getA(), is(closeTo(-0.5, ERR)));
        assertThat((double) m.getB(), is(closeTo(-Math.sqrt(0.75), ERR)));
        assertThat((double) m.getC(), is(closeTo(0.0, ERR)));
        assertThat((double) m.getD(), is(closeTo(Math.sqrt(0.75), ERR)));
        assertThat((double) m.getE(), is(closeTo(-0.5, ERR)));
        assertThat((double) m.getF(), is(closeTo(0.0, ERR)));
    }
}