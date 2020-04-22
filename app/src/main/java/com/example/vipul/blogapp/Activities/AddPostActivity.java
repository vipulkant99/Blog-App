   package com.example.vipul.blogapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.vipul.blogapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

   public class AddPostActivity extends AppCompatActivity {
       private ImageButton postImage;
       private EditText postTitle;
       private EditText postDesc;
       private Button submit;
       private DatabaseReference postDatabase;
       private FirebaseAuth auth;
       private FirebaseUser user;
       private ProgressDialog progress;
       private static final int code = 1;
       private Uri imageUri;
       private StorageReference storage;

       @Override
       protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_add_post);

           progress = new ProgressDialog(this);
           auth = FirebaseAuth.getInstance();
           user = auth.getCurrentUser();
           storage = FirebaseStorage.getInstance().getReference();
           postDatabase = FirebaseDatabase.getInstance().getReference().child("MBlog");
           postImage = (ImageButton) findViewById(R.id.imageButton);
           postTitle = (EditText) findViewById(R.id.titleEt);
           postDesc = (EditText) findViewById(R.id.descriptionEt);
           submit = (Button) findViewById(R.id.submit);

           postImage.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                   galleryIntent.setType("image/*");
                   startActivityForResult(galleryIntent, code);
               }
           });

           submit.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   startPosting();

               }
           });
       }

       @Override
       protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
           super.onActivityResult(requestCode, resultCode, data);

           if (requestCode == code && resultCode == RESULT_OK) {
               imageUri = data.getData();
               postImage.setImageURI(imageUri);
           }
       }

       private void startPosting() {
           progress.setMessage("Posting.....");
           progress.show();
           final String postId=postDatabase.push().getKey();
           final String titleVal = postTitle.getText().toString().trim();
           final String descVal = postDesc.getText().toString().trim();
           if (!TextUtils.isEmpty(titleVal) && !TextUtils.isEmpty(descVal)) {
               final StorageReference filepath= storage.child("MBlog_images").child(imageUri.getLastPathSegment());
                filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String downloadUrl= uri.toString();
                                DatabaseReference newpost = postDatabase.push();
                                Map<String, String> datatosave =new HashMap<>();
                                datatosave.put("title1",titleVal);
                                datatosave.put("decription",descVal);
                                datatosave.put("image1",downloadUrl.toString());
                                datatosave.put("timestamp",String.valueOf(java.lang.System.currentTimeMillis()));
                                datatosave.put("userid",user.getUid());
                                datatosave.put("userName",user.getDisplayName());
                                datatosave.put("userMail",user.getEmail());
                                datatosave.put("postId",postId);
                                newpost.setValue(datatosave);
                            }
                        });

                       //Uri downloadurl = taskSnapshot.getDownloadUrl();
                        //String downloadUrl=taskSnapshot.getStorage().getDownloadUrl();




                        progress.dismiss();
                        startActivity(new Intent(AddPostActivity.this,postListActivity.class));
                        finish();
                    }
                });

           }
       }
   }
