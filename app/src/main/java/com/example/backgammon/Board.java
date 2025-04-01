package com.example.backgammon;

public class Board
{
    private int eatenBlack;
    private int eatenWhite;
    private int[] locWhite;//מיקום השחקנים הלבנים(מערך מונים)
    private int[] locBlack;//מיקום השחקנים השחורים (מערך מונים)0-23
    private int exitedWhite;
    private int exitedBlack;
    private  int[] highlightSlots = new int[24];


    public Board(){
        eatenBlack = 0;
        eatenWhite = 0;
        exitedWhite = 0;
        exitedBlack = 0;
        locWhite = new int[24];
        locBlack = new int[24];
        locBlack[23] = 2;
        locWhite[0] = 2;
        locBlack[12] = 5;
        locWhite[11] = 5;
        locBlack[7] = 3;
        locWhite[16] = 3;
        locBlack[5] = 5;
        locWhite[18] = 5;


    }
    public int[] getLocWhite(){
        return locWhite;
    }
    public int[] getLocBlack(){
        return locBlack;
    }
    public int getEatenBlack(){
        return eatenBlack;
    }
    public int getEatenWhite(){
        return eatenWhite;
    }
    public int getExitedWhite(){
        return exitedWhite;
    }
    public int getExitedBlack(){
        return exitedBlack;
    }
    public boolean isLegalMove(int from, int to, boolean isWhite){
        if(isWhite){
            if(locWhite[from] > 0 && to < locBlack.length&&  locBlack[to] < 2){
                return true;
            }
        }
        else{
            if(locBlack[from] > 0 && to < locBlack.length && locWhite[to] < 2){
                return true;
            }
        }
        return false;
    }
    public void move(int from, int to, boolean isWhite){
        if(isWhite){
            locWhite[from]--;
            if(locBlack[to] == 1){
                locBlack[to] = 0;
                eatenBlack++;
            }
            locWhite[to]++;
        }
        else{
            locBlack[from]--;
            if(locWhite[to] == 1){
                locWhite[to] = 0;
                eatenWhite++;
            }
            locBlack[to]++;
        }
    }


    public void highlightSlot(int i,boolean isWhite, int numDice) {
        if (isWhite){
            highlightSlots[(i +  numDice)] = 1;
        }
        else {
            highlightSlots[(i -  numDice)] = 1;



        }
    }

    public int[] getHighlightedSlot() {
        return highlightSlots;
    }

    public void clearHighlights() {
        for (int i = 0; i < highlightSlots.length; i++) {
            highlightSlots[i] = 0;
        }
    }
    public boolean isBlack( int i) {
        return locBlack[i] > 0;

    }

    public boolean whitePieces(int i)
    {
        return locWhite[i] > 0;
    }
    public boolean HighlightedSlotIsOn(int i) {
        return highlightSlots[i] == 1;
    }



}