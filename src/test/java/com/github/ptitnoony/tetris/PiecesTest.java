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

import java.awt.Point;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import static java.util.logging.Logger.getLogger;
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
public class PiecesTest {

    private Piece instance;
    private final Grid grid = new Grid();

    public PiecesTest() {
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

    @Test
    public void testPieces() {
        pieceOfClass(I.class);
        pieceOfClass(L.class);
        pieceOfClass(T.class);
        pieceOfClass(N.class);
        pieceOfClass(InvertedL.class);
        pieceOfClass(InvertedN.class);
        pieceOfClass(Square.class);
    }

    private void pieceOfClass(Class classe) {
        try {
            Constructor<?> constructor = classe.getConstructor(Grid.class);
            instance = (Piece) constructor.newInstance(grid);
            Point center = instance.getCenterPosition();
            Assert.assertNotNull(center);
            int code = instance.getCode();
            Assert.assertNotNull(code);
            int[][] matrix;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    instance.getCodeAt(i, j);
                }
            }
            Grid g = instance.getGrid();
            Assert.assertNotNull(g);
            instance.moveDown();
            instance.moveLeft();
            instance.moveRight();
            for (int i = 0; i < 5; i++) {
                instance.turnClockWise();
                matrix = instance.getCurrentMatrix();
                Assert.assertNotNull(matrix);
            }
            for (int i = 0; i < 5; i++) {
                instance.turnCounterClockWise();
                matrix = instance.getCurrentMatrix();
                Assert.assertNotNull(matrix);
            }

        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            getLogger(PiecesTest.class.getName()).log(Level.SEVERE, "Exception while creating {0} :: {1}", new Object[]{classe.getName(), e});
        }
    }
}
