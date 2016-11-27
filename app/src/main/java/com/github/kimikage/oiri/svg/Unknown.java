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
import org.xml.sax.Attributes;


public class Unknown implements Element {
    protected String mUri;
    protected String mLocalName;
    protected AttrMap mAttributes = new AttrMap();
    protected String mPrefix;
    protected Node mParent;
    protected NodeArrayList mChildren = new NodeArrayList();

    public Unknown() {

    }

    public Unknown(String uri, String qName, Attributes attr) {
        mUri = uri;
        if (qName == null) {
            qName = "";
        }
        int prefixIndex = qName.indexOf(':');
        if (prefixIndex >= 0) {
            mLocalName = qName.substring(prefixIndex + 1);
            setPrefix(qName.substring(0, prefixIndex));
        } else {
            mLocalName = qName;
        }

        if (attr != null) {
            for (int i = 0; i < attr.getLength(); ++i) {
                setAttributeNS(attr.getURI(i), attr.getQName(i), attr.getValue(i));
            }
        }
    }

    @Override
    public void setAttribute(String name, String value) {
        Node n = mAttributes.getNamedItem(name);
        if (n != null) {
            n.setNodeValue(value);
        } else {
            mAttributes.setNamedItem(new SimpleAttr(this, name, value));
        }
    }

    @Override
    public void removeAttribute(String name) throws DOMException {
        mAttributes.removeNamedItem(name);
    }

    @Override
    public Attr getAttributeNode(String name) {
        return (Attr) (mAttributes.getNamedItem(name));
    }

    @Override
    public Attr setAttributeNode(Attr newAttr) throws DOMException {
        return (Attr) mAttributes.setNamedItem(newAttr);
    }

    @Override
    public Attr removeAttributeNode(Attr oldAttr) throws DOMException {
        return mAttributes.removeAttributeNode(oldAttr);
    }

    @Override
    public NodeList getElementsByTagName(String name) {
        return null;
    }

    @Override
    public String getAttributeNS(String namespaceURI, String localName) throws DOMException {
        Attr a = (Attr) mAttributes.getNamedItemNS(namespaceURI, localName);
        if (a == null || a.getValue() == null) {
            return getDefaultAttributeNS(namespaceURI, localName);
        }
        return a.getValue();
    }

    protected String getDefaultAttributeNS(String namespaceURI, String localName) {
        return "";
    }

    @Override
    public void setAttributeNS(String namespaceURI, String qName, String value)
            throws DOMException {
        int prefixIndex = qName.indexOf(':');
        String localName = qName;
        String prefix = null;
        if (prefixIndex >= 0) {
            localName = qName.substring(prefixIndex + 1);
            prefix = qName.substring(0, prefixIndex);
        }

        Node n = mAttributes.getNamedItemNS(namespaceURI, localName);
        if (n != null) {
            n.setPrefix(prefix);
            n.setNodeValue(value);
        } else {
            Attr a = new SimpleAttr(this, namespaceURI, localName, value);
            a.setPrefix(prefix);
            mAttributes.setNamedItemNS(a);
        }
    }

    @Override
    public void removeAttributeNS(String namespaceURI, String localName) throws DOMException {

    }

    @Override
    public Attr getAttributeNodeNS(String namespaceURI, String localName) throws DOMException {
        return (Attr) mAttributes.getNamedItemNS(namespaceURI, localName);
    }

    @Override
    public Attr setAttributeNodeNS(Attr newAttr) throws DOMException {
        mAttributes.setNamedItemNS(newAttr);
        return newAttr;
    }

    @Override
    public NodeList getElementsByTagNameNS(String namespaceURI, String localName)
            throws DOMException {
        return null;
    }

    @Override
    public boolean hasAttribute(String name) {
        return mAttributes.hasAttribute(name);
    }

    @Override
    public boolean hasAttributeNS(String namespaceURI, String localName) throws DOMException {
        return mAttributes.hasAttributeNS(namespaceURI, localName);
    }

    @Override
    public TypeInfo getSchemaTypeInfo() {
        return null;
    }

    @Override
    public void setIdAttribute(String name, boolean isId) throws DOMException {
    }

    @Override
    public void setIdAttributeNS(String namespaceURI, String localName, boolean isId)
            throws DOMException {
    }

    @Override
    public void setIdAttributeNode(Attr idAttr, boolean isId) throws DOMException {
    }

    @Override
    public String getTagName() {
        return mPrefix == null ? mLocalName : mPrefix + ":" + mLocalName;
    }

    @Override
    public String getAttribute(String name) {
        Node n = mAttributes.getNamedItem(name);
        if (n == null) {
            return null;
        }
        return n.getNodeValue();
    }

    @Override
    public final String getNodeName() {
        return getTagName();
    }

    @Override
    public final String getNodeValue() throws DOMException {
        return null;
    }

    @Override
    public final void setNodeValue(String nodeValue) throws DOMException {
    }

    @Override
    public final short getNodeType() {
        return ELEMENT_NODE;
    }

    @Override
    public Node getParentNode() {
        return mParent;
    }

    protected void setParentNode(Node parent) {
        mParent = parent;
    }

    @Override
    public NodeList getChildNodes() {
        return mChildren;
    }

    @Override
    public Node getFirstChild() {
        return mChildren.first();
    }

    @Override
    public Node getLastChild() {
        return mChildren.last();
    }

    @Override
    public Node getPreviousSibling() {
        if (mParent == null) {
            return null;
        }
        NodeList n = mParent.getChildNodes();
        for (int i = 1; i < n.getLength(); ++i) {
            if (n.item(i) == this) {
                return n.item(i - 1);
            }
        }
        return null;
    }

    @Override
    public Node getNextSibling() {
        if (mParent == null) {
            return null;
        }
        NodeList n = mParent.getChildNodes();
        for (int i = 1; i < n.getLength(); ++i) {
            if (n.item(i - 1) == this) {
                return n.item(i);
            }
        }
        return null;
    }

    @Override
    public NamedNodeMap getAttributes() {
        return mAttributes;
    }

    @Override
    public Document getOwnerDocument() {
        return null;
    }

    @Override
    public Node insertBefore(Node newChild, Node refChild) throws DOMException {
        return null;
    }

    @Override
    public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
        return null;
    }

    @Override
    public Node removeChild(Node oldChild) throws DOMException {
        mChildren.remove(oldChild);
        return oldChild;
    }

    @Override
    public Node appendChild(Node newChild) throws DOMException {
        mChildren.add(newChild);
        if (newChild instanceof Unknown) {
            ((Unknown) newChild).setParentNode(this);
        }
        return newChild;
    }

    @Override
    public boolean hasChildNodes() {
        return mChildren.getLength() > 0;
    }

    @Override
    public Node cloneNode(boolean deep) {
        return null;
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
        mPrefix = prefix;
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
}
