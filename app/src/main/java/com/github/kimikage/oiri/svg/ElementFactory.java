/*
 * Copyright (c) 2016 Oiri Project
 *
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.svg;

import org.xml.sax.Attributes;

interface ElementFactory {
    SvgElement newElement(String qName, Attributes attr);
}
