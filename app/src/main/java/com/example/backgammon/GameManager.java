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

        if(board.isEaten(isWhite)){
            if(board.soldierCanReturn(isWhite,first,second) == false) {
                isWhite = !isWhite;
                rollDice();
            }
        }
        return new int[]{first,second};
    }


    //public void ExitSoldier(int soldierIndex, boolean isWhite){
    //    if (isWhite) {
    //        board.exitWhite(soldierIndex);
   //         boardView.invalidate();
   //     } else {
   //         board.exitBlack(soldierIndex);
   //         boardView.invalidate();
   //     }
   // }


    public void sourceSelected(int soldierIndex) {

        sourceIndex = soldierIndex;
        if (soldierIndex == -1){
            boardView.setClickCounter(0);
            return;
        }
        if(board.isEaten(isWhite)) {
            if (first == second)
                numOfMoves = 4;
            else
                numOfMoves = 2;
            if (isWhite) {
                if (board.HighlightedSlotIsOn(soldierIndex) && soldierIndex == (first - 1)) {
                    board.returnGame(soldierIndex, isWhite);
                    moveCounter++;
                    first = 0;
                    boardView.setClickCounter(0);
                    board.clearHighlight(soldierIndex);


                }
                if (board.HighlightedSlotIsOn(soldierIndex) && soldierIndex == second - 1) {
                    board.returnGame(soldierIndex, isWhite);
                    moveCounter++;
                    second = 0;
                    boardView.setClickCounter(0);
                    board.clearHighlight(soldierIndex);

                }

            }
            else if (board.HighlightedSlotIsOn(soldierIndex) && 23-soldierIndex == (first - 1)) {
                    board.returnGame(soldierIndex, isWhite);
                    moveCounter++;
                    first = 0;
                    boardView.setClickCounter(0);
                    board.clearHighlight(soldierIndex);
                }
                if (board.HighlightedSlotIsOn(soldierIndex) && 23-soldierIndex == second - 1) {
                    board.returnGame(soldierIndex, isWhite);
                    moveCounter++;
                    second = 0;
                    boardView.setClickCounter(0);
                    board.clearHighlight(soldierIndex);

                }
            else {
                    boardView.setClickCounter(0);
                    return;
            }
            if (moveCounter == numOfMoves ) {
                isWhite = !isWhite;
                moveCounter = 0;
                boardView.setClickCounter(0);
                board.clearHighlights();
                rollDice();

            }
            boardView.setClickCounter(0);

            }


        int numDice1 = first;
        int numDice2 = second;
        int count = 0;
            if (board.isEaten(isWhite) == false && isWhite && board.whitePieces(soldierIndex)) {
                board.clearHighlights();
                numOfMoves = 2;
                if (board.isLegalMove(soldierIndex, soldierIndex + numDice1, isWhite)) {
                    board.highlightSlot(soldierIndex, isWhite, numDice1);
                    count++;
                }
                if (board.isLegalMove(soldierIndex, soldierIndex + numDice2, isWhite)) {
                    board.highlightSlot(soldierIndex, isWhite, numDice2);
                    count++;
                }
                if (moveCounter <= 2 && count >= 1 && board.isLegalMove(soldierIndex, soldierIndex + numDice1 + numDice2, isWhite)) {
                    board.highlightSlot(soldierIndex, isWhite, numDice1 + numDice2);
                    count++;


                }
                if (numDice1 == numDice2) {
                    numOfMoves = 4;
                    if (moveCounter <= 1 && count == 3 && board.isLegalMove(soldierIndex, soldierIndex + numDice1 * 3, isWhite)) {
                        board.highlightSlot(soldierIndex, isWhite, numDice1 * 3);
                        count++;
                    }
                    if (moveCounter == 0 && count == 4 && board.isLegalMove(soldierIndex, soldierIndex + numDice1 * 4, isWhite)) {
                        board.highlightSlot(soldierIndex, isWhite, numDice1 * 4);
                    }
                }
            }

        else if (board.isEaten(isWhite) == false && !isWhite && board.isBlack(soldierIndex)) {
            numOfMoves = 2;
            if (board.isLegalMove(soldierIndex, soldierIndex - numDice1, isWhite)) {
                board.highlightSlot(soldierIndex, isWhite, numDice1);
                count++;
            }
            if (board.isLegalMove(soldierIndex, soldierIndex - numDice2, isWhite)) {
                board.highlightSlot(soldierIndex, isWhite, numDice2);
                count++;
            }
            if (moveCounter <= 2 && count >=1 && board.isLegalMove(soldierIndex, soldierIndex - (numDice1 + numDice2), isWhite)) {
                board.highlightSlot(soldierIndex, isWhite, numDice1 + numDice2);
                count++;
            }
            if (numDice1 == numDice2) {
                numOfMoves = 4;
                if (moveCounter <=1 &&count == 3 && board.isLegalMove(soldierIndex, soldierIndex - numDice1 * 3, isWhite)) {
                    board.highlightSlot(soldierIndex, isWhite, numDice1*3);
                }
                if (moveCounter ==0 &&count == 4 && board.isLegalMove(soldierIndex, soldierIndex - numDice1 * 4, isWhite)) {
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
            boolean b = true;
            int d =Math.abs(soldierIndex - sourceIndex);
            if (moveCounter <= 2 && first == second){
                board.move(sourceIndex, soldierIndex, isWhite);
                moveCounter += d/first;;
            }
            else if (moveCounter == 0 && d == first + second){
                board.move(sourceIndex, soldierIndex, isWhite);
                moveCounter+=2;
                first = 0;
                second =0;
            }
            else if (d == first){
                board.move(sourceIndex, soldierIndex, isWhite);
                moveCounter++;
                first = 0;
            }
            else if (d == second){
                board.move(sourceIndex, soldierIndex, isWhite);
                moveCounter++;
                second = 0;
            }
            }

        // update the arrays
        sourceIndex = -1;
        board.clearHighlights();
        if (moveCounter == numOfMoves ) {
            isWhite = !isWhite;
            moveCounter = 0;
            rollDice();
        }
        boardView.invalidate();
        // remove highlights???
        // invaldate board view
    }


}
