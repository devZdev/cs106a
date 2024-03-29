/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

import java.awt.*;
import java.awt.event.MouseEvent;

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


    /** Runs the Breakout program. */
    public void run() {
        setup();
        addBall();
        while(ball.getX() < APPLICATION_WIDTH){
            moveBall();
            checkForCollision();
            pause(10.0);
        }
    }

    public void setup() {
        setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
        pause(10.0);
        int y = BRICK_Y_OFFSET;
        int row = 0;
        for (int i = NBRICK_ROWS; i > 0; i--) {
            row++;
            Color brickColor = getBrickColor(row);
            int x = 0;
            for(int k = NBRICKS_PER_ROW; k > 0; k--){
                GRect brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
                brick.setColor(brickColor);
                brick.setFillColor(brickColor);
                brick.setFilled(true);
                add(brick, x, y);
                x = (x + BRICK_WIDTH + BRICK_SEP);
            }
            y = y + BRICK_HEIGHT + BRICK_SEP;
        }
        addPaddle();
        addMouseListeners();
        vx = rgen.nextDouble(1.0, 3.0);
        if(rgen.nextBoolean(0.5)) {
            vx = -vx;
        }
        vy = 6.0;
    }

    private void moveBall () {
         ball.move(vx, vy);
    }

    private void checkForCollision() {
        GObject obj = getElementAt(ball.getX(), ball.getY());
        if(obj != null) {
            vy = -vy;
            if(obj != paddle) {
                remove(obj);
            }
        }
        if(ball.getX() < 0 || ball.getX() > (APPLICATION_WIDTH - BALL_RADIUS)) {
            vx = -vx;
        }
        if(ball.getY() < 0 || ball.getY() > APPLICATION_HEIGHT) {
            vy = -vy;
        }
    }

    public void mousePressed(MouseEvent e) {
        lastPoint = new GPoint(e.getX(), paddleY);
        paddle = getElementAt(lastPoint);
    }

    public void mouseDragged(MouseEvent e) {
        if(paddle != null && e.getX() >= 0 && e.getX() <= APPLICATION_WIDTH) {
            paddle.move(e.getX() - lastPoint.getX(), paddleY - lastPoint.getY());
            lastPoint = new GPoint(e.getX(), paddleY);
        }
    }

    private void addPaddle() {
        int y = getHeight() - (PADDLE_Y_OFFSET + PADDLE_HEIGHT);
        paddleY = y;
        int x = (getWidth()-PADDLE_WIDTH)/2;
        GRect paddle = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
        paddle.setColor(black);
        paddle.setFillColor(black);
        paddle.setFilled(true);
        add(paddle, x, paddleY);

    }

    private void addBall() {
        ball = new GOval(BALL_RADIUS, BALL_RADIUS);
        ball.setFilled(true);
        ball.setFillColor(black);
        ball.setFilled(true);
        add(ball, (APPLICATION_WIDTH - ball.getWidth())/2, (APPLICATION_HEIGHT - ball.getHeight())/2);
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

    private Color black = new Color(0, 0, 0);
    private GPoint lastPoint;
    private GObject paddle;
    private int paddleY;
    private GOval ball;
    private double vx, vy;
    private RandomGenerator rgen = RandomGenerator.getInstance();

}
