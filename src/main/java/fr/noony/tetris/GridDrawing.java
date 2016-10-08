/*
 * Copyright (C) 2014 Arnaud Hamon-Keromen
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
package fr.noony.tetris;

import static fr.noony.tetris.Grid.DEFAULT_COLUMN_NUMBER;
import static fr.noony.tetris.Grid.DEFAULT_ROW_NUMBER;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Arnaud Hamon-Keromen
 */
public class GridDrawing implements GridView {

    /**
     * default grid width in pixels
     */
    public static final int DEFAULT_GRID_AREA_WIDTH = 200;
    /**
     * default grid height in pixels
     */
    public static final int DEFAULT_GRID_AREA_HEIGHT = 500;
    /**
     * default grid square size
     */
    public static final int DEFAULT_GRID_ELEMENT_SIZE = 20;
    private CellDrawing[][] cellDrawings;
    private final Grid grid;

    /**
     *
     * @param parent gridGrawing parent
     * @param g associated grid
     */
    public GridDrawing(Pane parent, Grid g) {
        grid = g;
        if ((parent != null) && (grid != null)) {
            Rectangle background = new Rectangle();
            background.setHeight(DEFAULT_GRID_AREA_HEIGHT);
            background.setWidth(DEFAULT_GRID_AREA_WIDTH);
            background.setStroke(Color.BLACK);
            background.setFill(null);
            parent.getChildren().add(background);
            cellDrawings = new CellDrawing[DEFAULT_ROW_NUMBER + 1][DEFAULT_COLUMN_NUMBER];
            for (int i = 0; i < DEFAULT_ROW_NUMBER + 1; i++) {
                for (int j = 0; j < DEFAULT_COLUMN_NUMBER; j++) {
                    CellDrawing cellDrawing = new CellDrawing(i, j, grid.getCodeAt(i, j));
                    cellDrawings[i][j] = cellDrawing;
                    parent.getChildren().add(cellDrawing.getNode());
                }
            }
        } else {
            throw new IllegalArgumentException("null parameter P1:" + parent + " P2:" + grid);
        }
    }

    /**
     *
     */
    @Override
    public void update() {
        for (int i = 0; i < DEFAULT_ROW_NUMBER + 1; i++) {
            for (int j = 0; j < DEFAULT_COLUMN_NUMBER; j++) {
                cellDrawings[i][j].setElement(grid.getCodeAt(i, j));
            }
        }
    }
}
