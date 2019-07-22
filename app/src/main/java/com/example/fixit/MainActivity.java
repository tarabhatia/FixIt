package com.example.fixit;
// Add an import statement for the client library.

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Autocomplete";
    private FirebaseAuth mAuth;
    private EditText usernameSignUp;
    private EditText passwordSignUp;
    private Button signUpbtn;
    private TextView tvPlace;
    private String apiKey = "AIzaSyBR_HirBjq-d46IBvG40f16aqHJ20LHoSw";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameSignUp = findViewById(R.id.username_signin_et);
        passwordSignUp = findViewById(R.id.password_signin_et);
        signUpbtn = findViewById(R.id.btnSignUp);
        tvPlace = findViewById(R.id.tvPlace);

        mAuth = FirebaseAuth.getInstance();

        signUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp();

            }
        });

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        // Initialize Places.
        Places.initialize(getApplicationContext(), apiKey);

// Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

         autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

         autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
             @Override
             public void onPlaceSelected(@NonNull Place place) {
                 // TODO: Get info about the selected place.
                tvPlace.setText(place.getLatLng().latitude + " , " + place.getLatLng().longitude);
             }

             @Override
             public void onError(@NonNull Status status) {
                 // TODO: Handle the error.ddufehghgefrlnhetdcetfturkinfknd
                 tvPlace.setText("An error occurred: " + status);
             }
         });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void SignUp() {
        String email = usernameSignUp.getText().toString(), password = passwordSignUp.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("SignUp", "createUserWithEmail:success");
                } else {
                    Log.w("SignUp", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}