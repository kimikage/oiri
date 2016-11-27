/*
 * Copyright (c) 2016 Oiri Project
 *
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.svg;

import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSValue;

public class Value implements CSSValue {
    protected String mText;

    public Value(String text) {
        mText = text;
    }

    @Override
    public String getCssText() {
        return mText;
    }

    @Override
    public void setCssText(String cssText) throws DOMException {
        mText = cssText;
    }

    @Override
    public short getCssValueType() {
        return CSS_PRIMITIVE_VALUE;
    }

    @Override
    public String toString() {
        return getCssText();
    }
}
