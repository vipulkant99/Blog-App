package com.example.vipul.blogapp.Activities;

import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vipul.blogapp.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int code = 7717;
    private Button login;
    private List<AuthUI.IdpConfig> provider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=(Button) findViewById(R.id.signUpButton);
        provider = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build()

        );
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSiginOptions();
            }
        });


    }

    private void showSiginOptions() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(provider).build(),code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == code){
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                //Toast.makeText(this,""+user.getEmail(),Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this,postListActivity.class));

            }
            else {
                Toast.makeText(this,""+response.getError().getMessage(),Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
//        String umail=user.getEmail();
//        if(umail.contains("gla.ac.in"))
        if(user!=null){
            startActivity(new Intent(MainActivity.this,postListActivity.class));
        }
    }
}
