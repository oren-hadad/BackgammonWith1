package com.example.backgammon;

import static com.example.backgammon.AppConsts.DISTANCE_BETWEEN_SOLDIERS;
import static com.example.backgammon.AppConsts.radius;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.FrameLayout;
import android.content.Intent;

import java.util.Random;

public class GameManager implements DiceRollingSurfaceView.DiceAnimationListener
{
    private Board board;
    private BoardView boardView;
    private Context context;
    private boolean isWhite;
    private int numOfMoves=0;
    private int moveCounter=0;
    private int sourceIndex = -1;
    private FrameLayout gameContainer;
    private DiceRollingSurfaceView diceRollingView;

    public GameManager(BoardView boardView, Context context,Board board, FrameLayout gameContainer) {
        this.board =board;
        this.boardView = boardView;
        this.context = context;
        boardView.getPositionArrayX();
        this.isWhite = false;
        this.gameContainer = gameContainer;


        // יצירת תצוגת הקוביות

        CountDownTimer timer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                startGame();
            }
        };
        timer.start();


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
//        first =5;
//        second =5;

        if(first==second)
            numOfMoves = 4;
        else
            numOfMoves = 2;
        if(diceRollingView == null) {

            diceRollingView = new DiceRollingSurfaceView(context)  ;
            diceRollingView.setAnimationListener(this);

        }
        diceRollingView.setDiceValues(first,second);

        ;
        if (diceRollingView.getParent() == null) {
            gameContainer.addView(diceRollingView);
        }

        if(board.isEaten(isWhite)){
            if(board.soldierCanReturn(isWhite,first,second) == false) {
                isWhite = !isWhite;
                rollDice();
            }
            else{
                boardView.invalidate();
            }
        }
        return new int[]{first,second};
    }


    public void sourceSelected(int soldierIndex) {
        sourceIndex = soldierIndex;
        if (soldierIndex == -1){
            boardView.setClickCounter(0);
            return;
        }

        // אם יש חיילים אכולים - טיפול נפרד
        if(board.isEaten(isWhite)) {
            handleEatenSoldiers(soldierIndex);
            return;
        }

        // בדיקה אם השחקן יכול להוציא שחקנים
        if (board.canExit(isWhite)) {
            // ניסיון להוציא שחקן
            if (tryToExitSoldier(soldierIndex)) {
                if(board.isGameOver(isWhite)){
                    // אם המשחק נגמר, צריך להודיע על כך
                    AppConsts.Winner = isWhite; // עדכון המנצח
                    if(!board.canExit(!isWhite)){
                        AppConsts.coins = 150;
                    }
                    else if(isWhite && board.getExitedWhite() == 0 || !isWhite && board.getExitedBlack() == 0){
                        AppConsts.coins = 100;
                    }
                    else{
                        AppConsts.coins = 50;
                    }
                    Intent intent = new Intent(context, endGame.class);
                    context.startActivity(intent);
                    board = new Board(); // איפוס הלוח למשחק חדש
                }
                return; // אם הצלחנו להוציא, לא צריך לעשות כלום נוסף
            }
            // אם לא הצלחנו להוציא, ממשיכים למשחק רגיל
        }

        // משחק רגיל
        handleNormalGameplay(soldierIndex);
    }

    private void handleEatenSoldiers(int soldierIndex) {
        if (first == second)
            numOfMoves = 4;
        else
            numOfMoves = 2;

        if (isWhite) {
            if (first == second && board.HighlightedSlotIsOn(soldierIndex) && soldierIndex == (first - 1) ){
                moveCounter = board.getWhiteEaten();
                for (int i = 0; i < moveCounter; i++) {
                    board.returnGame(soldierIndex, isWhite);
                }
                board.clearHighlights();
            }
            else{
                if (board.HighlightedSlotIsOn(soldierIndex) && soldierIndex == (first - 1)) {
                    board.returnGame(soldierIndex, isWhite);
                    moveCounter++;
                    if(first != second )
                        first = 0;
                    if(!board.isEaten(isWhite))
                        board.clearHighlights();
                    else if(first != second)
                        board.clearHighlight(soldierIndex);
                    boardView.setClickCounter(0);
                    boardView.invalidate();
                }
                if (board.HighlightedSlotIsOn(soldierIndex) && soldierIndex == second - 1 ) {
                    board.returnGame(soldierIndex, isWhite);
                    moveCounter++;
                    if(first != second)
                        second = 0;
                    if(!board.isEaten(isWhite))
                        board.clearHighlights();
                    else if(first != second)
                        board.clearHighlight(soldierIndex);
                    boardView.setClickCounter(0);
                    boardView.invalidate();
                }
            }
        }
        else {
            if (first == second && board.HighlightedSlotIsOn(soldierIndex) && soldierIndex == (first - 1) ){
                moveCounter = board.getBlackEaten();
                for (int i = 0; i < moveCounter; i++) {
                    board.returnGame(soldierIndex, isWhite);
                }
                board.clearHighlights();
            }
            else {
                if (board.HighlightedSlotIsOn(soldierIndex) && 23 - soldierIndex == (first - 1)) {
                    board.returnGame(soldierIndex, isWhite);
                    moveCounter++;
                    if (first != second)
                        first = 0;
                    if (!board.isEaten(isWhite))
                        board.clearHighlights();
                    else if(first != second)
                        board.clearHighlight(soldierIndex);
                    boardView.setClickCounter(0);
                    boardView.invalidate();
                }
                if (board.HighlightedSlotIsOn(soldierIndex) && 23 - soldierIndex == second - 1) {
                    board.returnGame(soldierIndex, isWhite);
                    moveCounter++;
                    if (first != second)
                        second = 0;
                    if (!board.isEaten(isWhite))
                        board.clearHighlights();
                    else if(first != second)
                        board.clearHighlight(soldierIndex);
                    boardView.setClickCounter(0);
                    boardView.invalidate();
                }
            }
        }

        // בדיקה האם התור הסתיים
        if (moveCounter == numOfMoves) {
            endTurn();
        } else if (!board.isEaten(isWhite)) {
            // סיימנו עם האכילות, אבל עדיין יש מהלכים
            // צריך לבדוק אם השחקן יכול לשחק עם הקוביות שנותרו
            if (!canPlayerMakeAnyMove(isWhite, first, second)) {
                // אם השחקן לא יכול לזוז - מעביר תור
                endTurn();
            } else {
                // השחקן יכול לשחק - ממשיך
                board.clearHighlights();
                boardView.setClickCounter(0);
            }
        }
    }

    private void handleNormalGameplay(int soldierIndex) {
        int count = 0;
        if (isWhite && board.whitePieces(soldierIndex)) {
            board.clearHighlights();
            numOfMoves = 2;
            if (board.isLegalMove(soldierIndex, soldierIndex + first, isWhite)) {
                board.highlightSlot(soldierIndex, isWhite, first);
                count++;
            }
            if (board.isLegalMove(soldierIndex, soldierIndex + second, isWhite)) {
                board.highlightSlot(soldierIndex, isWhite, second);
                count++;
            }
            if (moveCounter <= 2 && count >= 1 && board.isLegalMove(soldierIndex, soldierIndex + first + second, isWhite)) {
                board.highlightSlot(soldierIndex, isWhite, first + second);
                count++;
            }
            if (first == second) {
                numOfMoves = 4;
                if (moveCounter <= 1 && count == 3 && board.isLegalMove(soldierIndex, soldierIndex + first * 3, isWhite)) {
                    board.highlightSlot(soldierIndex, isWhite, first * 3);
                    count++;
                }
                if (moveCounter == 0 && count == 4 && board.isLegalMove(soldierIndex, soldierIndex + first * 4, isWhite)) {
                    board.highlightSlot(soldierIndex, isWhite, first * 4);
                }
            }
        }
        else if (!isWhite && board.isBlack(soldierIndex)) {
            numOfMoves = 2;
            if (board.isLegalMove(soldierIndex, soldierIndex - first, isWhite)) {
                board.highlightSlot(soldierIndex, isWhite, first);
                count++;
            }
            if (board.isLegalMove(soldierIndex, soldierIndex - second, isWhite)) {
                board.highlightSlot(soldierIndex, isWhite, second);
                count++;
            }
            if (moveCounter <= 2 && count >=1 && board.isLegalMove(soldierIndex, soldierIndex - (first + second), isWhite)) {
                board.highlightSlot(soldierIndex, isWhite, first + second);
                count++;
            }
            if (first == second) {
                numOfMoves = 4;
                if (moveCounter <=1 &&count == 3 && board.isLegalMove(soldierIndex, soldierIndex - first * 3, isWhite)) {
                    board.highlightSlot(soldierIndex, isWhite, first*3);
                    count ++;
                }
                if (moveCounter ==0 &&count == 4 && board.isLegalMove(soldierIndex, soldierIndex - first * 4, isWhite)) {
                    board.highlightSlot(soldierIndex, isWhite, first*4);
                }
            }
        }
        boardView.invalidate();
    }

    private void endTurn() {
        isWhite = !isWhite;
        moveCounter = 0;
        boardView.setClickCounter(0);
        board.clearHighlights();
        rollDice();

        // 3. Start the animation
    //    diceRollingView.startAnimationSequence(first, second);
    }

    // פונקציה שבודקת אם השחקן יכול לבצע מהלך כלשהו עם הקוביות שנותרו
    private boolean canPlayerMakeAnyMove(boolean isWhite, int dice1, int dice2) {
        // אם שתי הקוביות אופסו - אין מהלכים
        if (dice1 == 0 && dice2 == 0) {
            return false;
        }

        // בודק כל עמדה בלוח
        for (int i = 0; i < 24; i++) {
            if (isWhite && board.whitePieces(i)) {
                // בודק אם יכול לזוז עם dice1
                if (dice1 > 0 && board.isLegalMove(i, i + dice1, isWhite)) {
                    return true;
                }
                // בודק אם יכול לזוז עם dice2
                if (dice2 > 0 && board.isLegalMove(i, i + dice2, isWhite)) {
                    return true;
                }
            } else if (!isWhite && board.isBlack(i)) {
                // בודק אם יכול לזוז עם dice1
                if (dice1 > 0 && board.isLegalMove(i, i - dice1, isWhite)) {
                    return true;
                }
                // בודק אם יכול לזוז עם dice2
                if (dice2 > 0 && board.isLegalMove(i, i - dice2, isWhite)) {
                    return true;
                }
            }
        }

        return false; // לא נמצא מהלך אפשרי
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
            endTurn();
        }
        boardView.invalidate();
        // remove highlights???
        // invaldate board view
    }

    private boolean tryToExitSoldier(int soldierIndex) {
        if (isWhite) {
            return tryToExitWhiteSoldier(soldierIndex);
        } else {
            return tryToExitBlackSoldier(soldierIndex);
        }
    }

    /**
     * ניסיון להוציא שחקן לבן
     */
    private boolean tryToExitWhiteSoldier(int soldierIndex) {
        // בדיקה שיש שחקן לבן במקום הזה
        if (!board.whitePieces(soldierIndex)) {
            return false;
        }

        // חישוב המרחק שהשחקן צריך לעבור כדי לצאת
        int distanceToExit = 24 - soldierIndex; // כמה צעדים עד היציאה

        // טיפול בדבלים - אם זה דבל, יכול להשתמש בכל המהלכים שנותרו
        if (first == second && first > 0) {
            // בדבלים - אם השחקן יכול לצאת עם הקובייה
            if (distanceToExit == first || (distanceToExit < first && !board.hasSoldiersBehindWhite(soldierIndex))) {
                board.exit(isWhite, soldierIndex);
                moveCounter++;

                // בדיקה אם התור הסתיים
                if (moveCounter == numOfMoves&& !board.isGameOver(true)) {
                    endTurn();
                } else {
                    board.clearHighlights();
                    boardView.setClickCounter(0);
                }
                boardView.invalidate();
                return true;
            }
            return false; // לא יכול לצאת בדבל
        }

        // טיפול במקרה רגיל (לא דבל)
        // מקרה 1: השחקן יכול לצאת בדיוק עם קובייה ראשונה
        if (distanceToExit == first && first > 0) {
            board.exit(isWhite, soldierIndex);
            moveCounter++;
            first = 0; // "משתמשים" בקובייה

            // בדיקה אם התור הסתיים
            if (moveCounter == numOfMoves&& !board.isGameOver(true)) {
                endTurn();
            } else {
                board.clearHighlights();
                boardView.setClickCounter(0);
            }
            boardView.invalidate();
            return true;
        }

        // מקרה 2: השחקן יכול לצאת בדיוק עם קובייה שנייה
        if (distanceToExit == second && second > 0) {
            board.exit(isWhite, soldierIndex);
            moveCounter++;
            second = 0; // "משתמשים" בקובייה

            // בדיקה אם התור הסתיים
            if (moveCounter == numOfMoves&& !board.isGameOver(true)) {
                endTurn();
            } else {
                board.clearHighlights();
                boardView.setClickCounter(0);
            }
            boardView.invalidate();
            return true;
        }

        // מקרה 3: השחקן יכול לצאת עם קובייה גבוהה יותר (אם אין שחקנים מאחוריו)
        if (!board.hasSoldiersBehindWhite(soldierIndex)) {
            // בודק אם קובייה ראשונה גבוהה מהמרחק הנדרש
            if (first > distanceToExit && first > 0) {
                board.exit(isWhite, soldierIndex);
                moveCounter++;
                first = 0;

                if (moveCounter == numOfMoves&& !board.isGameOver(true)) {
                    endTurn();
                } else {
                    board.clearHighlights();
                    boardView.setClickCounter(0);
                }
                boardView.invalidate();
                return true;
            }

            // בודק אם קובייה שנייה גבוהה מהמרחק הנדרש
            if (second > distanceToExit && second > 0) {
                board.exit(isWhite, soldierIndex);
                moveCounter++;
                second = 0;

                if (moveCounter == numOfMoves&& !board.isGameOver(true)) {
                    endTurn();
                } else {
                    board.clearHighlights();
                    boardView.setClickCounter(0);
                }
                boardView.invalidate();
                return true;
            }
        }
        // לא הצלחנו להוציא את השחקן
        return false;
    }
    private boolean tryToExitBlackSoldier(int soldierIndex) {
        // בדיקה שיש שחקן שחור במקום הזה
        if (!board.isBlack(soldierIndex)) {
            return false;
        }

        // חישוב המרחק שהשחקן צריך לעבור כדי לצאת
        // עבור שחקנים שחורים - הם יוצאים מהצד השני (מיקומים 0-5)
        int distanceToExit = soldierIndex + 1; // כמה צעדים עד היציאה

        // טיפול בדבלים
        if (first == second && first > 0) {
            // בדבלים - אם השחקן יכול לצאת עם הקובייה
            if (distanceToExit == first || (distanceToExit < first && !board.hasSoldiersBehindBlack(soldierIndex))) {
                board.exit(false, soldierIndex); // false = שחקן שחור
                moveCounter++;

                // בדיקה אם התור הסתיים
                if (moveCounter == numOfMoves&& !board.isGameOver(false)) {
                    endTurn();
                } else {
                    board.clearHighlights();
                    boardView.setClickCounter(0);
                }
                boardView.invalidate();
                return true;
            }
            return false; // לא יכול לצאת בדבל
        }

        // מקרה 1: השחקן יכול לצאת בדיוק עם קובייה ראשונה
        if (distanceToExit == first && first > 0) {
            board.exit(false, soldierIndex); // false = שחקן שחור
            moveCounter++;
            first = 0;

            if (moveCounter == numOfMoves && !board.isGameOver(false)) {
                endTurn();
            } else {
                board.clearHighlights();
                boardView.setClickCounter(0);
            }
            boardView.invalidate();
            return true;
        }

        // מקרה 2: השחקן יכול לצאת בדיוק עם קובייה שנייה
        if (distanceToExit == second && second > 0) {
            board.exit(false, soldierIndex); // false = שחקן שחור
            moveCounter++;
            second = 0;

            if (moveCounter == numOfMoves&& !board.isGameOver(false)) {
                endTurn();
            } else {
                board.clearHighlights();
                boardView.setClickCounter(0);
            }
            boardView.invalidate();
            return true;
        }

        // מקרה 3: השחקן יכול לצאת עם קובייה גבוהה יותר (אם אין שחקנים מאחוריו)
        if (!board.hasSoldiersBehindBlack(soldierIndex)) {
            // בודק אם קובייה ראשונה גבוהה מהמרחק הנדרש
            if (first > distanceToExit && first > 0) {
                board.exit(false, soldierIndex); // false = שחקן שחור
                moveCounter++;
                first = 0;
                if (moveCounter == numOfMoves&& !board.isGameOver(false)) {
                    endTurn();
                } else {
                    board.clearHighlights();
                    boardView.setClickCounter(0);
                }
                boardView.invalidate();
                return true;
            }
            // בודק אם קובייה שנייה גבוהה מהמרחק הנדרש
            if (second > distanceToExit && second > 0) {
                board.exit(false, soldierIndex); // false = שחקן שחור
                moveCounter++;
                second = 0;
                if (moveCounter == numOfMoves&& !board.isGameOver(false)) {
                    endTurn();
                } else {
                    board.clearHighlights();
                    boardView.setClickCounter(0);
                }
                boardView.invalidate();
                return true;
            }
        }


        // לא הצלחנו להוציא את השחקן
        return false;
    }
    public void startGame(){//הגרלת קוביות ראשונות ואם הקובייה הראשונה גדולה מהשנייה השחקן הלבן יתחיל
        rollDice();
        if(first == second){
            rollDice();
        }
        if(first > second) {
            isWhite = true;
        }
    }

    @Override
    public void onDiceAnimationFinished() {
        if (gameContainer != null && diceRollingView != null) {
            gameContainer.removeView(diceRollingView);
        }
    }
}