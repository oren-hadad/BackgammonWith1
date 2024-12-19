package com.example.backgammon;

public class GameManager
{
    private Board board;
    private boolean turn;//false = black

    private BoardView boardView;


    public GameManager(BoardView boardView){
        board = new Board();
        this.boardView = boardView;
        // לזמן פעולה של הקוביות שקובע של מי התור ומעדכן את התור
    }
    int[] arrLocWhite = board.getLocWhite();
    int[] arrLocBlack = board.getLocBlack();

    public void updateGame(){
        for(int i = 0; i<arrLocWhite.length; i++){

        }
    }

}
