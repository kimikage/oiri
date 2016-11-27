/*
 * Copyright (c) 2016 Oiri Project
 *
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.svg;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

class XmlFragment {
    private static final String ENCODING = "utf-8";
    private InputStream mXml;

    public XmlFragment(String text) {
        try {
            mXml = new ByteArrayInputStream(text.getBytes(ENCODING));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public Attributes getAttributes() {
        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            Handler handler = new Handler();
            parser.parse(mXml, handler);
            return handler.getAttributes();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class Handler extends DefaultHandler {
        private Attributes mAttributes;

        public Attributes getAttributes() {
            return mAttributes;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes)
                throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            mAttributes = attributes;
        }
    }
}
