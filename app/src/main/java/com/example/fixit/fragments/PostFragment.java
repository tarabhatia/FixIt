package com.example.fixit.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fixit.Issue;
import com.example.fixit.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PostFragment extends Fragment {

    private final static int PICK_PHOTO_CODE = 1046;
    private final static int PERMISSION_CODE = 1001;
    private final static String POST_ROUTE = "posts";
    private final static String IMAGE_STORAGE_ROUTE = "images/";
    private static final String IMAGE_FORMAT = ".jpg";
    private static int size;
    private final static int STEP = 20;


    private ImageButton btnPickFromGallery;
    private ImageView ivPreview;
    private EditText etDescription;
    private Button btnSubmit;

    private FirebaseStorage mStorage;
    private FirebaseDatabase mDatabase;
    private Issue issue;
    private Uri file;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnPickFromGallery = view.findViewById(R.id.btnPickFromGallery);
        ivPreview = view.findViewById(R.id.ivPreview);
        etDescription = view.findViewById(R.id.etDescription);
        btnSubmit = view.findViewById(R.id.btnSubmit);


        // Initialize Storage
        mStorage = FirebaseStorage.getInstance();

        // Initialize database
        mDatabase = FirebaseDatabase.getInstance();

        btnPickFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPickPhoto();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postIssue();
            }
        });
        file = null;
        size = 0;

    }

    private void postIssue() {
        DatabaseReference mPostReference = mDatabase.getReference().child(POST_ROUTE).push();
        String key = mPostReference.getKey();
        String description = etDescription.getText().toString();
        issue = new Issue(description, key);
        mPostReference.setValue(issue);
        upLoadFileToStorage(key);
    }

    // Trigger gallery selection for a photo
    public void onPickPhoto() {
        // Create intent for picking a photo from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Bring up gallery to select a photo
           startActivityForResult(intent, PICK_PHOTO_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null) {
            file = data.getData();
            Toast.makeText(getContext(), "File Ready to Upload", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getContext(), "Error selecting image", Toast.LENGTH_LONG).show();
        }
    }

    public void upLoadFileToStorage(String key){
        if(file != null) {
            StorageReference mImageRef = mStorage.getReference().child(IMAGE_STORAGE_ROUTE + key + IMAGE_FORMAT);
            mImageRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    // Successfully uploaded
                    Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_LONG).show();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Toast.makeText(getContext(), "Uploading failed", Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            Toast.makeText(getContext(), "Select an image before issueing", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case PERMISSION_CODE :{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onPickPhoto();
                } else {
                    Toast.makeText(getContext(), "Permission denied :(", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


}
