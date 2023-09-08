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

import static com.github.ptitnoony.tetris.Grid.DEFAULT_COLUMN_NUMBER;
import static com.github.ptitnoony.tetris.Grid.DEFAULT_ROW_NUMBER;
import java.awt.Point;
import java.util.logging.Level;
import static java.util.logging.Logger.getLogger;

/**
 *
 * @author Arnaud Hamon-Keromen
 */
public abstract class Piece {

    /**
     * default starting grid position of the new piece to appear
     */
    public static final Point DEFAULT_INIT_POSITION = new Point(0, 4);
    /**
     * code for empty square
     */
    public static final int EMTY_CODE = 0;
    /**
     * code for ground square
     */
    public static final int GROUND_CODE = 1;
    /**
     * code for square occupied by a T piece
     */
    public static final int T_CODE = 2;
    /**
     * code for square occupied by a L piece
     */
    public static final int L_CODE = 3;
    /**
     * code for square occupied by an inverted L piece
     */
    public static final int L_INVERTED_CODE = 4;
    /**
     * code for square occupied by a N piece
     */
    public static final int N_CODE = 5;
    /**
     * code for square occupied by an inverted N piece
     */
    public static final int N_INVERTED_CODE = 6;
    /**
     * code for square occupied by an I piece
     */
    public static final int I_CODE = 7;
    /**
     * code for square background
     */
    public static final int SQUARE_CODE = 8;
    //
    private Point centerPosition;
    //
    private MatrixRotation myRotation = MatrixRotation.ONE;
    private final Grid grid;
    private int[][] currentMatrix;

    /**
     * rotation matrices
     */
    public enum MatrixRotation {

        /**
         * one quarter right
         */
        ONE,
        /**
         * two quarters right
         */
        TWO,
        /**
         * three quarters right
         */
        THREE,
        /**
         * four quarters right
         */
        FOUR
    }

    /**
     *
     * @param g grid to host the piece
     */
    public Piece(Grid g) {
        centerPosition = new Point(DEFAULT_INIT_POSITION);
        currentMatrix = new int[5][5];
        grid = g;
    }

    /**
     *
     * @return the grid hosting the piece
     */
    public final Grid getGrid() {
        return grid;
    }

    /**
     *
     * @return the current rotation matrix of the piece
     */
    public final MatrixRotation getMyRotation() {
        return myRotation;
    }

    /**
     *
     * @return the center position of the piece
     */
    public Point getCenterPosition() {
        return centerPosition;
    }

    /**
     *
     * @param centerPosition new center position for the piece
     */
    protected void setCenterPosition(Point centerPosition) {
        this.centerPosition = centerPosition;
    }

    /**
     * Only impacts private attributes
     *
     * @param myRotation rotation matrix
     */
    public void setMyRotation(MatrixRotation myRotation) {
        this.myRotation = myRotation;
    }

    /**
     *
     * @param rotation rotation matrix to be applied
     */
    protected abstract void setRotation(MatrixRotation rotation);

    /**
     *
     * @return the current matrix
     */
    public int[][] getCurrentMatrix() {
        return currentMatrix.clone();
    }

    /**
     *
     * @param newMatrix matrix to be applied
     */
    protected void setCurrentMatrix(int[][] newMatrix) {
        currentMatrix = newMatrix.clone();
    }

    /**
     *
     * @return the code of the piece
     */
    public abstract int getCode();

    /**
     *
     * @param i row index
     * @param j column index
     * @return the code of the piece at the corresponding cell
     */
    public int getCodeAt(int i, int j) {
        if (currentMatrix[i][j] != EMTY_CODE) {
            return getCode();
        } else {
            return EMTY_CODE;
        }
    }

    /**
     * turns the piece clockwise
     */
    public final void turnClockWise() {
        switch (myRotation) {
            case ONE -> setRotation(MatrixRotation.TWO);
            case TWO -> setRotation(MatrixRotation.THREE);
            case THREE -> setRotation(MatrixRotation.FOUR);
            case FOUR -> setRotation(MatrixRotation.ONE);
            default -> throw new IllegalArgumentException("" + myRotation);
        }
    }

    /**
     * turns the piece counter clockwise
     */
    public final void turnCounterClockWise() {
        switch (myRotation) {
            case ONE -> setRotation(MatrixRotation.FOUR);
            case TWO -> setRotation(MatrixRotation.ONE);
            case THREE -> setRotation(MatrixRotation.TWO);
            case FOUR -> setRotation(MatrixRotation.THREE);
            default -> throw new IllegalArgumentException("" + myRotation);
        }
    }

    /**
     *
     * @return if the piece will hit at next position
     */
    public final boolean testHit() {
        return hitAtNextPosition(centerPosition);
    }

    /**
     *
     * @return if the piece will hit down at next position
     */
    public final boolean testHitDown() {
        return hitAtNextPosition(new Point(centerPosition.x + 1, centerPosition.y));
    }

    /**
     *
     * @return if the piece will hit left at next position
     */
    public final boolean testHitLeft() {
        return hitAtNextPosition(new Point(centerPosition.x, centerPosition.y - 1));
    }

    /**
     *
     * @return if the piece will hit right at next position
     */
    public final boolean testHitRight() {
        return hitAtNextPosition(new Point(centerPosition.x, centerPosition.y + 1));
    }

    /**
     *
     * @return if the piece will hit clockwise at next position
     */
    public final boolean testHitClockWise() {
        turnClockWise();
        boolean hitClockWise = hitAtNextPosition(centerPosition);
        turnCounterClockWise();
        return hitClockWise;
    }

    /**
     *
     * @return if the piece will hit counter clockwise at next position
     */
    public final boolean testHitCounterClockWise() {
        turnCounterClockWise();
        var hitClockWise = hitAtNextPosition(centerPosition);
        turnClockWise();
        return hitClockWise;
    }

    private boolean hitAtNextPosition(Point nextPosition) {
        var hit = false;
        int desI;
        int desJ;
        try {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    desI = i + nextPosition.x - 2;
                    desJ = j + nextPosition.y - 2;
                    if ((desI >= 0) && (desI < DEFAULT_ROW_NUMBER + 1) && ((desJ >= 0)) && ((desJ < DEFAULT_COLUMN_NUMBER))) {
                        if ((!grid.isCellEmpty(desI, desJ) && (currentMatrix[i][j] != EMTY_CODE))) {
                            hit = true;
                            break;
                        }
                    } else if ((desJ < 0) || (desJ >= DEFAULT_COLUMN_NUMBER)) {
                        if ((currentMatrix[i][j] != EMTY_CODE)) {
                            hit = true;
                            break;
                        }
                    }
                }
            }
            return hit;
        } catch (Exception e) {
            getLogger(Piece.class.getName()).log(Level.SEVERE, " hitAtNextPosition {0}", e);
            return false;
        }

    }

    /**
     * moves the piece left
     */
    public void moveLeft() {
        centerPosition.translate(0, -1);
    }

    /**
     * moves the piece down
     */
    public void moveDown() {
        // i = row
        // j = column
        centerPosition.translate(1, 0);
    }

    /**
     * moves the piece right
     */
    public void moveRight() {
        centerPosition.translate(0, 1);
    }
}
