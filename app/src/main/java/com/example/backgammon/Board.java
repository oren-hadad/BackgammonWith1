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
        locBlack[17] = 5;



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
            if(from >=0&& locWhite[from] > 0 && to < locBlack.length&&  locBlack[to] < 2){
                return true;
            }
        }
        else{
            if(to >=0&& locBlack[from] > 0 && to < locBlack.length && locWhite[to] < 2){
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
        if (numDice == 0){
         return;
        }
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

        return i >=0&& highlightSlots[i] == 1;
    }
    public boolean isEaten(boolean isWhite) {
        if( isWhite){
            return eatenWhite >0;
        }
        else{
            return eatenBlack >0;
        }
    }
    public void soldierCanReturn(boolean isWhite, int numDice1, int numDice2) {
        if(isWhite){
                if(locBlack[numDice1-1] <= 1){
                    highlightSlot(0, isWhite, numDice1-1);
                }
                if(locBlack[numDice2-1] <= 1){
                    highlightSlot(0, isWhite, numDice2);
                }

        }
        else{
            if(locBlack[24- numDice1] <= 1){
                highlightSlot(0, isWhite, numDice1-1);
            }
            if(locBlack[24-numDice2] <= 1){
                highlightSlot(0, isWhite, numDice2);
            }

        }
    }



}