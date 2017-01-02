package com.example.asus.knowingfirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseDatabase data;
    private FirebaseListAdapter<ChatMessage> adapter;
    private CustomAdapter customAdapter;
    private TextView textEmail;
    private TextView nama;
    private TextView waktu;
    private TextView pesan;
    private Button btnLogout;
    private Button btnSend;
    private EditText textPesan;
    private ListView isiPesan;
    private FirebaseUser user;
    private ArrayList<ChatMessage> name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        data = FirebaseDatabase.getInstance();
        name = new ArrayList<>();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, UserLogin.class));
        }

        user = mAuth.getCurrentUser();

        textEmail = (TextView) findViewById(R.id.userEmail);
        textPesan = (EditText) findViewById(R.id.edPesan);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        textEmail.setText("Welcome " + user.getEmail());

        btnLogout.setOnClickListener(this);
        btnSend.setOnClickListener(this);

        isiPesan = (ListView) findViewById(R.id.chatList);
        data.getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    ChatMessage cm = new ChatMessage();
                    cm.setMessageText(data.getValue(ChatMessage.class).getMessageText());
                    cm.setMessageUser(data.getValue(ChatMessage.class).getMessageUser());
                    name.add(cm);
                    //Toast.makeText(ProfileActivity.this, cm.getMessageText(), Toast.LENGTH_SHORT).show();
                }
                customAdapter = new CustomAdapter(name, getApplicationContext(), user.getEmail());
                isiPesan.setAdapter(customAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        displayChat();
    }

    @Override
    public void onClick(View v) {
        if (v == btnLogout) {
            mAuth.signOut();
            finish();
            startActivity(new Intent(this, UserLogin.class));
        }

        if (v == btnSend) {
            String pesan = textPesan.getText().toString().trim();
            if (!TextUtils.isEmpty(pesan)) {
                data.getReference()
                        .push()
                        .setValue(new ChatMessage((pesan),
                                mAuth.getCurrentUser().getEmail())
                        );
            }
            textPesan.setText("");
        }
    }

    private void displayChat() {
//        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
//                R.layout.message, data.getReference()) {
//            @Override
//            protected void populateView(View v, ChatMessage model, int position) {
//                nama = (TextView) v.findViewById(R.id.textNama);
//                waktu = (TextView) v.findViewById(R.id.textWaktu);
//                pesan = (TextView) v.findViewById(R.id.textPesan);
//
//                pesan.setText(model.getMessageText());
//                nama.setText(model.getMessageUser());
//                waktu.setText(DateFormat.format("(HH:mm:ss)", model.getMessageTime()));
//            }
//        };
//        isiPesan.setAdapter(adapter);
    }


//    private void retrieveData() {
//        data.getReference().addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                getUpdate(dataSnapshot);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }


    public FirebaseUser getUser() {
        return user;
    }
}
