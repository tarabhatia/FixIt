package com.example.fixit.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
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
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.fixit.Issue;
import com.example.fixit.R;
import com.example.fixit.UserActivity;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteFragment;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Arrays;

import static android.app.Activity.RESULT_OK;

public class PostFragment extends Fragment {

    Button btnTest;

    // galley pictures
    private final static int PICK_PHOTO_CODE = 1046;
    private final static int PERMISSION_CODE = 1001;
    private final static String POST_ROUTE = "posts";
    private final static String IMAGE_STORAGE_ROUTE = "images/";
    private static final String IMAGE_FORMAT = ".jpg";
    private static int size;
    private final static int STEP = 20;

    private FragmentActivity myContext;


    // launch camera
    public final String APP_TAG = "MyCustomApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    File photoFile;

    // UI elements for xml
    private EditText etDescription;
    private ImageButton btnCaptureImage;
    private ImageView ivPostImage;
    private Button btnSubmit;
    private ImageButton btnPickFromGallery;
    private EditText etTitle;
    private EditText etLat;
    private EditText etLon;


    // storage for database
    private FirebaseStorage mStorage;
    private FirebaseDatabase mDatabase;
    private Issue issue;
    private LatLng location;
    private Uri file;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etDescription = view.findViewById(R.id.etDescription);
        btnCaptureImage = view.findViewById(R.id.btnCaptureImage);
        ivPostImage = view.findViewById(R.id.ivPicture);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnPickFromGallery = view.findViewById(R.id.btnPickFromGallery);
        etTitle = view.findViewById(R.id.etTitle);
        issue = new Issue();



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


        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLaunchCamera(v);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Button works!", Toast.LENGTH_SHORT).show();
                postIssue();
            }
        });

        if (!Places.isInitialized()) {
            Places.initialize(getActivity().getApplicationContext(), "AIzaSyBR_HirBjq-d46IBvG40f16aqHJ20LHoSw");
        }



        // Initialize Places.
        Places.initialize(getActivity().getApplicationContext(), "AIzaSyBR_HirBjq-d46IBvG40f16aqHJ20LHoSw");




        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(getActivity().getApplicationContext());

        // Initialize the AutocompleteSupportFragment.

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                    location = place.getLatLng();

            }

            @Override
            public void onError(@NonNull Status status) {
                Log.d("testing", "place");
            }
        });

    }

    public void onLaunchCamera(View view) {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference to access to future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(APP_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                ivPostImage.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }

        if (data != null) {
            file = data.getData();
            Toast.makeText(getContext(), "File Ready to Upload", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "Error selecting image", Toast.LENGTH_LONG).show();
        }

    }

    private void postIssue() {
        DatabaseReference mPostReference = mDatabase.getReference().child(POST_ROUTE).push();
        String key = mPostReference.getKey();
        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();
        Double latitude = location.latitude;
        Double longitude = location.longitude;
        issue = new Issue(title, description, latitude, longitude, key);
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

    public void upLoadFileToStorage(String key) {
        if (file != null) {
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
        } else {
            Toast.makeText(getContext(), "Select an image before issueing", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onPickPhoto();
                } else {
                    Toast.makeText(getContext(), "Permission denied 😞", Toast.LENGTH_LONG).show();
                }
            }
        }


    }
}