package com.example.backgammon;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class Leader_board extends AppCompatActivity {

    TextView[] players = new TextView[5]; // מערך של 5 טקסטים

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        // קישור ל־TextView-ים לפי ID
        players[0] = findViewById(R.id.player1);
        players[1] = findViewById(R.id.player2);
        players[2] = findViewById(R.id.player3);
        players[3] = findViewById(R.id.player4);
        players[4] = findViewById(R.id.player5);

        // איפוס טקסטים
        for (int i = 0; i < players.length; i++) {
            players[i].setText((i + 1) + " ...");
        }

        // טעינת המובילים מה־Firebase
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").orderBy("coins", Query.Direction.DESCENDING).limit(5).get().addOnSuccessListener(result -> {
                    List<com.google.firebase.firestore.DocumentSnapshot> list = result.getDocuments();

                    for (int i = 0; i < list.size(); i++) {
                        String name = list.get(i).getString("name");
                        long coins = list.get(i).getLong("coins");
                        long numOfGames = list.get(i).getLong("numOfGames");

                        String icon = ""; // אייקון מדליה
                        if (i == 0) icon = "🥇 ";
                        else if (i == 1) icon = "🥈 ";
                        else if (i == 2) icon = "🥉 ";

                        String text = icon + (i + 1) + " - " + name + " (" + coins + " Coins" + ", " + numOfGames + " Games)";
                        players[i].setText(text);
                    }
                });

        // כפתור חזרה
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            startActivity(new Intent(this, endGame.class));
            finish();
        });
    }
}
