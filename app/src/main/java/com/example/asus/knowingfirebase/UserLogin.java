package com.example.asus.knowingfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserLogin extends AppCompatActivity implements View.OnClickListener {

    private Button signin;
    private TextView signup;
    private EditText edEmail;
    private EditText edPass;
    private ProgressDialog progressdialog;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));

        }

        progressdialog = new ProgressDialog(this);
        signin = (Button) findViewById(R.id.btnSignin);
        signup = (TextView) findViewById(R.id.btnSignup);
        edEmail = (EditText) findViewById(R.id.edEmail1);
        edPass = (EditText) findViewById(R.id.edPassword1);



        signin.setOnClickListener(this);
        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == signin) {
            userLogin();
        }

        if (v == signup) {
            startActivity(new Intent(this,UserRegist.class));
        }
    }

    private void userLogin() {
        String email = edEmail.getText().toString().trim();
        String password = edPass.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this,"Please enter your email",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this,"Please enter your password",Toast.LENGTH_SHORT).show();
            return;
        }

        progressdialog.setMessage("Logging in...");
        progressdialog.show();

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressdialog.dismiss();
                        if (task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        } else {
                            Toast.makeText(UserLogin.this, "Your email / password doesn't match. please try again !", Toast.LENGTH_SHORT).show();
                            edEmail.setText("");
                            edPass.setText("");
                        }
                    }
                });

    }
}
