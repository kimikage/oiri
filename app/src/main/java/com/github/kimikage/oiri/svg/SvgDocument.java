/*
 * Copyright (c) 2016 Oiri Project
 *
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.svg;

import android.graphics.Canvas;

import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;
import org.w3c.dom.svg.SVGDocument;
import org.w3c.dom.svg.SVGElement;

import java.io.OutputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class SvgDocument implements SVGDocument {
    private SvgElement mRoot;
    private Style mStyle;

    public SvgDocument() {
        mRoot = Svg.getElementFactory().newElement(null, null);
    }

    @Override
    public DocumentType getDoctype() {
        return null;
    }

    @Override
    public DOMImplementation getImplementation() {
        return null;
    }

    @Override
    public Element getDocumentElement() {
        return getRootElement();
    }

    @Override
    public Element createElement(String tagName) throws DOMException {
        return new Unknown(null, tagName, null);
    }

    @Override
    public DocumentFragment createDocumentFragment() {
        return null;
    }

    @Override
    public Text createTextNode(String data) {
        return null;
    }

    @Override
    public Comment createComment(String data) {
        return null;
    }

    @Override
    public CDATASection createCDATASection(String data) throws DOMException {
        return null;
    }

    @Override
    public ProcessingInstruction createProcessingInstruction(String target, String data) throws DOMException {
        return null;
    }

    @Override
    public Attr createAttribute(String name) throws DOMException {
        return null;
    }

    @Override
    public EntityReference createEntityReference(String name) throws DOMException {
        return null;
    }

    @Override
    public NodeList getElementsByTagName(String tagname) {
        return null;
    }

    @Override
    public Node importNode(Node importedNode, boolean deep) throws DOMException {
        return null;
    }

    @Override
    public Element createElementNS(String namespaceURI, String qualifiedName) throws DOMException {
        return null;
    }

    @Override
    public Attr createAttributeNS(String namespaceURI, String qualifiedName) throws DOMException {
        return null;
    }

    @Override
    public NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
        return null;
    }

    @Override
    public Element getElementById(String elementId) {
        if (mRoot == null) {
            return null;
        }
        if (elementId == null) {
            return null;
        }
        return getElementById(mRoot, elementId);
    }

    private Element getElementById(Element element, String elementId) {
        if (elementId.equals(element.getAttribute("id"))) {
            return element;
        }
        NodeList l = element.getChildNodes();
        for (int i = 0; i < l.getLength(); ++i) {
            Node n = l.item(i);
            if (!(n instanceof Element)) {
                continue;
            }
            Element e = getElementById((Element) n, elementId);
            if (e != null) {
                return e;
            }
        }
        return null;
    }

    @Override
    public String getInputEncoding() {
        return null;
    }

    @Override
    public String getXmlEncoding() {
        return "UTF-8";
    }

    @Override
    public boolean getXmlStandalone() {
        return false;
    }

    @Override
    public void setXmlStandalone(boolean xmlStandalone) throws DOMException {

    }

    @Override
    public String getXmlVersion() {
        return "1.0";
    }

    @Override
    public void setXmlVersion(String xmlVersion) throws DOMException {

    }

    @Override
    public boolean getStrictErrorChecking() {
        return false;
    }

    @Override
    public void setStrictErrorChecking(boolean strictErrorChecking) {

    }

    @Override
    public String getDocumentURI() {
        return null;
    }

    @Override
    public void setDocumentURI(String documentURI) {

    }

    @Override
    public Node adoptNode(Node source) throws DOMException {
        return null;
    }

    @Override
    public DOMConfiguration getDomConfig() {
        return null;
    }

    @Override
    public void normalizeDocument() {

    }

    @Override
    public Node renameNode(Node n, String namespaceURI, String qualifiedName) throws DOMException {
        return null;
    }

    @Override
    public String getNodeName() {
        return null;
    }

    @Override
    public String getNodeValue() throws DOMException {
        return null;
    }

    @Override
    public void setNodeValue(String nodeValue) throws DOMException {

    }

    @Override
    public short getNodeType() {
        return DOCUMENT_NODE;
    }

    @Override
    public Node getParentNode() {
        return null;
    }

    @Override
    public NodeList getChildNodes() {
        if (mRoot == null) {
            return null;
        }
        NodeArrayList children = new NodeArrayList();
        children.add(mRoot);
        return children;
    }

    @Override
    public Node getFirstChild() {
        return mRoot;
    }

    @Override
    public Node getLastChild() {
        return mRoot;
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
    public String getTitle() {
        return null;
    }

    @Override
    public String getReferrer() {
        return null;
    }

    @Override
    public String getDomain() {
        return null;
    }

    @Override
    public String getURL() {
        return null;
    }

    @Override
    public SVGElement getRootElement() {
        return mRoot;
    }

    void setRootElement(SvgElement root) {
        mRoot = root;
    }

    public void draw(Canvas canvas) {
        mRoot.draw(canvas);
    }

    public void writeXml(OutputStream output) throws TransformerException {
        DOMSource source = new DOMSource(this);
        StreamResult result = new StreamResult(output);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.transform(source, result);
    }
}
