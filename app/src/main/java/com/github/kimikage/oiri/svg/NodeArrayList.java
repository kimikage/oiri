/*
 * Copyright (c) 2016 Oiri Project
 *
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.svg;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class NodeArrayList implements NodeList {
    private List<Node> mNodes = new ArrayList<>();

    @Override
    public Node item(int index) {
        return mNodes.get(index);
    }

    @Override
    public int getLength() {
        return mNodes.size();
    }

    public Node last() {
        return mNodes.size() > 0 ? mNodes.get(mNodes.size() - 1) : null;
    }

    public Node first() {
        return mNodes.size() > 0 ? mNodes.get(0) : null;
    }

    public void add(Node node) {
        mNodes.add(node);
    }

    public void remove(Node node) {
        mNodes.remove(node);
    }

    public List<Node> getList() {
        return mNodes;
    }
}
