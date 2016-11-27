/*
 * Copyright (c) 2016 Oiri Project
 *
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.svg;

import org.junit.Test;
import org.w3c.dom.svg.SVGMatrix;
import org.w3c.dom.svg.SVGPoint;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PointTest {
    private static final double ERR = 1e-6;

    @Test
    public void matrixTransform() throws Exception {
        SVGMatrix m1 = new Matrix().translate(2.0f, -0.5f).scaleNonUniform(2.0f, -0.5f);
        SVGMatrix m2 = new Matrix().scaleNonUniform(0.5f, -2.0f).translate(-2.0f, 0.5f);
        SVGPoint p = new Point(2.0f, -0.5f);
        SVGPoint p2 = p.matrixTransform(m1).matrixTransform(m2);

        assertThat((double) p2.getX(), is(closeTo(2.0, ERR)));
        assertThat((double) p2.getY(), is(closeTo(-0.5, ERR)));
    }

}