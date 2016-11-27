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

public class StyleDeclarationTest {
    @Test
    public void getCssText() throws Exception {

    }

    @Test
    public void setCssText() throws Exception {
        StyleDeclaration s = new StyleDeclaration();
        s.setCssText("a : 0.0;\nb\t: text/*comment*/; c:/*comment\n2*/url(#url)");

        assertThat(s.getLength(), is(3));
        assertThat(s.getPropertyValue("a"), is("0.0"));
        assertThat(s.getPropertyValue("b"), is("text"));
        assertThat(s.getPropertyValue("c"), is("url(#url)"));
    }


    @Test
    public void removeProperty() throws Exception {

    }

    @Test
    public void getPropertyPriority() throws Exception {

    }

    @Test
    public void setProperty() throws Exception {

    }


    @Test
    public void item() throws Exception {

    }

}