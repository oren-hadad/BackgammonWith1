package com.example.backgammon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                AppConsts.DuoAGame = false;
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
                EditText etName = findViewById(R.id.editTextTextName);
                Button eSubmit = findViewById(R.id.button);

                etMail.setVisibility(View.VISIBLE);
                eSubmit.setVisibility(View.VISIBLE);
                etName.setVisibility(View.VISIBLE);

                eSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String otherPlayerEmail = etMail.getText().toString();
                        String Name = etName.getText().toString();
                        // check if such user exists in the firebase ??? password

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("users").whereEqualTo("email", otherPlayerEmail).get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            if (task.getResult().getDocuments().size() > 0) {//
                                                // there is maximum of one user that can have this email!
                                                // check for the name
                                                User u = task.getResult().getDocuments().get(0).toObject(User.class);
                                                if(u.getName()!=null && u.getName().equals(Name))// only if this also equals - make game DUO
                                                {
                                                    AppConsts.DuoAGame = true;
                                                    // update the other user name and Reference!
                                                    AppConsts.otherPlayerName = u.getName();
                                                    // store / save the reference of the other user document
                                                    // will be eaiser later to update directly no need to search AGAIN
                                                    AppConsts.otherPlayerReference = task.getResult().getDocuments().get(0).getId();
                                                    // we can move to next activity

                                                    Intent intent = new Intent(GameModeSelectionActivity.this, MainActivity.class);
                                                    intent.putExtra("otherPlayerEmail", otherPlayerEmail);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                                else
                                                    Toast.makeText(GameModeSelectionActivity.this, "User not found or name does not match", Toast.LENGTH_SHORT).show();

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
