/*
 * Copyright (c) 2016 Oiri Project
 *  
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.svg;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AttrMap implements NamedNodeMap {
    private Map<Key, Attr> mMap = new HashMap<>();

    @Override
    public Node getNamedItem(String name) {
        if (name.indexOf(':') >= 0) {
            return null;
        }
        return getNamedItemNS(null, name);
    }

    @Override
    public Node setNamedItem(Node arg) throws DOMException {
        if (arg.getNodeName().indexOf(':') >= 0) {
            throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "use setNamedItemNS()");
        }
        return setNamedItemNS(arg);
    }

    @Override
    public Node removeNamedItem(String name) throws DOMException {
        if (name.indexOf(':') >= 0) {
            throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "use removeNamedItemNS()");
        }
        return removeNamedItemNS(null, name);
    }

    @Override
    public Node item(int index) {
        Node n = null;
        Iterator<Attr> it = mMap.values().iterator();
        for (int i = 0; i <= index; ++i) {
            n = it.next();
        }
        return n;
    }

    @Override
    public int getLength() {
        return mMap.size();
    }

    @Override
    public Node getNamedItemNS(String namespaceURI, String localName) throws DOMException {
        Key key = new Key(namespaceURI, localName);
        if (mMap.containsKey(key)) {
            return mMap.get(key);
        }
        return null;
    }

    @Override
    public Node setNamedItemNS(Node arg) throws DOMException {
        if (arg instanceof Attr) {
            Key k = new Key(arg.getNamespaceURI(), arg.getLocalName());
            mMap.put(k, (Attr) arg);
            return arg;
        }
        return null;
    }

    @Override
    public Node removeNamedItemNS(String namespaceURI, String localName) throws DOMException {
        Key k = new Key(namespaceURI, localName);
        if (mMap.containsKey(k)) {
            Node n = mMap.get(k);
            mMap.remove(k);
            return n;
        }
        return null;
    }

    public Attr removeAttributeNode(Attr node) {
        for (Map.Entry<Key, Attr> e : mMap.entrySet()) {
            if (e.getValue() == node) {
                mMap.remove(e.getKey());
                return node;
            }
        }
        return null;
    }

    public boolean hasAttribute(String name) {
        return hasAttributeNS(null, name);
    }

    public boolean hasAttributeNS(String namespaceURI, String localName) {
        Key k = new Key(namespaceURI, localName);
        return mMap.containsKey(k);
    }

    private class Key {
        public String uri;
        public String localName;

        public Key(String uri, String localName) {
            this.uri = (uri != null && uri.isEmpty()) ? null : uri;
            this.localName = localName;
        }

        @Override
        public int hashCode() {
            if (uri == null) {
                return localName.hashCode();
            }
            return uri.hashCode() ^ localName.hashCode();
        }

        @Override
        public boolean equals(Object other) {
            Key k = (Key) other;
            if (!localName.equals(localName)) {
                return false;
            }
            if (uri != null && !uri.equals(k.uri)) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            if (uri != null) {
                return localName + "@" + uri;
            }
            return localName;
        }
    }
}
