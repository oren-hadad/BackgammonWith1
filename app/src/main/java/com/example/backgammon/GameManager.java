package com.example.backgammon;

import static com.example.backgammon.AppConsts.DISTANCE_BETWEEN_SOLDIERS;
import static com.example.backgammon.AppConsts.radius;

import android.content.Context;
import android.graphics.Color;

public class GameManager
{
    private Board board;
    private BoardView boardView;
    private Context context;
    private int[] arrLocWhite;
    private int[] arrLocBlack;
    private boolean isWhite;


    public GameManager(BoardView boardView, Context context,Board board){
        this.board =board;
        this.boardView = boardView;
        this.context = context;
        arrLocWhite = board.getLocWhite();
        arrLocBlack = board.getLocBlack();
        boardView.getPositionArrayX();
        this.isWhite = true;

        // לזמן פעולה של הקוביות שקובע של מי התור ומעדכן את התור
    }

    public void updateGame(){

        boardView.invalidate();

    }
    public int[] Dice(){
    return new int[]{5,6};
    }
    public void sourceSelected(int soldierIndex) {

        board.clearHighlights();

        int numDice1 = 2;
        int numDice2 = 2;
        if ( isWhite && board.whitePieces(soldierIndex)) {
            if ( soldierIndex != -1 && board.isLegalMove(soldierIndex, soldierIndex + numDice1, isWhite)) {
                board.highlightSlot(soldierIndex - numDice1, isWhite);
            }
            if (soldierIndex != -1 && board.isLegalMove(soldierIndex, soldierIndex + numDice2, isWhite)) {
                board.highlightSlot(soldierIndex - numDice2, isWhite);

            }
            if (soldierIndex != -1 && board.isLegalMove(soldierIndex, soldierIndex + numDice1 + numDice2, isWhite)) {
                board.highlightSlot(soldierIndex - numDice1- numDice2, isWhite);
            }
            if (soldierIndex != -1 && numDice1 == numDice2) {
                if (board.isLegalMove(soldierIndex, soldierIndex + numDice1 * 3, isWhite)) {
                    board.highlightSlot(soldierIndex - numDice1 * 3, isWhite);
                }
                if (board.isLegalMove(soldierIndex, soldierIndex + numDice1 * 4, isWhite)) {
                    board.highlightSlot(soldierIndex - numDice1 * 4, isWhite);
                }
            }
        }
        else if ( isWhite == false && !board.isNotBlack(isWhite, soldierIndex)) {
            if (soldierIndex != -1 && board.isLegalMove(soldierIndex, soldierIndex + numDice1, isWhite)) {
                board.highlightSlot(soldierIndex + numDice1, isWhite);
            }
            if (soldierIndex != -1 && board.isLegalMove(soldierIndex, soldierIndex + numDice2, isWhite)) {
                board.highlightSlot(soldierIndex + numDice2, isWhite);

            }
            if (soldierIndex != -1 && board.isLegalMove(soldierIndex, soldierIndex + numDice1 + numDice2, isWhite)) {
                board.highlightSlot(soldierIndex + numDice1 + numDice2, isWhite);
            }
            if (soldierIndex != -1 && numDice1 == numDice2) {
                if (board.isLegalMove(soldierIndex, soldierIndex + numDice1 * 3, isWhite)) {
                    board.highlightSlot(soldierIndex + numDice1 * 3, isWhite);
                }
                if (board.isLegalMove(soldierIndex, soldierIndex + numDice1 * 4, isWhite)) {
                    board.highlightSlot(soldierIndex + numDice1 * 4, isWhite);
                }
            }
        }





        // clear all the highlights
        // call clear highlights in game view

        // we hold the dice result
        // we know current status


        // get the array list of optional destainations
        // according to dice status

        // call highlightSlot for each of the optional destainations

        boardView.invalidate();
    }
}






