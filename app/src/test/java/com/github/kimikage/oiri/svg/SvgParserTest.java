/*
 * Copyright (c) 2016 Oiri Project
 *
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.svg;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SvgParserTest {
    private static final double ERR = 1e-6;

    @Test
    public void parseLength() throws Exception {
        assertThat((double) SvgParser.parseLength("-12.34"), is(closeTo(-12.34, ERR)));
        assertThat((double) SvgParser.parseLength("56.7px"), is(closeTo(56.7, ERR)));
        assertThat((double) SvgParser.parseLength("7.2pt"), is(closeTo(9.6, ERR)));
        assertThat((double) SvgParser.parseLength("2.54E+2mm"), is(closeTo(960, ERR)));
    }


    @Test
    public void parse() throws Exception {
        String path = "test.svg";

        SvgParser parser = new SvgParser();
        parser.setInput(getResource(path));
        SvgDocument svg = parser.parse();
        Element svgRoot = svg.getDocumentElement();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document xml = db.parse(getResource(path));
        Element xmlRoot = xml.getDocumentElement();

        assertThat(svgRoot.getNamespaceURI(),
                is(xmlRoot.getNamespaceURI()));

        assertThat(svgRoot.getTagName(),
                is(xmlRoot.getTagName()));

        NamedNodeMap svgAttributes = svgRoot.getAttributes();
        NamedNodeMap xmlAttributes = xmlRoot.getAttributes();
        assertThat(svgAttributes.getLength(),
                is(xmlAttributes.getLength()));

        assertThat(svgAttributes.getNamedItem("viewbox"),
                is(xmlAttributes.getNamedItem("viewbox")));

        Shape circle = (Shape) svg.getElementById("circle");
        boolean b = circle.mStyle.hasFill();
        Object p = circle.getFillPaint();

    }

    private InputStream getResource(String path) {
        return this.getClass().getResourceAsStream(path);
    }

}