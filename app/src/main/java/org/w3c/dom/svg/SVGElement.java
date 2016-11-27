package org.w3c.dom.svg;

import org.w3c.dom.Element;

public interface SVGElement extends Element {
    String getId();

    void setId(String id);

    String getXMLbase();

    void setXMLbase(String XMLbase);

    //SVGSVGElement getOwnerSVGElement();

    SVGElement getViewportElement();
}
