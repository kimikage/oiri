/*
 * Copyright (c) 2016 Oiri Project
 *
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.svg;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class UnknownTest {
    @Test
    public void construct() {
        XmlFragment xml = new XmlFragment("<x:unknown a='1' b='text' />");
        Unknown e = new Unknown("", "unknown", xml.getAttributes());

        assertThat(e.getAttribute("a"), is("1"));
        assertThat(e.getAttribute("b"), is("text"));
    }
}