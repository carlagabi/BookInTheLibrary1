package com.example.bookinthelibrary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CadastroActivity extends AppCompatActivity {
    private EditText nomeCadastro,emailCadastro,senhaCadastro;
    private Button buttonCadastro,buttonFoto;
    String[] mensagens = {"Preencha todos os campos!", "Cadastro Completo!"};
    String usuarioID;
    Bitmap bitmap;
    private static final int foto = 1;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        IniciarComponentes();

        buttonFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TirarFoto();
            }
        });

        buttonCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome = nomeCadastro.getText().toString();
                String email = emailCadastro.getText().toString();
                String senha = senhaCadastro.getText().toString();

                if(nome.isEmpty() || email.isEmpty() || senha.isEmpty()){
                    Snackbar snackbar = Snackbar.make(v, mensagens[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else{
                    CadastrarUsuario(v);
                }
                Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void TirarFoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, foto);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == foto && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            SalvarImagem(imageBitmap);
        }
    }
    private void SalvarImagem(Bitmap imageBitmap) {
        String timeStamp = new SimpleDateFormat("aaaaBBcc_DDeeff").format(new Date());
        String imgNome = "IMG_" + timeStamp + ".jpg";

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = new File(storageDir, imgNome);
        try {
            FileOutputStream out = new FileOutputStream(imgNome);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private void CadastrarUsuario(View v) {
        String email = emailCadastro.getText().toString();
        String senha = senhaCadastro.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    SalvarDadosUsuario();

                    Snackbar snackbar = Snackbar.make(v, mensagens[1], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                } else {
                    String erro;
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erro = "Digite sua senha em até 8 caracteres!";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erro = "Você já está cadastrado!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erro = "Email inválido!";
                    } catch (Exception e) {
                        erro = "Erro ao Cadastrar!";
                    }
                    Snackbar snackbar = Snackbar.make(v, erro, Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            }
        });
    }
    private void SalvarDadosUsuario(){
        String nome = nomeCadastro.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        Map<String, Object> usuarios = new HashMap<>();
        usuarios.put("nome", "valor");

        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("db", "Dados Salvos!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("db erro", "Os dados não foram salvos!" + e.toString());
                    }
                });
    }
    private void IniciarComponentes(){
        nomeCadastro = findViewById(R.id.nomeCadastro);
        emailCadastro = findViewById(R.id.emailCadastro);
        senhaCadastro = findViewById(R.id.senhaCadastro);
        buttonCadastro = findViewById(R.id.buttonCadastro);

        buttonFoto = findViewById(R.id.buttonFoto);
        imageView = findViewById(R.id.imageView);
    }
}