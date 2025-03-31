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
        if (soldierIndex == -1){
            return;
        }
        int numDice1 = 1;
        int numDice2 = 2;
        int count = 0;
        if ( isWhite && board.whitePieces(soldierIndex)) {
            if (board.isLegalMove(soldierIndex, soldierIndex + numDice1 , isWhite)) {
                board.highlightSlot(soldierIndex, isWhite, numDice1);
                count++;
            }
            if (board.isLegalMove(soldierIndex, soldierIndex + numDice2, isWhite)) {
                board.highlightSlot(soldierIndex, isWhite, numDice2);
                count++;
            }
            if (count > 0 && board.isLegalMove(soldierIndex, soldierIndex + numDice1 + numDice2, isWhite)) {
                board.highlightSlot(soldierIndex, isWhite, numDice1 + numDice2);
            }
            if (numDice1 == numDice2) {
                if (count == 2 && board.isLegalMove( soldierIndex, soldierIndex + numDice1 * 3, isWhite)) {
                    board.highlightSlot(soldierIndex, isWhite, numDice1*3);
                }
                if (count == 3 && board.isLegalMove(soldierIndex, soldierIndex + numDice1 * 4, isWhite)) {
                    board.highlightSlot(soldierIndex, isWhite, numDice1*4);
                }
            }
        }
        else if ( !isWhite && board.isBlack(soldierIndex)) {
            if (board.isLegalMove(soldierIndex, soldierIndex - numDice1, isWhite)) {
                board.highlightSlot(soldierIndex, isWhite, numDice1);
            }
            if (board.isLegalMove(soldierIndex, soldierIndex - numDice2, isWhite)) {
                board.highlightSlot(soldierIndex, isWhite, numDice2);

            }
            if (board.isLegalMove(soldierIndex, soldierIndex - numDice1 + numDice2, isWhite)) {
                board.highlightSlot(soldierIndex, isWhite, numDice1 + numDice2);
            }
            if (numDice1 == numDice2) {
               if (board.isLegalMove(soldierIndex, soldierIndex - numDice1 * 3, isWhite)) {
                   board.highlightSlot(soldierIndex, isWhite, numDice1*3);
                }
                if (board.isLegalMove(soldierIndex, soldierIndex - numDice1 * 4, isWhite)) {
                    board.highlightSlot(soldierIndex, isWhite, numDice1*4);
                }
            }
        }


        boardView.invalidate();

        //mobw the soldier
        isWhite = !isWhite;
    }
}