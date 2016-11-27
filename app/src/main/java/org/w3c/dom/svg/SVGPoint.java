package org.w3c.dom.svg;

public interface SVGPoint {
    float getX();

    void setX(float x);

    float getY();

    void setY(float y);

    SVGPoint matrixTransform(SVGMatrix matrix);
}
