/*
 * Copyright (c) 2016 Oiri Project
 *
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.svg;

import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSValue;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StyleDeclaration implements CSSStyleDeclaration {
    private static final Pattern sPatternComment = Pattern.compile("/\\*/?(?:[^/]|[^*]/)*\\*/");
    private static final Pattern sPatternProp = Pattern.compile("([a-zA-Z0-9\\-]+)\\s*:\\s*([^\\s]+)");
    protected Map<String, CSSValue> mMap = new HashMap<>();

    @Override
    public String getCssText() {
        return null;
    }

    @Override
    public void setCssText(String cssText) throws DOMException {
        mMap.clear();
        Matcher mc = sPatternComment.matcher(cssText);
        String[] props = mc.replaceAll("").split("\\s*;\\s*");
        for (String p : props) {
            Matcher m = sPatternProp.matcher(p);
            if (m.matches()) {
                setProperty(m.group(1), m.group(2), "");
            } else {
                throw new DOMException(DOMException.SYNTAX_ERR, "error:" + p);
            }
        }
    }

    @Override
    public String getPropertyValue(String propertyName) {
        return mMap.get(propertyName).getCssText();
    }

    @Override
    public CSSValue getPropertyCSSValue(String propertyName) {
        return mMap.get(propertyName);
    }

    @Override
    public String removeProperty(String propertyName) throws DOMException {
        if (mMap.containsKey(propertyName)) {
            return mMap.remove(propertyName).getCssText();
        }
        return null;
    }

    @Override
    public String getPropertyPriority(String propertyName) {
        return null;
    }

    @Override
    public void setProperty(String propertyName, String value, String priority) throws DOMException {
        mMap.put(propertyName, new Value(value));
    }

    @Override
    public int getLength() {
        return mMap.size();
    }

    @Override
    public String item(int index) {
        return null;
    }

    @Override
    public CSSRule getParentRule() {
        return null;
    }
}
