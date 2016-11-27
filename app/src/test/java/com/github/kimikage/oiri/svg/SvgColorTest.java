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

public class SvgColorTest {
    @Test
    public void fromLinearRgb() throws Exception {
        SvgColor c = SvgColor.fromLinearRgb(1.0f, 0.5f, 0.0f);

        assertThat(c.getIntColor(), is(0xFFFFBC00));

    }

    @Test
    public void getY() throws Exception {
        SvgColor c = new SvgColor(0xC0FFEE);

        assertThat((double) c.getY(), is(closeTo(0.889, 1e-3)));
    }

    @Test
    public void setRGBColor() throws Exception {
        SvgColor c = new SvgColor(0);

        c.setRGBColor("#C0FFEE");
        assertThat(c.getIntColor(), is(0xFFC0FFEE));

        c.setRGBColor("#1Ab");
        assertThat(c.getIntColor(), is(0xFF11AABB));
    }

}