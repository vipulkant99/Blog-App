package com.example.vipul.blogapp.Activities;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.vipul.blogapp.Data.BlogRecyclerAdapter;
import com.example.vipul.blogapp.Model.Blog;
import com.example.vipul.blogapp.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class postListActivity extends AppCompatActivity {
    private DatabaseReference refrence;
    private FirebaseDatabase database;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private RecyclerView recyclerView;
    private BlogRecyclerAdapter blogRecyclerAdapter;
    private List<Blog> blogList;
    private FloatingActionButton addPost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        refrence = database.getReference().child("MBlog");
        refrence.keepSynced(true);
        blogList = new ArrayList<>();
        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addPost = findViewById(R.id.addPost);
        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
        String umail=user.getEmail();
        if(umail.contains("gla.ac.in")){
            addPost.setVisibility(View.VISIBLE);
        }else{
            addPost.setVisibility(View.INVISIBLE);
        }
        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(postListActivity.this,AddPostActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_signout:
                AuthUI.getInstance().signOut(postListActivity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                startActivity(new Intent(postListActivity.this,MainActivity.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(postListActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
       /* refrence.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                blogList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Blog blog = ds.getValue(Blog.class);
                    blogList.add(new Blog(blog.title1,blog.decription,blog.image1,blog.timestamp,blog.userid,blog.userName,blog.userMail));
                }

                blogRecyclerAdapter = new BlogRecyclerAdapter(postListActivity.this,blogList);
                recyclerView.setAdapter(blogRecyclerAdapter);
                blogRecyclerAdapter.notifyDataSetChanged();
                Collections.reverse(blogList);
                *//* Blog blog = dataSnapshot.getValue(Blog.class);
                blogList.add(blog);*//*
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                blogList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Blog blog = ds.getValue(Blog.class);
                    blogList.add(new Blog(blog.title1,blog.decription,blog.image1,blog.timestamp,blog.userid,blog.userName,blog.userMail));
                }

                blogRecyclerAdapter = new BlogRecyclerAdapter(postListActivity.this,blogList);
                recyclerView.setAdapter(blogRecyclerAdapter);
                blogRecyclerAdapter.notifyDataSetChanged();
                Collections.reverse(blogList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}



/*
if (user != null && auth != null){
        startActivity(new Intent(postListActivity.this,AddPostActivity.class));
        finish();
        }*/
