package com.example.backgammon;

import static com.example.backgammon.AppConsts.DISTANCE_BETWEEN_SOLDIERS;
import static com.example.backgammon.AppConsts.radius;

import android.content.Context;
import android.graphics.Color;

import java.util.Random;

public class GameManager
{
    private Board board;
    private BoardView boardView;
    private Context context;
    private int[] arrLocWhite;
    private int[] arrLocBlack;
    private boolean isWhite;
    private int numOfMoves=0;
    private int moveCounter=0;
    private int sourceIndex = -1;

    public GameManager(BoardView boardView, Context context,Board board){
        this.board =board;
        this.boardView = boardView;
        this.context = context;
        arrLocWhite = board.getLocWhite();
        arrLocBlack = board.getLocBlack();
        boardView.getPositionArrayX();
        this.isWhite = true;


        rollDice();

        // לזמן פעולה של הקוביות שקובע של מי התור ומעדכן את התור
    }

    public void updateGame(){

        boardView.invalidate();

    }
    int first;
    int second;
    public int[] rollDice(){
        Random r = new Random();
        first= r.nextInt(6) + 1;
         second= r.nextInt(6) + 1;

        if(first==second)
            numOfMoves = 4;
        else
            numOfMoves = 2;
        return new int[]{first,second};
    }



    public void sourceSelected(int soldierIndex) {

        sourceIndex = soldierIndex;
        board.clearHighlights();
        if (soldierIndex == -1){
            return;
        }
        int numDice1 = first;
        int numDice2 = second;

        int count = 0;
        if ( isWhite && board.whitePieces(soldierIndex)) {
            numOfMoves = 2;
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
                numOfMoves = 4;
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
            if (count > 0 && board.isLegalMove(soldierIndex, soldierIndex - numDice1 + numDice2, isWhite)) {
                board.highlightSlot(soldierIndex, isWhite, numDice1 + numDice2);
            }
            if (numDice1 == numDice2) {
                if (count == 2 && board.isLegalMove(soldierIndex, soldierIndex - numDice1 * 3, isWhite)) {
                    board.highlightSlot(soldierIndex, isWhite, numDice1*3);
                }
                if (count == 3 && board.isLegalMove(soldierIndex, soldierIndex - numDice1 * 4, isWhite)) {
                    board.highlightSlot(soldierIndex, isWhite, numDice1*4);
                }
            }
        }


        boardView.invalidate();

        //mobw the soldier
    }

    public int getNumOfMoves() {
        return numOfMoves;
    }

    public void setNumOfMoves(int numOfMoves) {
        this.numOfMoves = numOfMoves;
    }

    public int getMoveCounter() {
        return moveCounter;
    }

    public void setMoveCounter(int moveCounter) {
        this.moveCounter = moveCounter;
    }

    public void destinationSelected(int soldierIndex) {
        if (board.HighlightedSlotIsOn(soldierIndex)){
            board.move(sourceIndex, soldierIndex, isWhite);

            moveCounter++;
            if (moveCounter == numOfMoves ) {
                isWhite = !isWhite;
                moveCounter = 0;
                rollDice();
            }
        }
        // update the arrays

        board.clearHighlights();
        sourceIndex = -1;
        boardView.invalidate();
        // remove highlights???
        // invaldate board view
    }
}