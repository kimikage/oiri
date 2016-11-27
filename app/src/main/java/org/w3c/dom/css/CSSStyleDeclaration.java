package org.w3c.dom.css;

import org.w3c.dom.DOMException;

public interface CSSStyleDeclaration {
    String getCssText();

    void setCssText(String cssText) throws DOMException;

    String getPropertyValue(String propertyName);

    CSSValue getPropertyCSSValue(String propertyName);

    String removeProperty(String propertyName) throws DOMException;

    String getPropertyPriority(String propertyName);

    void setProperty(String propertyName,
                     String value,
                     String priority) throws DOMException;

    int getLength();

    String item(int index);

    CSSRule getParentRule();

}

