package com.example.bookinthelibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FormPagamentoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pagamento);
    }

    public void mainactivity(View view){
        Intent in = new Intent(FormPagamentoActivity.this, MainActivity.class);
        startActivity(in);
    }

}