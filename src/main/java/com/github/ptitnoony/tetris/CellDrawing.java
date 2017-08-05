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
package com.github.ptitnoony.tetris;

import static javafx.application.Platform.runLater;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Arnaud Hamon-Keromen
 */
public class CellDrawing {

    private int pieceCode;
    private Rectangle background;
    private Circle foreground;
    private Group mainNode;
    private static final Color DEFAULT_BACKGROUND_COLOR = Color.BLACK;

    /**
     *
     * @param i row
     * @param j column
     * @param elementCode code of the element
     */
    public CellDrawing(int i, int j, int elementCode) {
        double posX, posY;
        int row, column;
        row = i;
        column = j;
        posX = (column) * GridDrawing.DEFAULT_GRID_ELEMENT_SIZE;
        posY = (row) * GridDrawing.DEFAULT_GRID_ELEMENT_SIZE;
        foreground = new Circle((GridDrawing.DEFAULT_GRID_ELEMENT_SIZE - 2.0) / 2.0 - 1.0);
        foreground.setCenterX(posX + 0.5 * GridDrawing.DEFAULT_GRID_ELEMENT_SIZE);
        foreground.setCenterY(posY + 0.5 * GridDrawing.DEFAULT_GRID_ELEMENT_SIZE);
        background = new Rectangle();
        background.setHeight(GridDrawing.DEFAULT_GRID_ELEMENT_SIZE);
        background.setWidth(GridDrawing.DEFAULT_GRID_ELEMENT_SIZE);
        background.setX(posX);
        background.setY(posY);
        mainNode = new Group();
        mainNode.getChildren().add(background);
        mainNode.getChildren().add(foreground);
        pieceCode = -2;
        setElement(elementCode);
    }

    /**
     *
     * @param i row
     * @param j column
     */
    public CellDrawing(int i, int j) {
        this(i, j, Piece.EMTY_CODE);
    }

    /**
     *
     * @return mainNode
     */
    public Node getNode() {
        return mainNode;
    }

    /**
     *
     * @param elementCode code of the element
     */
    public final void setElement(int elementCode) {
        if (pieceCode != elementCode) {
            pieceCode = elementCode;
            runLater(this::setElement);
        }
    }

    private void setElement() {
        switch (pieceCode) {
            case Piece.EMTY_CODE:
                background.setFill(null);
                foreground.setFill(null);
                break;
            case Piece.GROUND_CODE:
                background.setFill(DEFAULT_BACKGROUND_COLOR);
                foreground.setFill(DEFAULT_BACKGROUND_COLOR);
                break;
            case Piece.I_CODE:
                background.setFill(DEFAULT_BACKGROUND_COLOR);
                foreground.setFill(I.I_COLOR);
                break;
            case Piece.L_CODE:
                background.setFill(DEFAULT_BACKGROUND_COLOR);
                foreground.setFill(L.L_COLOR);
                break;
            case Piece.L_INVERTED_CODE:
                background.setFill(DEFAULT_BACKGROUND_COLOR);
                foreground.setFill(InvertedL.INVERTED_L_COLOR);
                break;
            case Piece.SQUARE_CODE:
                background.setFill(DEFAULT_BACKGROUND_COLOR);
                foreground.setFill(Square.SQUARE_COLOR);
                break;
            case Piece.T_CODE:
                background.setFill(DEFAULT_BACKGROUND_COLOR);
                foreground.setFill(T.T_COLOR);
                break;
            case Piece.N_CODE:
                background.setFill(DEFAULT_BACKGROUND_COLOR);
                foreground.setFill(N.N_COLOR);
                break;
            case Piece.N_INVERTED_CODE:
                background.setFill(DEFAULT_BACKGROUND_COLOR);
                foreground.setFill(InvertedN.INVERTED_N_COLOR);
                break;
            default:
                background.setFill(Color.MAGENTA);
                foreground.setFill(Color.MAGENTA);
                break;
        }
    }
}
