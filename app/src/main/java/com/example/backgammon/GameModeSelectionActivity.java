package com.example.backgammon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class GameModeSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_mode); // ודא שהקובץ נכון

        Button singlePlayerButton = findViewById(R.id.singlePlayerButton);
        Button twoPlayerButton = findViewById(R.id.twoPlayerButton);

        singlePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameModeSelectionActivity.this, MainActivity.class);
                intent.putExtra("otherPlayerEmail", "");

                startActivity(intent);
                finish();
            }
        });

        twoPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(GameModeSelectionActivity.this, TwoPlayerGameActivity.class);
                //  startActivity(intent);
                EditText etMail = findViewById(R.id.editTextTextEmailAddress);
                EditText etPas = findViewById(R.id.editTextTextPassword);
                Button eSubmit = findViewById(R.id.button);

                etMail.setVisibility(View.VISIBLE);
                eSubmit.setVisibility(View.VISIBLE);
                etPas.setVisibility(View.VISIBLE);

                eSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String otherPlayerEmail = etMail.getText().toString();
                        String password = etPas.getText().toString();
                        // check if such user exists in the firebase ??? password

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("users").whereEqualTo("email", otherPlayerEmail).get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            if (task.getResult().getDocuments().size() > 0) {
                                                // we can move to next activity
                                                Intent intent = new Intent(GameModeSelectionActivity.this, MainActivity.class);
                                                intent.putExtra("otherPlayerEmail", otherPlayerEmail);
                                                startActivity(intent);
                                                finish();

                                            }
                                        }
                                    }
                                });
                    }


                });

            }
        });
    }
}
