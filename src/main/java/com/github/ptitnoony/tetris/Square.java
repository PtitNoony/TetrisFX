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

import javafx.scene.paint.Color;

/**
 *
 * @author Arnaud Hamon-Keromen
 */
public class Square extends Piece {

    /**
     * piece color
     */
    public static final Color SQUARE_COLOR = Color.GREEN;
    private static final int[][] SQUARE_DATA = {
        {0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0},
        {0, 0, 2, 1, 0},
        {0, 0, 1, 1, 0},
        {0, 0, 0, 0, 0}};

    /**
     *
     * @param g grid
     */
    public Square(Grid g) {
        super(g);
        setRotation(MatrixRotation.ONE);
    }

    /**
     *
     * @param rotation rotation matrix
     */
    @Override
    protected final void setRotation(MatrixRotation rotation) {
        setMyRotation(rotation);
        switch (getMyRotation()) {
            case ONE:
            case TWO:
            case THREE:
            case FOUR:
                setCurrentMatrix(SQUARE_DATA.clone());
                break;
            default:
                throw new IllegalArgumentException("" + getMyRotation());
        }
    }

    /**
     *
     * @return the square piece code
     */
    @Override
    public int getCode() {
        return Piece.SQUARE_CODE;
    }
}
