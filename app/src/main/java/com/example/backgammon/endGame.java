package com.example.backgammon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class endGame extends AppCompatActivity {
    private static boolean updated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_end_game);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (!updated) {
            checkWinnerAndUpdate();
            updated = true;
        }
        // חיבור הכפתורים לפי ה-ID שלהם
        Button chooseModeButton = findViewById(R.id.chooseModeButton);
        Button leaderboardButton = findViewById(R.id.leaderboardButton);

        // מאזין ללחיצה על Choose Mode
        chooseModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updated = false;
                Intent intent = new Intent(endGame.this, GameModeSelectionActivity.class);
                startActivity(intent);
            }
        });

        // מאזין ללחיצה על Leaderboard
        leaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(endGame.this, Leader_board.class);
                startActivity(intent);
            }
        });
    }

    private void checkWinnerAndUpdate() {
        if (AppConsts.DuoAGame) { // only then update points!
            // אם המשחק הוא דו-משתמש, נבדוק מי ניצח ונעדכן את הסטטיסטיקות
            // check who won - Host or Other PLayer
            // update firebase accordingly! (HOst docuemt ID or other document ID

            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            String winnerRef = "";
            String loserRef = "";
            if(AppConsts.Winner) {
                winnerRef = AppConsts.otherPlayerReference;
                loserRef = mAuth.getCurrentUser().getUid();
            }
            else{
                winnerRef = mAuth.getCurrentUser().getUid();
                loserRef = AppConsts.otherPlayerReference;
            }

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseFirestore DbLoser = FirebaseFirestore.getInstance();
            db.collection("users").document(winnerRef).update("coins", FieldValue.increment(AppConsts.coins) ,"numOfGames", FieldValue.increment(1));
            DbLoser.collection("users").document(loserRef).update("numOfGames", FieldValue.increment(1));



        }
    }
}
