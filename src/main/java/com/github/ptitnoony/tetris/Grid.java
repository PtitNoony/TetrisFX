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

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Arnaud Hamon-Keromen
 */
public class Grid {

    /**
     * number of row composing the grid
     */
    public static final int DEFAULT_ROW_NUMBER = 24;
    /**
     * number of columns composing the grid
     */
    public static final int DEFAULT_COLUMN_NUMBER = 10;
    //
    private int[][] grid;
    private int[][] gridRender;
    private Piece currentPiece;
    private final List<GridView> views;
    private boolean started;
    private Random randomGenerator;
    private int score, nbLines, level;
    private Piece nextPiece;
    private final int[] randomIntValues;
    private int currentPieceId;

    /**
     *
     */
    public Grid() {
        grid = new int[DEFAULT_ROW_NUMBER + 1][DEFAULT_COLUMN_NUMBER];
        gridRender = new int[DEFAULT_ROW_NUMBER + 1][DEFAULT_COLUMN_NUMBER];
        started = false;
        randomGenerator = new Random(13);
        score = 0;
        nbLines = 0;
        level = 1;
        randomIntValues = new int[1000000];
        for (int i = 0; i < 1000000; i++) {
            randomIntValues[i] = randomGenerator.nextInt(7 * 4);
        }
        currentPieceId = 0;
        createNewPiece();
        for (int i = 0; i < DEFAULT_ROW_NUMBER; i++) {
            for (int j = 0; j < DEFAULT_COLUMN_NUMBER; j++) {
                grid[i][j] = Piece.EMTY_CODE;
            }
        }
        for (int j = 0; j < DEFAULT_COLUMN_NUMBER; j++) {
            grid[DEFAULT_ROW_NUMBER][j] = Piece.GROUND_CODE;
        }
        views = new LinkedList<>();
        cloneGrid();
    }

    /**
     * method to start a game
     */
    public void start() {
        if (!started) {
            addNewPiece();
            started = true;
        }
    }

    /**
     *
     * @return a string version of the grid for debug or console display
     */
    @Override
    public String toString() {
        var sb = new StringBuilder();
        for (int i = 0; i < DEFAULT_ROW_NUMBER + 1; i++) {
            for (int j = 0; j < DEFAULT_COLUMN_NUMBER; j++) {
                sb.append(gridRender[i][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private void updateGridRenderer() {
        cloneGrid();
        var pData = currentPiece.getCurrentMatrix();
        var pPosition = currentPiece.getCenterPosition();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                // -2  -2
                int desI = i + pPosition.x - 2;
                int desJ = j + pPosition.y - 2;
                if ((desI >= 0) && (desI < DEFAULT_ROW_NUMBER) && ((desJ >= 0)) && ((desJ < DEFAULT_COLUMN_NUMBER))) {
                    if (pData[i][j] != Piece.EMTY_CODE) {
                        gridRender[desI][desJ] = currentPiece.getCode();
                    }
                }
            }
        }
        updateViews();
    }

    /**
     *
     * @param row the row index
     * @param column the column index
     * @return if the cell is empty
     */
    public boolean isCellEmpty(int row, int column) {
        return grid[row][column] == Piece.EMTY_CODE;
    }

    /**
     *
     * @param row the row index
     * @param column the column index
     * @return the piece code of the cell
     */
    public int getCodeAt(int row, int column) {
        return gridRender[row][column];
    }

    /**
     *
     * @param view gridview to be added
     */
    public void addView(GridView view) {
        views.add(view);
    }

    /**
     *
     * @param view gridview to be removed
     */
    public void removeView(GridView view) {
        views.remove(view);
        view.update();
    }

    private void updateViews() {
        views.stream().forEach(GridView::update);
    }

    /**
     * moves the current piece right
     */
    public void moveRight() {
        if (!currentPiece.testHitRight()) {
            currentPiece.moveRight();
            updateGridRenderer();
        }
    }

    /**
     * moves the current piece left
     */
    public void moveLeft() {
        if (!currentPiece.testHitLeft()) {
            currentPiece.moveLeft();
            updateGridRenderer();
        }
    }

    /**
     * moves the current piece down
     */
    public void moveDown() {
        if (!currentPiece.testHitDown()) {
            currentPiece.moveDown();
            updateGridRenderer();
        }
    }

    private void createNewPiece() {
        var randomInt = randomIntValues[currentPieceId];
        currentPieceId++;
        var randowPieceType = randomInt / 7;
        var randomRotate = randomInt - 7 * randowPieceType;
        nextPiece = switch (randowPieceType) {
            case 0 -> new T(this);
            case 1 -> new I(this);
            case 2 -> new InvertedL(this);
            case 3 -> new L(this);
            case 4 -> new N(this);
            case 5 -> new InvertedN(this);
            case 6 -> new Square(this);
            default -> new T(this);
        };
        for (int i = 0; i < randomRotate; i++) {
            nextPiece.turnClockWise();
        }
        if (nextPiece.testHit()) {
            //end of line ...
            //Game over
//            tetrisController.endGame();
        }
    }

    private void addNewPiece() {
        currentPiece = nextPiece;
        createNewPiece();
        updateGridRenderer();
    }

    /**
     * rotates the current piece counter clockwise
     */
    public void rotateCounterClockWise() {
        if (!currentPiece.testHitCounterClockWise()) {
            currentPiece.turnCounterClockWise();
            updateGridRenderer();
        }
    }

    /**
     * rotates the current piece clockwise
     */
    public void rotateClockWise() {
        if (!currentPiece.testHitClockWise()) {
            currentPiece.turnClockWise();
            updateGridRenderer();
        }
    }

    /**
     * moves the currentPiece down and terminates the piece movement if impact
     */
    public void forceMoveDown() {
        if (!currentPiece.testHitDown()) {
            currentPiece.moveDown();
        } else {
            copyToGrid();
            var nbNewLinesCompleted = 0;
            while (checkCompletedLines()) {
                nbNewLinesCompleted++;
            }

            nbLines += nbNewLinesCompleted;
            score += Math.pow(10, nbNewLinesCompleted);
            checkLevel();
            addNewPiece();
        }
        updateGridRenderer();
    }

    private void checkLevel() {
        level = nbLines / 10;
    }

    private void cloneGrid() {
        for (int i = 0; i < DEFAULT_ROW_NUMBER + 1; i++) {
            System.arraycopy(grid[i], 0, gridRender[i], 0, DEFAULT_COLUMN_NUMBER);
        }
    }

    private void copyToGrid() {
        for (int i = 0; i < DEFAULT_ROW_NUMBER + 1; i++) {
            System.arraycopy(gridRender[i], 0, grid[i], 0, DEFAULT_COLUMN_NUMBER);
        }
    }

    private boolean checkCompletedLines() {
        var aChange = false;
        for (int i = 0; i < DEFAULT_ROW_NUMBER; i++) {
            var lineFull = true;
            for (int j = 0; j < DEFAULT_COLUMN_NUMBER; j++) {
                if (gridRender[i][j] == Piece.EMTY_CODE) {
                    lineFull = false;
                    break;
                }
            }
            if (lineFull) {
                copyToGrid();
                removeRow(i);
                return true;
            }
        }
        return aChange;
    }

    private void removeRow(int row) {
        var tempGrid = new int[DEFAULT_ROW_NUMBER + 1][DEFAULT_COLUMN_NUMBER];
        for (int i = 1; i < DEFAULT_ROW_NUMBER + 1; i++) {
            System.arraycopy(gridRender[i], 0, tempGrid[i], 0, DEFAULT_COLUMN_NUMBER);
        }
        for (int i = row; i > 1; i--) {
            System.arraycopy(gridRender[i - 1], 0, tempGrid[i], 0, DEFAULT_COLUMN_NUMBER);
        }
        for (int j = 0; j < DEFAULT_COLUMN_NUMBER; j++) {
            tempGrid[0][j] = 0;
        }
        //
        for (int i = 0; i < DEFAULT_ROW_NUMBER + 1; i++) {
            System.arraycopy(tempGrid[i], 0, grid[i], 0, DEFAULT_COLUMN_NUMBER);
        }
        for (int i = 0; i < DEFAULT_ROW_NUMBER + 1; i++) {
            System.arraycopy(tempGrid[i], 0, gridRender[i], 0, DEFAULT_COLUMN_NUMBER);
        }
    }

    /**
     *
     * @return the player's score
     */
    public int getScore() {
        return score;
    }

    /**
     *
     * @return the number of completed lines
     */
    public int getNbLines() {
        return nbLines;
    }

    /**
     *
     * @return the current level
     */
    public int getLevel() {
        return level;
    }

    /**
     * initiates the grid
     */
    public void init() {
        grid = new int[DEFAULT_ROW_NUMBER + 1][DEFAULT_COLUMN_NUMBER];
        gridRender = new int[DEFAULT_ROW_NUMBER + 1][DEFAULT_COLUMN_NUMBER];
        started = false;
        randomGenerator = new Random();
        score = 0;
        nbLines = 0;
        level = 1;
        for (int i = 0; i < DEFAULT_ROW_NUMBER; i++) {
            for (int j = 0; j < DEFAULT_COLUMN_NUMBER; j++) {
                grid[i][j] = Piece.EMTY_CODE;
            }
        }
        for (int j = 0; j < DEFAULT_COLUMN_NUMBER; j++) {
            grid[DEFAULT_ROW_NUMBER][j] = Piece.GROUND_CODE;
        }
        cloneGrid();
        updateViews();
    }

    /**
     *
     * @return the next piece to appear
     */
    public Piece getNextItem() {
        return nextPiece;
    }
}
