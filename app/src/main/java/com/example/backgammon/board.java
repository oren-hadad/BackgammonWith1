package com.example.backgammon;

public class board
{
    private int eatenBlack;
    private int eatenWhite;
    private int[] locWhite;//מיקום השחקנים הלבנים(מערך מונים)
    private int[] locBlack;//מיקום השחקנים השחורים (מערך מונים)1-24
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
    }


}
