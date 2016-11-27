/*
 * Copyright (c) 2016 Oiri Project
 *
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.svg;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;

public class Comment implements org.w3c.dom.Comment {
    private Node mParent;
    private String mData;

    public Comment(String data) {
        mData = data;
    }

    @Override
    public String getData() throws DOMException {
        return mData;
    }

    @Override
    public void setData(String data) throws DOMException {
        mData = data;
    }

    @Override
    public int getLength() {
        return mData.length();
    }

    @Override
    public String substringData(int offset, int count) throws DOMException {
        return mData.substring(offset, count);
    }

    @Override
    public void appendData(String arg) throws DOMException {
        mData = mData.concat(arg);
    }

    @Override
    public void insertData(int offset, String arg) throws DOMException {

    }

    @Override
    public void deleteData(int offset, int count) throws DOMException {

    }

    @Override
    public void replaceData(int offset, int count, String arg) throws DOMException {

    }

    @Override
    public String getNodeName() {
        return "#comment";
    }

    @Override
    public String getNodeValue() throws DOMException {
        return mData;
    }

    @Override
    public void setNodeValue(String nodeValue) throws DOMException {
        mData = nodeValue;
    }

    @Override
    public short getNodeType() {
        return Node.COMMENT_NODE;
    }

    @Override
    public Node getParentNode() {
        return mParent;
    }

    @Override
    public NodeList getChildNodes() {
        return null;
    }

    @Override
    public Node getFirstChild() {
        return null;
    }

    @Override
    public Node getLastChild() {
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
    public NamedNodeMap getAttributes() {
        return null;
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
        return null;
    }

    @Override
    public Node appendChild(Node newChild) throws DOMException {
        return null;
    }

    @Override
    public boolean hasChildNodes() {
        return false;
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
        return null;
    }

    @Override
    public String getPrefix() {
        return null;
    }

    @Override
    public void setPrefix(String prefix) throws DOMException {

    }

    @Override
    public String getLocalName() {
        return null;
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
        return mData;
    }

    @Override
    public void setTextContent(String textContent) throws DOMException {
        mData = textContent;
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
