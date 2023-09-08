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

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import static javafx.application.Platform.runLater;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 *
 * @author Arnaud Hamon-Keromen
 */
public class TetrisController implements Initializable, GridView {

    private static final Logger LOG = Logger.getGlobal();

    private final Grid grid = new Grid();
    private Timeline timeline;
    @FXML
    private Label scoreLabel, nbLinesLabel, levelLabel;
    @FXML
    private Pane drawingArea, previewArea;
    @FXML
    private Button startButton, stopButton;
    @FXML
    private ToggleButton pauseButton;
    //
    private int score, nbLines, level;
    private boolean canInteract;
    private boolean paused;

    /**
     *
     * @param event action event
     */
    @FXML
    public void startAction(ActionEvent event) {
        LOG.log(Level.INFO, "Start Game on event :: {0}", event);
        startGame();
    }

    /**
     *
     * @param event action event
     */
    @FXML
    public void stopAction(ActionEvent event) {
        LOG.log(Level.INFO, "Stop Game on event :: {0}", event);
        stopGame();
    }

    /**
     *
     * @param event action event
     */
    @FXML
    public void pauseAction(ActionEvent event) {
        LOG.log(Level.INFO, "Pause Game on event :: {0}", event);
        pauseGame();
    }

    /**
     *
     * @param event key event
     */
    @FXML
    public void processKeyTyped(KeyEvent event) {
        if (canInteract) {
            var keyTyped = event.getCharacter();
            switch (keyTyped) {
                case "s", "l", "S", "L" -> grid.moveDown();
                case "q", "k", "Q", "K" -> grid.moveLeft();
                case "d", "m", "D", "M" -> grid.moveRight();
                case "a", "i", "A", "I" -> grid.rotateCounterClockWise();
                case "e", "p", "E", "P" -> grid.rotateClockWise();
                default -> {
                }
            }
        }
    }

    /**
     *
     * @param url URL
     * @param rb resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        score = -1;
        nbLines = -1;
        level = -1;
        var delay = 1000;
        canInteract = false;
        paused = false;
        pauseButton.setDisable(true);
        startButton.setDisable(false);
        stopButton.setDisable(true);
        //
        timeline = new Timeline(new KeyFrame(Duration.millis(delay), ae -> {
            LOG.log(Level.FINEST, "new keyfrmae action :: {0}", ae);
            canInteract = false;
            grid.forceMoveDown();
            canInteract = true;
        }));
        timeline.setRate(1000 / (1000 + 10 * (level - 1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        //TODO add pp change
        var gridDrawing = new GridDrawing(drawingArea, grid);
        var previewDrawing = new PreviewDrawing(previewArea, grid);
        grid.addView(previewDrawing);
        grid.addView(gridDrawing);
        grid.addView(this);
    }

    private void startGame() {
        runLater(() -> {
            grid.init();
            grid.start();
            pauseButton.setDisable(false);
            startButton.setDisable(true);
            stopButton.setDisable(false);
            timeline.play();
            canInteract = true;
        });
    }

    private void pauseGame() {
        paused = pauseButton.isSelected();
        if (paused) {
            timeline.stop();
        } else {
            timeline.play();
        }
    }

    private void stopGame() {
        timeline.stop();
        pauseButton.setDisable(true);
        startButton.setDisable(false);
        stopButton.setDisable(true);
        grid.init();
    }

    /**
     * ends the game
     */
    public void endGame() {
        timeline.stop();
        pauseButton.setDisable(false);
        startButton.setDisable(true);
        stopButton.setDisable(false);
        runLater(() -> scoreLabel.setText("" + score + " :: GAME OVER"));
    }

    /**
     *
     */
    @Override
    public void update() {
        runLater(() -> {
            if (score != grid.getScore()) {
                score = grid.getScore();
                scoreLabel.setText("" + score);
            }
            if (nbLines != grid.getNbLines()) {
                nbLines = grid.getNbLines();
                nbLinesLabel.setText("" + nbLines);
            }
            if (level != grid.getLevel()) {
                level = grid.getLevel();
                timeline.setRate(1000 / (1000 + 10 * (level - 1)));
                levelLabel.setText("" + nbLines);
            }
        });
    }
}
