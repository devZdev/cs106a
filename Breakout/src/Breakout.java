/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

    /** Width and height of application window in pixels */
    public static final int APPLICATION_WIDTH = 400;
    public static final int APPLICATION_HEIGHT = 600;

    /** Dimensions of game board (usually the same) */
    private static final int WIDTH = APPLICATION_WIDTH;
    private static final int HEIGHT = APPLICATION_HEIGHT;

    /** Dimensions of the paddle */
    private static final int PADDLE_WIDTH = 60;
    private static final int PADDLE_HEIGHT = 10;

    /** Offset of the paddle up from the bottom */
    private static final int PADDLE_Y_OFFSET = 30;

    /** Number of bricks per row */
    private static final int NBRICKS_PER_ROW = 10;

    /** Number of rows of bricks */
    private static final int NBRICK_ROWS = 10;

    /** Separation between bricks */
    private static final int BRICK_SEP = 4;

    /** Width of a brick */
    private static final int BRICK_WIDTH =
            (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

    /** Height of a brick */
    private static final int BRICK_HEIGHT = 8;

    /** Radius of the ball in pixels */
    private static final int BALL_RADIUS = 10;

    /** Offset of the top brick row from the top */
    private static final int BRICK_Y_OFFSET = 70;

    /** Number of turns */
    private static final int NTURNS = 3;

/* Method: run() */
    /** Runs the Breakout program. */
    public void init() {
        setUpWorld();
        addPaddle();
        addMouseListeners();
    }

    public void mousePressed(MouseEvent e) {
        lastPoint = new GPoint(e.getX(), e.getY());
        paddle = getElementAt(lastPoint);
    }

    public void mouseDragged(MouseEvent e) {
        if(paddle != null) {
            System.out.print(e.getY());
            System.out.print(lastPoint.getY());
            paddle.move(e.getX() - lastPoint.getX(), e.getY() - lastPoint.getY());
            lastPoint = new GPoint(e.getPoint());
        }
    }

    public void setUpWorld(){
        int ypos = BRICK_Y_OFFSET;
        int row = 0;
        for (int i = NBRICK_ROWS; i > 0; i--) {
            row++;
            Color brickColor = getBrickColor(row);
            int xpos = 0;
            for(int k = NBRICKS_PER_ROW; k > 0; k--){
                GRect brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
                brick.setColor(brickColor);
                brick.setFillColor(brickColor);
                brick.setFilled(true);
                add(brick, xpos, ypos);
                xpos = (xpos + BRICK_WIDTH + BRICK_SEP);
                pause(10);
            }
            ypos = ypos + BRICK_HEIGHT + BRICK_SEP;
        }
    }

    public void addPaddle() {
        int ypos = getHeight() - (PADDLE_Y_OFFSET + PADDLE_HEIGHT);
        paddleY = ypos;
        int xpos = (getWidth()-PADDLE_WIDTH)/2;
        GRect paddle = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
        Color black = new Color(0, 0, 0);
        paddle.setColor(black);
        paddle.setFillColor(black);
        paddle.setFilled(true);
        add(paddle, xpos, paddleY);

    }


    private Color getBrickColor(int row) {
        if (row < 3){
            Color red = new Color(255, 0, 0);
            return red;
        } else if (row < 5) {
            Color orange = new Color(255, 127, 0);
            return orange;
        } else if (row < 7) {
            Color yellow = new Color(255, 255, 0);
            return yellow;
        } else if (row < 9) {
            Color green = new Color(0, 255, 0);
            return green;
        } else {
            Color cyan = new Color(0, 0, 255);
            return cyan;
        }
    }

    private GPoint lastPoint;
    private GObject paddle;
    private int paddleY;

}
