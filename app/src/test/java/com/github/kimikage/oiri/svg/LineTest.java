/*
 * Copyright (c) 2016 Oiri Project
 *
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.svg;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class LineTest {
    private static final double ERR = 1e-6;

    @org.junit.Test
    public void constructWithFullCoordinates() {
        String xml = "<line x1='10' y1='-2' x2='3.14' y2='.6e2' />";
        Line e = new Line("line", new XmlFragment(xml).getAttributes());

        assertThat((double) e.getX1(), is(closeTo(10, ERR)));
        assertThat((double) e.getY1(), is(closeTo(-2, ERR)));
        assertThat((double) e.getX2(), is(closeTo(3.14, ERR)));
        assertThat((double) e.getY2(), is(closeTo(.6e2, ERR)));
    }

    @org.junit.Test
    public void constructWithDefaultValues() {
        String xml = "<line />";
        Line e = new Line("line", new XmlFragment(xml).getAttributes());

        assertThat((double) e.getX1(), is(closeTo(0.0, ERR)));
        assertThat((double) e.getY1(), is(closeTo(0.0, ERR)));
        assertThat((double) e.getX2(), is(closeTo(0.0, ERR)));
        assertThat((double) e.getY2(), is(closeTo(0.0, ERR)));
    }

    @org.junit.Test
    public void constructWithCoordinateParameters() {
        Line e = new Line(10f, -2f, 3.14f, .6e2f);

        assertThat((double) e.getX1(), is(closeTo(10, ERR)));
        assertThat((double) e.getY1(), is(closeTo(-2, ERR)));
        assertThat((double) e.getX2(), is(closeTo(3.14, ERR)));
        assertThat((double) e.getY2(), is(closeTo(.6e2, ERR)));
    }

}