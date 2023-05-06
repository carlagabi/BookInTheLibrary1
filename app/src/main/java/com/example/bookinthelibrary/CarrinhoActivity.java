package com.example.bookinthelibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CarrinhoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);
    }

    public void formpagamentoactivity(View view){
        Intent in = new Intent(CarrinhoActivity.this, FormPagamentoActivity.class);
        startActivity(in);
    }

}