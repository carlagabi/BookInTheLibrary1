package com.example.bookinthelibrary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FormPagamentoActivity extends AppCompatActivity {

    private Button buttonFormPagamento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pagamento);

        buttonFormPagamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormPagamentoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}