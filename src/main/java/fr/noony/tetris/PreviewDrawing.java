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

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Arnaud Hamon-Keromen
 */
public class PreviewDrawing implements GridView {

    /**
     * Size of squares for block preview
     */
    public static final int DEFAULT_PREVIEW_ELEMENT_SIZE = 20;
    private CellDrawing[][] cellDrawings;
    private final Grid grid;

    /**
     *
     * @param parent the parent to put the previewGrid in
     * @param g the grid related to the preview
     */
    public PreviewDrawing(Pane parent, Grid g) {
        grid = g;
        if ((parent != null) && (grid != null)) {
            Rectangle background = new Rectangle();
            background.setHeight(100);
            background.setWidth(100);
            background.setStroke(Color.BLACK);
            background.setFill(null);
            parent.getChildren().add(background);
            cellDrawings = new CellDrawing[5][5];
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    CellDrawing cellDrawing = new CellDrawing(i, j, grid.getNextItem().getCodeAt(i, j));
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
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                cellDrawings[i][j].setElement(grid.getNextItem().getCodeAt(i, j));
            }
        }
    }
}
