/*
 * Copyright (c) 2016 Oiri Project
 *
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.svg;

import org.w3c.dom.svg.SVGMatrix;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Transform {
    private static final String sNumber = "([-+]?(?:[0-9]+(?:\\.[0-9]*)?|\\.[0-9]+)(?:[eE][-+]?[0-9]+)?)";
    private static final Pattern sPatternTranslate = Pattern.compile(
            "^\\s*translate\\s*\\(\\s*" + sNumber + "(?:\\s*[, ]\\s*" + sNumber + ")?\\s*\\)");
    private static final Pattern sPatternScale = Pattern.compile(
            "^\\s*scale\\s*\\(\\s*" + sNumber + "(?:\\s*[, ]\\s*" + sNumber + ")?\\s*\\)");
    private Matrix mMatrix;
    private android.graphics.Matrix mAndroidMatrix;

    public Transform(String list) {
        mMatrix = new Matrix();
        String l = list;
        for (; ; ) {
            Matcher m = sPatternTranslate.matcher(l);
            if (m.find()) {
                float tx = Float.parseFloat(m.group(1));
                String g2 = m.group(2);
                float ty = g2 != null ? Float.parseFloat(g2) : 0.0f;
                mMatrix = (Matrix) mMatrix.translate(tx, ty);
                l = l.substring(m.end());
                continue;
            }
            m = sPatternScale.matcher(l);
            if (m.find()) {
                float sx = Float.parseFloat(m.group(1));
                String g2 = m.group(2);
                float sy = g2 != null ? Float.parseFloat(g2) : sx;
                mMatrix = (Matrix) mMatrix.scaleNonUniform(sx, sy);
                l = l.substring(m.end());
                continue;
            }
            break;
        }
    }

    public SVGMatrix getMatrix() {
        return mMatrix;
    }

    public android.graphics.Matrix getAndroidMatrix() {
        if (mAndroidMatrix == null) {
            mAndroidMatrix = new android.graphics.Matrix();
            mAndroidMatrix.setValues(mMatrix.getValues());
        }
        return mAndroidMatrix;
    }

    public void getMatrixValues(float[] dest) {
        mMatrix.getValues(dest);
    }
}
