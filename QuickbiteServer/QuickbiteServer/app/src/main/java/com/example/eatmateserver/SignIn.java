package com.example.eatmateserver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eatmateserver.Common.Common;
import com.example.eatmateserver.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    EditText edtPhone,edtPassword;
    Button btnSignIn;

    FirebaseDatabase db;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtPhone = (EditText) findViewById(R.id.edtPhone);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);

        //Init Firebase
        db = FirebaseDatabase.getInstance();
        users = db.getReference("User");
        
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInUser(edtPhone.getText().toString(),edtPassword.getText().toString());
            }
        });
    }

    private void signInUser(String phone, String password) {
        ProgressDialog mDialog = new ProgressDialog(SignIn.this);
        mDialog.setMessage("Loading...");
        mDialog.show();

        final String localPhone = phone;
        final String localPassword = password;
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(localPhone).exists()){
                    mDialog.dismiss();
                    User user = snapshot.child(localPhone).getValue(User.class);
                    user.setPhone(localPhone);
                    if(Boolean.parseBoolean(user.getIsStaff())){
                        if(user.getPassword().equals(localPassword)){
                            Intent login = new Intent(SignIn.this, Home.class);
                            Common.currentUser = user;
                            startActivity(login);
                            finish();
                        }
                        else {
                            Toast.makeText(SignIn.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                        Toast.makeText(SignIn.this, "You are not Staff!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    mDialog.dismiss();
                    Toast.makeText(SignIn.this, "User Does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}