package com.example.backgammon;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;
import android.os.Handler;
import android.os.Looper;



public class DiceRollingSurfaceView extends SurfaceView implements SurfaceHolder.Callback {


    private int finalDie1Index;
    private int finalDie2Index;

    public void setDiceValues(int first, int second) {

        this.finalDie1Index = first-1;
        this.finalDie2Index = second-1;
    }

    public interface DiceAnimationListener {
        void onDiceAnimationFinished();
    }

    private final Random random = new Random();
    private DrawingThread drawingThread;
    private Bitmap[] diceImages; // Array to store dice face images
    private int[] currentDiceFaces = {1, 1}; // The current faces of the two dice
    private boolean isRolling = false; // Flag to control the rolling animation

    // Dice positions for the drop and rolling animations
    private float leftDiceX, leftDiceY, rightDiceX, rightDiceY;
    private float leftDiceTargetY, rightDiceTargetY;
    private boolean isDropping = true; // Whether the dice are in the drop animation phase

    private DiceAnimationListener animationListener; // reference to the game manager


    public DiceRollingSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);

        // Load dice images into the array
        diceImages = new Bitmap[6];
        diceImages[0] = BitmapFactory.decodeResource(getResources(), R.drawable.dice1);
        diceImages[1] = BitmapFactory.decodeResource(getResources(), R.drawable.dice2);
        diceImages[2] = BitmapFactory.decodeResource(getResources(), R.drawable.dice3);
        diceImages[3] = BitmapFactory.decodeResource(getResources(), R.drawable.dice4);
        diceImages[4] = BitmapFactory.decodeResource(getResources(), R.drawable.dice5);
        diceImages[5] = BitmapFactory.decodeResource(getResources(), R.drawable.dice6);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
             startDropAnimation();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (drawingThread != null) {
            drawingThread.stopDrawing();
        }
    }


    public void setAnimationListener(DiceAnimationListener listener) {
        this.animationListener = listener;
    }

    private void notifyAnimationFinished() {
        if (animationListener != null) {
            // It's good practice to post to the UI thread if the listener
            // will manipulate UI elements (like removing a view)
            new Handler(Looper.getMainLooper()).post(() ->
                    animationListener.onDiceAnimationFinished()
            );
        }
    }

    // NEW PUBLIC METHOD: This is what GameManager will call



    private void startDropAnimation() {
        // Initialize the dice positions for the drop animation
        int canvasWidth = getWidth();
        int canvasHeight = getHeight();
        int diceSpacing = 20;

        Bitmap diceImage = diceImages[0]; // Use any dice image to calculate dimensions
        int imageWidth = diceImage.getWidth();
        int imageHeight = diceImage.getHeight();

        // Initial positions (off-screen at the top)
        leftDiceX = (canvasWidth / 2) - imageWidth - (diceSpacing / 2);
        leftDiceY = -imageHeight;
        rightDiceX = (canvasWidth / 2) + (diceSpacing / 2);
        rightDiceY = -imageHeight;

        // Target positions (center of the canvas)
        leftDiceTargetY = (canvasHeight - imageHeight) / 2;
        rightDiceTargetY = (canvasHeight - imageHeight) / 2;

        // Start the drawing thread
        isDropping = true;
        isRolling = false;
        drawingThread = new DrawingThread(getHolder());
        drawingThread.start();
    }

    private void startRollingAnimation() {
        isDropping = false;
        isRolling = true;
    }

    private class DrawingThread extends Thread {
        private final SurfaceHolder surfaceHolder;
        private boolean running = true;

        public DrawingThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        @Override
        public void run() {
            while (running) {
                Canvas canvas = null;

                try {
                    canvas = surfaceHolder.lockCanvas();
                    if (canvas != null) {
                        if (isDropping) {
                            drawDropAnimation(canvas);
                        } else if (isRolling) {
                            drawRollingAnimation(canvas);
                        }
                    }
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }

                try {
                    Thread.sleep(16); // ~60 FPS
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            notifyAnimationFinished();
        }

        public void stopDrawing() {
            running = false;
        }

        private void drawDropAnimation(Canvas canvas) {
            // Clear the canvas
            canvas.drawColor(Color.WHITE);

            // Move the dice down gradually
            leftDiceY += 20; // Adjust speed of the drop
            rightDiceY += 20;

            // Check if the dice have reached their target positions
            if (leftDiceY >= leftDiceTargetY) {
                leftDiceY = leftDiceTargetY;
            }
            if (rightDiceY >= rightDiceTargetY) {
                rightDiceY = rightDiceTargetY;
            }

            // Draw the dice at their current positions
            Bitmap leftDiceImage = diceImages[currentDiceFaces[0]];
            Bitmap rightDiceImage = diceImages[currentDiceFaces[1]];
            canvas.drawBitmap(leftDiceImage, leftDiceX, leftDiceY, null);
            canvas.drawBitmap(rightDiceImage, rightDiceX, rightDiceY, null);

            // If both dice have reached their target positions, start the rolling animation
            if (leftDiceY == leftDiceTargetY && rightDiceY == rightDiceTargetY) {
                startRollingAnimation();
            }
        }

        private void drawRollingAnimation(Canvas canvas) {
            // Clear the canvas
            canvas.drawColor(Color.WHITE);

            // Randomize the dice faces during rolling
            currentDiceFaces[0] =DiceRollingSurfaceView.this.finalDie1Index;// random.nextInt(6); // Random index between 0 and 5 for dice 1
            currentDiceFaces[1] = DiceRollingSurfaceView.this.finalDie2Index;//random.nextInt(6); // Random index between 0 and 5 for dice 2

            // Draw the two dice images at the center
            Bitmap leftDiceImage = diceImages[currentDiceFaces[0]];
            Bitmap rightDiceImage = diceImages[currentDiceFaces[1]];
            canvas.drawBitmap(leftDiceImage, leftDiceX, leftDiceY, null);
            canvas.drawBitmap(rightDiceImage, rightDiceX, rightDiceY, null);

            // Simulate rolling duration
            try {
                Thread.sleep(100); // Adjust the time between dice face changes
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Stop rolling after a set duration (e.g., 3 seconds)
            if (!isDropping && System.currentTimeMillis() % 3000 < 100) {
                stopDrawing();
            }
        }
    }
}