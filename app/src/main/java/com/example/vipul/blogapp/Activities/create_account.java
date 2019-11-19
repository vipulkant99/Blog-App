package com.example.vipul.blogapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.vipul.blogapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class create_account extends AppCompatActivity {
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private Button createAccount;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;
    private ImageButton profilePic;
    private final static int code=1;
    private Uri imageUri=null ;
    private StorageReference storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference().child("MUsers");
        auth=FirebaseAuth.getInstance();
        progressDialog= new ProgressDialog(this);
        firstName=(EditText) findViewById(R.id.firstName);
        lastName=(EditText) findViewById(R.id.lastName);
        email=(EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);
        createAccount=(Button) findViewById(R.id.createAccount);
        storage = FirebaseStorage.getInstance().getReference().child("Profile_Pics");
        profilePic=(ImageButton) findViewById(R.id.profilePic);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,code);
            }
        });
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAccount();
            }
        });
    }

    private void createNewAccount() {

        final String name = firstName.getText().toString().trim();
        final String lname = lastName.getText().toString().trim();
        String em = email.getText().toString().trim();
        String pwd = password.getText().toString().trim();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(lname)
                && !TextUtils.isEmpty(em) && !TextUtils.isEmpty(pwd)) {

            progressDialog.setMessage("Creating Account...");
            progressDialog.show();

            auth.createUserWithEmailAndPassword(em, pwd)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            if (authResult != null) {

                                StorageReference imagePath = storage.child("MBlog_Profile_Pics")
                                        .child(imageUri.getLastPathSegment());

                                imagePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                        String userid = auth.getCurrentUser().getUid();

                                        DatabaseReference currenUserDb = databaseReference.child(userid);
                                        currenUserDb.child("firstname").setValue(name);
                                        currenUserDb.child("lastname").setValue(lname);
                                        currenUserDb.child("image").setValue(imageUri.toString());


                                        progressDialog.dismiss();

                                        //send users to postList
                                        Intent intent = new Intent(create_account.this, postListActivity.class );
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);


                                    }
                                });





                            }

                        }
                    });


        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == code && resultCode == RESULT_OK) {
            Uri mImageUri = data.getData();

            CropImage.activity(mImageUri)
                    .setAspectRatio(1,1)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);



        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();

                profilePic.setImageURI(imageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
