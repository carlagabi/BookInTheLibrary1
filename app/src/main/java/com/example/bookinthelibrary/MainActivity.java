package com.example.bookinthelibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void carrinhoactivity(View view){
        Intent in = new Intent(MainActivity.this, CarrinhoActivity.class);
        startActivity(in);
    }

}