/*
 * Copyright (c) 2016 Oiri Project
 *
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.svg;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;

public class SimpleAttr implements Attr {
    private String mUri;
    private String mPrefix;
    private String mLocalName;
    private String mValue;
    private Element mOwner;

    public SimpleAttr(Element owner, String qName, String value) {
        this(qName, value);
        setOwnerElement(owner);
    }

    public SimpleAttr(String qName, String value) {
        int prefixIndex = qName.indexOf(':');
        if (prefixIndex > 0) {
            mLocalName = qName.substring(prefixIndex + 1);
            setPrefix(qName.substring(0, prefixIndex));
        }
    }

    public SimpleAttr(Element owner, String uri, String localName, String value) {
        this(uri, localName, value);
        setOwnerElement(owner);
    }

    public SimpleAttr(String uri, String localName, String value) {
        mUri = uri;
        if (uri != null && uri.isEmpty()) {
            mUri = null;
        }
        mLocalName = localName;
        mValue = value;
    }

    @Override
    public final String getName() {
        return getNodeName();
    }

    @Override
    public boolean getSpecified() {
        return false;
    }

    @Override
    public String getValue() {
        return mValue;
    }

    @Override
    public void setValue(String value) throws DOMException {
        setNodeValue(value);
    }

    @Override
    public Element getOwnerElement() {
        return null;
    }

    public void setOwnerElement(Element owner) {
        mOwner = owner;
    }

    @Override
    public TypeInfo getSchemaTypeInfo() {
        return null;
    }

    @Override
    public boolean isId() {
        return false;
    }

    @Override
    public String getNodeName() {
        return mPrefix == null ? mLocalName : mPrefix + ":" + mLocalName;
    }

    @Override
    public String getNodeValue() throws DOMException {
        return mValue;
    }

    @Override
    public void setNodeValue(String nodeValue) throws DOMException {
        mValue = nodeValue == null ? "" : nodeValue;
    }

    @Override
    public final short getNodeType() {
        return Node.ATTRIBUTE_NODE;
    }

    @Override
    public Node getParentNode() {
        return getOwnerElement();
    }

    @Override
    public final NodeList getChildNodes() {
        return null;
    }

    @Override
    public final Node getFirstChild() {
        return null;
    }

    @Override
    public final Node getLastChild() {
        return null;
    }

    @Override
    public Node getPreviousSibling() {
        return null;
    }

    @Override
    public Node getNextSibling() {
        return null;
    }

    @Override
    public final NamedNodeMap getAttributes() {
        return null;
    }

    @Override
    public final Document getOwnerDocument() {
        if (getOwnerElement() == null) {
            return null;
        }
        return getOwnerElement().getOwnerDocument();
    }

    @Override
    public final Node insertBefore(Node newChild, Node refChild) throws DOMException {
        throw new DOMException(DOMException.INVALID_MODIFICATION_ERR, "");
    }

    @Override
    public final Node replaceChild(Node newChild, Node oldChild) throws DOMException {
        throw new DOMException(DOMException.INVALID_MODIFICATION_ERR, "");
    }

    @Override
    public final Node removeChild(Node oldChild) throws DOMException {
        throw new DOMException(DOMException.INVALID_MODIFICATION_ERR, "");
    }

    @Override
    public final Node appendChild(Node newChild) throws DOMException {
        throw new DOMException(DOMException.INVALID_MODIFICATION_ERR, "");
    }

    @Override
    public final boolean hasChildNodes() {
        return false;
    }

    @Override
    public Node cloneNode(boolean deep) {
        return new SimpleAttr(mUri, mLocalName, mValue);
    }

    @Override
    public void normalize() {

    }

    @Override
    public boolean isSupported(String feature, String version) {
        return false;
    }

    @Override
    public String getNamespaceURI() {
        return mUri;
    }

    @Override
    public String getPrefix() {
        return mPrefix;
    }

    @Override
    public void setPrefix(String prefix) throws DOMException {
        mPrefix = (prefix != null && prefix.isEmpty()) ? null : prefix;
    }

    @Override
    public String getLocalName() {
        return mLocalName;
    }

    @Override
    public boolean hasAttributes() {
        return false;
    }

    @Override
    public String getBaseURI() {
        return null;
    }

    @Override
    public short compareDocumentPosition(Node other) throws DOMException {
        return 0;
    }

    @Override
    public String getTextContent() throws DOMException {
        return null;
    }

    @Override
    public void setTextContent(String textContent) throws DOMException {

    }

    @Override
    public boolean isSameNode(Node other) {
        return false;
    }

    @Override
    public String lookupPrefix(String namespaceURI) {
        return null;
    }

    @Override
    public boolean isDefaultNamespace(String namespaceURI) {
        return false;
    }

    @Override
    public String lookupNamespaceURI(String prefix) {
        return null;
    }

    @Override
    public boolean isEqualNode(Node arg) {
        return false;
    }

    @Override
    public Object getFeature(String feature, String version) {
        return null;
    }

    @Override
    public Object setUserData(String key, Object data, UserDataHandler handler) {
        return null;
    }

    @Override
    public Object getUserData(String key) {
        return null;
    }

    @Override
    public String toString() {
        if (mValue == null) {
            return "";
        }
        return mValue;
    }
}
