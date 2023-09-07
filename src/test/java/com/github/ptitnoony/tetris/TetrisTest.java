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
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Arnaud
 */
public class TetrisTest {

    private TetrisApp tetris;

    public TetrisTest() {
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
     * Test of main method, of class Tetris.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        JFXPanel fXPanel = new JFXPanel();
        Platform.runLater(() -> {
            tetris = new TetrisApp();
            Stage s = new Stage();
            tetris.start(s);
        });
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Logger.getLogger(TetrisTest.class.getName()).log(Level.SEVERE, null, e);
        }
        Platform.runLater(() -> {
            try {
                tetris.stop();
            } catch (Exception ex) {
                Logger.getLogger(TetrisTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Logger.getLogger(TetrisTest.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
