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

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Arnaud
 */
public class CellDrawingTest {

    public CellDrawingTest() {
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
     * Test of getNode method, of class CellDrawing.
     */
    @Test
    public void testGetNode() {
        CellDrawing instance = new CellDrawing(0, 0);
        Node result = instance.getNode();
        Assert.assertNotNull(result);
    }

    /**
     * Test of setElement method, of class CellDrawing.
     */
    @Test
    public void testSetElement() {
        try {
            CellDrawing instance = new CellDrawing(0, 0, 0);
            instance.setElement(Piece.EMTY_CODE);
            Thread.sleep(2);
            instance.setElement(Piece.GROUND_CODE);
            Thread.sleep(2);
            instance.setElement(Piece.I_CODE);
            Thread.sleep(2);
            instance.setElement(Piece.L_CODE);
            Thread.sleep(2);
            instance.setElement(Piece.L_INVERTED_CODE);
            Thread.sleep(2);
            instance.setElement(Piece.N_CODE);
            Thread.sleep(2);
            instance.setElement(Piece.N_INVERTED_CODE);
            Thread.sleep(2);
            instance.setElement(Piece.SQUARE_CODE);
            Thread.sleep(2);
            instance.setElement(Piece.T_CODE);
            Thread.sleep(2);
            instance.setElement(-1);
            Thread.sleep(2);
        } catch (InterruptedException ex) {
            Logger.getLogger(CellDrawingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
