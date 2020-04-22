package com.example.vipul.blogapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.vipul.blogapp.Data.CommentRecyclerAdapter;
import com.example.vipul.blogapp.Model.comment;
import com.example.vipul.blogapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class commentActivity extends AppCompatActivity {

    private EditText message;
    private FloatingActionButton send;
    private RecyclerView commentRecycler;
    private String userId, myComment;
    private DatabaseReference commentRefrence;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private List<comment> commentList = new ArrayList<>();
    private CommentRecyclerAdapter commentAdapter;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        message = findViewById(R.id.CommentMessage);
        send = findViewById(R.id.Commentsend);
        commentRecycler = findViewById(R.id.CommentRecycler);
        commentRecycler.setHasFixedSize(true);
        commentRecycler.setLayoutManager(new LinearLayoutManager(this));
        userId = getIntent().getExtras().getString("userId");
        commentRefrence = FirebaseDatabase.getInstance().getReference().child("Comments").child(userId);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        showComments();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadComment();
            }
        });
    }

    private void uploadComment() {
        myComment = message.getText().toString();
        String userName = user.getDisplayName();
        comment cm = new comment(myComment,userName);
        commentRefrence.push().setValue(cm).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Sent", Toast.LENGTH_SHORT).show();
                message.getText().clear();
                showComments();
                //commentList.clear();
            }
        });
    }

    private void showComments() {
        pd = new ProgressDialog(commentActivity.this);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();

        commentRefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentList.clear();
                for (DataSnapshot ds :dataSnapshot.getChildren()){
                    comment cm = ds.getValue(comment.class);
                    commentList.add(new comment(cm.comment,cm.name));
                }
                commentAdapter = new CommentRecyclerAdapter(commentList);
                commentRecycler.setAdapter(commentAdapter);
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        pd.dismiss();
    }

    @Override
    protected void onStart() {
        super.onStart();
        showComments();
    }
}
