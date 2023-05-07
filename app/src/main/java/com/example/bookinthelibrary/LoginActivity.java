package com.example.bookinthelibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.ktx.Firebase;

public class LoginActivity extends AppCompatActivity {

    private TextView cadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        IniciarComponentes();

        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });
    }

    private void IniciarComponentes(){
        cadastro = findViewById(R.id.cadastro);
    }

    public void mainactivity(View view){
        Intent in = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(in);
    }
}