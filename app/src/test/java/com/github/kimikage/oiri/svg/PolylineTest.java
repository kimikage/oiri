/*
 * Copyright (c) 2016 Oiri Project
 *
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.svg;

import org.junit.Test;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PolylineTest {
    private static final double ERR = 1e-6;

    @Test
    public void constructWithPoints() {
        String xml = "<polyline points='10,-2 3.14,.6e2 +5-6' />";
        Polyline e = new Polyline("Polyline", new XmlFragment(xml).getAttributes());

        assertThat((double) e.getPoint(0).getX(), is(closeTo(10, ERR)));
        assertThat((double) e.getPoint(0).getY(), is(closeTo(-2, ERR)));
        assertThat((double) e.getPoint(1).getX(), is(closeTo(3.14, ERR)));
        assertThat((double) e.getPoint(1).getY(), is(closeTo(.6e2, ERR)));
        assertThat((double) e.getPoint(2).getX(), is(closeTo(5, ERR)));
        assertThat((double) e.getPoint(2).getY(), is(closeTo(-6, ERR)));
    }
}