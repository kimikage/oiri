package org.w3c.dom.svg;

public interface SVGMatrix {
    float getA();

    void setA(float a);

    float getB();

    void setB(float b);

    float getC();

    void setC(float c);

    float getD();

    void setD(float d);

    float getE();

    void setE(float e);

    float getF();

    void setF(float f);

    SVGMatrix multiply(SVGMatrix secondMatrix);

    SVGMatrix inverse();

    SVGMatrix translate(float x, float y);

    SVGMatrix scale(float scaleFactor);

    SVGMatrix scaleNonUniform(float scaleFactorX, float scaleFactorY);

    SVGMatrix rotate(float angle);

    SVGMatrix rotateFromVector(float x, float y);

    SVGMatrix flipX();

    SVGMatrix flipY();

    SVGMatrix skewX(float angle);

    SVGMatrix skewY(float angle);
}
