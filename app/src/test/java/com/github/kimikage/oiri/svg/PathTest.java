/*
 * Copyright (c) 2016 Oiri Project
 *
 *  This software is distributed under an MIT-style license.
 *  See LICENSE file for more information.
 */

package com.github.kimikage.oiri.svg;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Mockito.doAnswer;

@RunWith(MockitoJUnitRunner.class)
public class PathTest {
    private static final double ERR = 1e-6;
    private static final double ERR_DEG = 1e-4;

    @Mock
    android.graphics.Path mPath = new android.graphics.Path();

    @Test
    public void constructWithM() {
        Path p = new Path("M 100 .5  m-50,15e-1 10.0 48", mPath);

        int i = 0;
        assertThat(p.getSegment(i), is(Path.MOVE_TO));
        double x = p.getSegmentParameter(i, 0);
        double y = p.getSegmentParameter(i, 1);
        assertThat(x, is(closeTo(100.0, ERR)));
        assertThat(y, is(closeTo(.5, ERR)));

        i++;
        assertThat(p.getSegment(i), is(Path.MOVE_TO));
        x = p.getSegmentParameter(i, 0);
        y = p.getSegmentParameter(i, 1);
        assertThat(x, is(closeTo(50.0, ERR)));
        assertThat(y, is(closeTo(2.0, ERR)));

        i++;
        assertThat(p.getSegment(i), is(Path.LINE_TO));
        x = p.getSegmentParameter(i, 0);
        y = p.getSegmentParameter(i, 1);
        assertThat(x, is(closeTo(60.0, ERR)));
        assertThat(y, is(closeTo(50.0, ERR)));
    }

    @Test
    public void constructWithL() {
        Path p = new Path("L 100 .5  l-50,15e-1 10.0 48", mPath);

        int i = 0;
        assertThat(p.getSegment(i), is(Path.LINE_TO));
        double x = p.getSegmentParameter(i, 0);
        double y = p.getSegmentParameter(i, 1);
        assertThat(x, is(closeTo(100.0, ERR)));
        assertThat(y, is(closeTo(.5, ERR)));

        i++;
        assertThat(p.getSegment(i), is(Path.LINE_TO));
        x = p.getSegmentParameter(i, 0);
        y = p.getSegmentParameter(i, 1);
        assertThat(x, is(closeTo(50.0, ERR)));
        assertThat(y, is(closeTo(2.0, ERR)));

        i++;
        assertThat(p.getSegment(i), is(Path.LINE_TO));
        x = p.getSegmentParameter(i, 0);
        y = p.getSegmentParameter(i, 1);
        assertThat(x, is(closeTo(60.0, ERR)));
        assertThat(y, is(closeTo(50.0, ERR)));
    }

    @Test
    public void constructWithHV() {
        Path p = new Path("H 100 V.5  h-50,15e-1 v10.0 48", mPath);

        int i = 0;
        assertThat(p.getSegment(i), is(Path.LINE_TO));
        double x = p.getSegmentParameter(i, 0);
        double y = p.getSegmentParameter(i, 1);
        assertThat(x, is(closeTo(100.0, ERR)));
        assertThat(y, is(closeTo(0.0, ERR)));

        i++;
        assertThat(p.getSegment(i), is(Path.LINE_TO));
        x = p.getSegmentParameter(i, 0);
        y = p.getSegmentParameter(i, 1);
        assertThat(x, is(closeTo(100.0, ERR)));
        assertThat(y, is(closeTo(0.5, ERR)));

        i++;
        assertThat(p.getSegment(i), is(Path.LINE_TO));
        x = p.getSegmentParameter(i, 0);
        y = p.getSegmentParameter(i, 1);
        assertThat(x, is(closeTo(50.0, ERR)));
        assertThat(y, is(closeTo(0.5, ERR)));

        i++;
        assertThat(p.getSegment(i), is(Path.LINE_TO));
        x = p.getSegmentParameter(i, 0);
        y = p.getSegmentParameter(i, 1);
        assertThat(x, is(closeTo(51.5, ERR)));
        assertThat(y, is(closeTo(0.5, ERR)));

        i++;
        assertThat(p.getSegment(i), is(Path.LINE_TO));
        x = p.getSegmentParameter(i, 0);
        y = p.getSegmentParameter(i, 1);
        assertThat(x, is(closeTo(51.5, ERR)));
        assertThat(y, is(closeTo(10.5, ERR)));

        i++;
        assertThat(p.getSegment(i), is(Path.LINE_TO));
        x = p.getSegmentParameter(i, 0);
        y = p.getSegmentParameter(i, 1);
        assertThat(x, is(closeTo(51.5, ERR)));
        assertThat(y, is(closeTo(58.5, ERR)));
    }

    @Test
    public void constructWithA() {
        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                assertThat((double) ((Float) args[1]), is(closeTo(180.0, ERR_DEG)));
                assertThat((double) ((Float) args[2]), is(closeTo(-90.0, ERR_DEG)));
                return null;
            }
        }).when(mPath).arcTo(any(android.graphics.RectF.class), anyFloat(), anyFloat());
        Path p = new Path("M 125,75 a100,50 0 0,0 100,50", mPath);

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                assertThat((double) ((Float) args[1]), is(closeTo(-90.0, ERR_DEG)));
                assertThat((double) ((Float) args[2]), is(closeTo(90.0, ERR_DEG)));
                return null;
            }
        }).when(mPath).arcTo(any(android.graphics.RectF.class), anyFloat(), anyFloat());
        p = new Path("M125,75 a100,50 0 0,1 100,50", mPath);


        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                assertThat((double) ((Float) args[1]), is(closeTo(-90.0, ERR_DEG)));
                assertThat((double) ((Float) args[2]), is(closeTo(-270.0, ERR_DEG)));
                return null;
            }
        }).when(mPath).arcTo(any(android.graphics.RectF.class), anyFloat(), anyFloat());
        p = new Path("M125,75 a100,50 0 1,0 100,50", mPath);


        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                assertThat((double) ((Float) args[1]), is(closeTo(180.0, ERR_DEG)));
                assertThat((double) ((Float) args[2]), is(closeTo(270.0, ERR_DEG)));
                return null;
            }
        }).when(mPath).arcTo(any(android.graphics.RectF.class), anyFloat(), anyFloat());
        p = new Path("M125,75 a100,50 0 1,1 100,50", mPath);

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                assertThat((double) ((Float) args[1]), is(closeTo(-60.0, ERR_DEG)));
                assertThat((double) ((Float) args[2]), is(closeTo(-90.0, ERR_DEG)));
                return null;
            }
        }).when(mPath).arcTo(any(android.graphics.RectF.class), anyFloat(), anyFloat());
        p = new Path("M150,56.69872981 A100,50 0 0,0 13.397459621556,75", mPath);

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                assertThat((double) ((Float) args[1]), is(closeTo(-60.0, ERR_DEG)));
                assertThat((double) ((Float) args[2]), is(closeTo(270.0, ERR_DEG)));
                return null;
            }
        }).when(mPath).arcTo(any(android.graphics.RectF.class), anyFloat(), anyFloat());
        p = new Path("M150,56.69872981 A100,50 0 1,1 13.397459621556,75", mPath);
    }
}