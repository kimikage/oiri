package org.w3c.dom.svg;

import org.w3c.dom.css.CSSValue;

public interface SVGColor extends CSSValue {
    short SVG_COLORTYPE_UNKNOWN = 0;
    short SVG_COLORTYPE_RGBCOLOR = 1;
    short SVG_COLORTYPE_RGBCOLOR_ICCCOLOR = 2;
    short SVG_COLORTYPE_CURRENTCOLOR = 3;

    short getColorType();

    // RGBColor getRgbColor();
    // SVGICCColor getIccColor();
    void setRGBColor(String rgbColor);

    void setRGBColorICCColor(String rgbColor, String iccColor);

    void setColor(short colorType, String rgbColor, String iccColor);
}
