/*
 * Copyright (c) 2016 Oiri Project
 *
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.svg;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SvgParser {
    public static final String NS_SVG = "http://www.w3.org/2000/svg";
    private static final String NUMBER = "([-+]?(?:[0-9]+(?:\\.[0-9]*)?|\\.[0-9]+)(?:[eE][-+]?[0-9]+)?)";
    private static final Pattern sPatternNumber = Pattern.compile(NUMBER);
    private static final Pattern sPatternLength =
            Pattern.compile("^" + NUMBER + "(|in|cm|mm|pt|pc|px|%)$");
    private static Map<String, ElementFactory> sFactories = new HashMap<>();

    static {
        sFactories.put(Svg.NAME, Svg.getElementFactory());
        sFactories.put(Group.NAME, Group.getElementFactory());
        sFactories.put(Path.NAME, Path.getElementFactory());
        sFactories.put(Rect.NAME, Rect.getElementFactory());
        sFactories.put(Circle.NAME, Circle.getElementFactory());
        sFactories.put(Line.NAME, Line.getElementFactory());
        sFactories.put(Polyline.NAME, Polyline.getElementFactory());
    }

    private InputStream mInput;

    public static float parseLength(String input) {
        Matcher m = sPatternLength.matcher(input);
        if (m.matches()) {
            float f = Float.parseFloat(m.group(1));
            String unit = m.group(2);
            switch (unit) {
                case "":
                case "px":
                    return f;
                case "in":
                    return f * 96.0f; // 96 px/inch
                case "cm":
                    return f * 96.0f / 2.54f; // (96 px/inch) / (2.54 cm/inch)
                case "mm":
                    return f * 96.0f / 25.4f; // (96 px/inch) / (2.54 mm/inch)
                case "pt":
                    return f * 96.0f / 72.0f; // (96 px/inch) / (72 pt/inch)
                case "pc":
                    return f * 96.0f / 72.0f / 12.0f; // (96 px/inch) / (72 pt/inch) / (12 pt/pc)
                case "%":
                    return Float.NaN;
            }
            return f;
        } else {
            return Float.NaN;
        }
    }

    public static float parseCoordinate(String input) {
        return parseLength(input);
    }

    public static List<Float> parsePoints(String input) {
        List<Float> values = new ArrayList<>();
        Matcher m = sPatternNumber.matcher(input);
        while (m.find()) {
            values.add(Float.parseFloat(m.group(1)));
        }
        return values;
    }

    public void setInput(InputStream input) {
        mInput = input;
    }

    public SvgDocument parse() throws IOException, SAXException {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            SAXParser parser = factory.newSAXParser();
            SvgHandler handler = new SvgHandler();
            parser.setProperty("http://xml.org/sax/properties/lexical-handler", handler);
            parser.parse(mInput, handler);

            SvgDocument doc = new SvgDocument();
            doc.setRootElement(handler.getSvgElement());
            return doc;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class SvgHandler extends DefaultHandler implements LexicalHandler {
        private Deque<Node> mContext;
        private Svg mSvg;
        private List<String> mPrefixes = new ArrayList<>();
        private List<String> mUris = new ArrayList<>();

        public Svg getSvgElement() {
            return mSvg;
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);

            Node current = mContext.pop();
            if (mContext.isEmpty()) {
                mSvg = (Svg) current;
            } else {
                Node parent = mContext.getFirst();
                parent.appendChild(current);
            }
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            mContext = new ArrayDeque<>();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes)
                throws SAXException {
            super.startElement(uri, localName, qName, attributes);

            Element e = null;
            if ((uri.equals(NS_SVG) || uri.isEmpty()) && sFactories.containsKey(localName)) {
                ElementFactory factory = sFactories.get(localName);
                e = factory.newElement(qName, attributes);
            } else {
                e = new Unknown(uri, qName, attributes);
            }
            for (int i = 0; i < mPrefixes.size(); ++i) {
                String prefix = mPrefixes.get(i);
                String xmlns = prefix.isEmpty() ? "xmlns" : "xmlns:" + prefix;
                e.setAttributeNS("", xmlns, mUris.get(i));
            }
            mPrefixes.clear();
            mUris.clear();
            mContext.push(e);
        }

        @Override
        public void startPrefixMapping(String prefix, String uri) throws SAXException {
            super.startPrefixMapping(prefix, uri);
            mPrefixes.add(prefix);
            mUris.add(uri);
        }

        @Override
        public void startDTD(String name, String publicId, String systemId) throws SAXException {

        }

        @Override
        public void endDTD() throws SAXException {

        }

        @Override
        public void startEntity(String name) throws SAXException {

        }

        @Override
        public void endEntity(String name) throws SAXException {

        }

        @Override
        public void startCDATA() throws SAXException {

        }

        @Override
        public void endCDATA() throws SAXException {

        }

        @Override
        public void comment(char[] ch, int start, int length) throws SAXException {
            Node n = mContext.getFirst();
            StringBuilder sb = new StringBuilder(length);
            sb.append(ch, 0, length);
            n.appendChild(new Comment(sb.toString()));

        }
    }
}
