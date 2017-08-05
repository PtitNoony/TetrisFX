/*
 * Copyright (C) 2014 Arnaud
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.ptitnoony.tetris;

import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.Pane;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Arnaud
 */
public class GridDrawingTest {

    public GridDrawingTest() {
        JFXPanel panel = new JFXPanel();
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of update method, of class GridDrawing.
     */
    @Test
    public void testInstance() {
        Grid g = new Grid();
        Pane p = new Pane();
        GridDrawing instance = new GridDrawing(p, g);
        instance.update();
        //
        try {
            instance = new GridDrawing(null, g);
            instance.update();
        } catch (Exception e) {
        }
        //
        try {
            instance = new GridDrawing(p, null);
            instance.update();
        } catch (Exception e) {
        }
        //
        try {
            instance = new GridDrawing(null, null);
            instance.update();
        } catch (Exception e) {
        }
    }

}
