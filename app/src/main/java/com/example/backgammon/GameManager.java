package com.example.backgammon;

import static com.example.backgammon.AppConsts.DISTANCE_BETWEEN_SOLDIERS;
import static com.example.backgammon.AppConsts.radius;

import android.content.Context;
import android.graphics.Color;

public class GameManager
{
    private Board board;
    private boolean turn;//false = black

    private BoardView boardView;
    private Context context;
    private int[] arrLocWhite;
    private int[] arrLocBlack;


    public GameManager(BoardView boardView, Context context,Board board){
        this.board =board;
        this.boardView = boardView;
        this.context = context;
        arrLocWhite = board.getLocWhite();
        arrLocBlack = board.getLocBlack();
        boardView.getPositionArrayX();

        // לזמן פעולה של הקוביות שקובע של מי התור ומעדכן את התור
    }

    public void updateGame(){

        boardView.invalidate();

    }


    public void sourceSelected(int soldierIndex,int numDice1, int numDice2) {

        if(board.isLegalMove(soldierIndex, soldierIndex+numDice1, turn)){

        }


        else if(board.isLegalMove(soldierIndex, soldierIndex+numDice2, turn)){
            // הזזה חוקית
            // מעדכן את הלוח
            // מעדכן את המיקום של החייל
            // מעדכן את התור
            // מעדכן את הקוביות
            // מעדכן את המסך
        }
        else if(board.isLegalMove(soldierIndex, soldierIndex+numDice1+numDice2, turn)){
            // הזזה חוקית
            // מעדכן את הלוח
            // מעדכן את המיקום של החייל
            // מעדכן את התור
            // מעדכן את הקוביות
            // מעדכן את המסך
        }
        else{
            // הזזה לא חוקית
            // הודעה למשתמש
        }


        // clear all the highlights
        // call clear highlights in game view

        // we hold the dice result
        // we know current status


        // get the array list of optional destainations
        // according to dice status

        // call highlightSlot for each of the optional destainations
    }


