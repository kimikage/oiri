/*
 * Copyright (c) 2016 Oiri Project
 *
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.svg;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class SvgDocumentTest {
    @Test
    public void writeXml() throws Exception {
        String path = "test.svg";

        SvgParser parser = new SvgParser();
        parser.setInput(getResource(path));
        SvgDocument svg = parser.parse();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        svg.writeXml(out);
        String xml = out.toString("UTF-8");
    }

    private InputStream getResource(String path) {
        return this.getClass().getResourceAsStream(path);
    }
}