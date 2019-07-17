package com.example.fixit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

//    private TextView test;
//    private Button button;
    private FirebaseAuth mAuth;
    private EditText usernameSignUp;
    private EditText passwordSignUp;
    private Button signUpbtn;


//    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
//    DatabaseReference conditionRef = mRootRef.child("condition");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
//        test = findViewById(R.id.test);
//        button = findViewById(R.id.button);
        usernameSignUp = findViewById(R.id.username_signin_et);
        passwordSignUp = findViewById(R.id.password_signin_et);
        signUpbtn = findViewById(R.id.btnSignUp);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        signUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp();
            }
        });
//        conditionRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String text = dataSnapshot.getValue(String.class);
//                test.setText(text);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                conditionRef.setValue("Hello World");
//            }
//        });

    }

    private void SignUp(){
        String email = usernameSignUp.getText().toString(), password = passwordSignUp.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Log.d("SignUp", "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                }
                else{
                    Log.w("SignUp", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
