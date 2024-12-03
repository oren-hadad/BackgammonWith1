package com.example.backgammon;

import android.content.Context;
import android.graphics.Canvas;

public class board
{
    private int eatenBlack;
    private int eatenWhite;
    private int[] locWhite;//מיקום השחקנים הלבנים(מערך מונים)
    private int[] locBlack;//מיקום השחקנים השחורים (מערך מונים)0-23
    private int exitedWhite;
    private int exitedBlack;

    public board(){
        eatenBlack = 0;
        eatenWhite = 0;
        exitedWhite = 0;
        exitedBlack = 0;
        for (int i = 0; i< locWhite.length; i++){
            locWhite[i] = 0;
            locBlack[i] = 0;
        }
        locBlack[0] = 2;
        locWhite[0] = 2;
        locBlack[11] = 5;
        locWhite[11] = 5;
        locBlack[15] = 3;
        locWhite[15] = 3;
        locBlack[18] = 5;
        locWhite[18] = 5;
    }
    public static void startBoard(Canvas canvas){

    }






}
