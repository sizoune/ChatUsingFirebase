package com.example.asus.knowingfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserRegist extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Button register;
    private EditText edemail;
    private EditText edpassword;
    private TextView signin;
    private ProgressDialog progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_regist);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        progressdialog = new ProgressDialog(this);

        register = (Button) findViewById(R.id.btnRegister);
        edemail = (EditText) findViewById(R.id.edEmail);
        edpassword = (EditText) findViewById(R.id.edPassword);
        signin = (TextView) findViewById(R.id.btnSignin);

        register.setOnClickListener(this);
        signin.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v == register) {
            registerUser();
        }
        if (v == signin) {
            startActivity(new Intent(this, UserLogin.class));
        }
    }

    private void registerUser() {
        String email = edemail.getText().toString().trim();
        String password = edpassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressdialog.setMessage("Registering User...");
        progressdialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressdialog.dismiss();
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        } else {
                            progressdialog.dismiss();
                            Toast.makeText(UserRegist.this, "Could not Registered. please try again !", Toast.LENGTH_SHORT).show();
                            edemail.setText("");
                            edpassword.setText("");
                        }
                    }
                });
    }
}
