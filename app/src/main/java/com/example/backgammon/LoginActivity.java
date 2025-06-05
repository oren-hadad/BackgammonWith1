package com.example.backgammon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(mAuth.getCurrentUser()!=null)// already registerd skip this screen
        {
            moveToNextActivity();
        }
        Button loginBtn = findViewById(R.id.loginButton);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etMail = findViewById(R.id.emailEditText);
                EditText etPassword = findViewById(R.id.passwordEditText);

                if(TextUtils.isEmpty(etMail.getText()) || TextUtils.isEmpty(etPassword.getText()))
                {
                    Toast.makeText(LoginActivity.this, " please fill both fields ", Toast.LENGTH_SHORT).show();
                    return;
                }

                String Email =etMail.getText().toString();
                String Password = etPassword.getText().toString();

                mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            // finished Auth
                            // create user object
                            // Add user to  FB

                            User user = new User(Email);

                            FirebaseFirestore db = FirebaseFirestore.getInstance();

                            db.collection("users").document(mAuth.getCurrentUser().getUid()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(LoginActivity.this,"finished registration",Toast.LENGTH_SHORT).show();
                                      // move to screen
                                        moveToNextActivity();

                                    //    finish();

                                    }
                                    else
                                    {
                                        Toast.makeText(LoginActivity.this,"failed registration",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });


    }

    private void moveToNextActivity() {
        Intent intent = new Intent(LoginActivity.this, GameModeSelectionActivity.class);
        startActivity(intent);
        finish();
    }
}
