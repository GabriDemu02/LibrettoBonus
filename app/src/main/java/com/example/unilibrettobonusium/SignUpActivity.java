package com.example.unilibrettobonusium;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {

    Button signup;
    ImageView back;
    EditText Username, Nome, Cognome, Password, RePassword;
    TextView Data;
    DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Username = findViewById(R.id.username);
        Nome = findViewById(R.id.nm);
        Cognome = findViewById(R.id.cm);

        Data = findViewById(R.id.data);
        Data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int anno = cal.get(Calendar.YEAR);
                int mese = cal.get(Calendar.MONTH);
                int giorno = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SignUpActivity.this,
                        AlertDialog.THEME_HOLO_DARK,
                        dateSetListener,
                        anno, mese, giorno
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;

                String d = month + "/" + dayOfMonth + "/" + year;
                Data.setText(d);
            }
        };

        Password = findViewById(R.id.pass);
        RePassword = findViewById(R.id.pass1);

        signup = findViewById(R.id.registrati);
        back = findViewById(R.id.back_button_to_login);

        DBHelper DB = new DBHelper(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = Username.getText().toString();
                String nome = Nome.getText().toString();
                String cognome = Cognome.getText().toString();
                String data = Data.getText().toString();
                String pass = Password.getText().toString();
                String repass = RePassword.getText().toString();

                if(user.equals("")){
                    Username.setError("Inserire nome utente!");
                }else if(user.length()>15){
                    Username.setError("Inserire nome utente di massimo 15 caratteri!");
                }
                if(nome.equals("")){
                    Nome.setError("Inserire nome!");
                }else if(nome.length()>15){
                    Nome.setError("Inserire nome di massimo 15 caratteri!");
                }
                if(cognome.equals("")){
                    Cognome.setError("Inserire cognome!");
                }else if(cognome.length()>15){
                    Cognome.setError("Inserire cognome di massimo 15 caratteri!");
                }
                if(data.equals("")){
                    Data.setError("Inserire data di nascita!");
                }else Data.setError("");
                if(pass.equals("")){
                    Password.setError("Inserire password!");
                }
                if(repass.equals("")){
                    RePassword.setError("Ripetere password!");
                }

                if(!(user.equals("") || nome.equals("") || cognome.equals("") || data.equals("") || pass.equals("") || repass.equals(""))){
                    if(pass.equals(repass)){
                        Boolean checkuser = DB.checkusername(user);
                        if(!checkuser){
                            Boolean insert = DB.registrazione(user, nome, cognome, data, pass, "f");
                            if(insert){
                                Toast.makeText(SignUpActivity.this, "Registrazione riuscita!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(SignUpActivity.this, "Registrazione fallita!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(SignUpActivity.this, "Utente già esistente!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(SignUpActivity.this, "Le password non corrispondono!", Toast.LENGTH_SHORT).show();
                    }
                } }
        });


    }
}