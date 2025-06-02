package com.example.backgammon;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);


        // 1. Create the FrameLayout
        FrameLayout gameContainer = new FrameLayout(this);
        gameContainer.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        ));

        // 2. Create and add BoardView to the FrameLayout
        BoardView boardView = new BoardView(this,gameContainer);
        gameContainer.addView(boardView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        ));

        // 3. Set the FrameLayout as the content view
        setContentView(gameContainer);

        // Now you can get a reference to 'gameContainer' in your GameManager
        // (or pass it) and add/remove DiceRollingSurfaceView to it.

        // Example of how GameManager might get it (you'll need to pass 'gameContainer')
        // GameManager gameManager = new GameManager(boardView, this, board, gameContainer);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

//        BoardView boardView = new BoardView(this);
//
//        setContentView(boardView);
//
//
//
//        //   setContentView(R.layout.activity_main);
//
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

    }
}