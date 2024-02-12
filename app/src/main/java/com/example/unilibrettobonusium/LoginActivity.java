package com.example.unilibrettobonusium;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    Button btnlogin, btnreg;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username1);
        password = findViewById(R.id.password1);
        btnlogin = findViewById(R.id.btnsignin1);
        btnreg = findViewById(R.id.btnreg);
        db = new DBHelper(this);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("")){
                    username.setError("Inserire nome utente!");
                }
                if(pass.equals("")){
                    password.setError("Inserire la password!");
                }

                if(!(user.equals("") || pass.equals(""))){
                    Boolean checkuserpass = db.checkusernamepassword(user, pass);
                    if(checkuserpass){
                        Toast.makeText(getApplicationContext(), "Accesso riuscito!", Toast.LENGTH_SHORT).show();
                        db.updateData(user, "t");
                        Intent intent  = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "Accesso fallito!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });


    }
}