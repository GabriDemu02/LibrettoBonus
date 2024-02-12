package com.example.unilibrettobonusium;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class LibrettoAddActivity extends AppCompatActivity {

    EditText nome, voto, cfu;
    LinearLayout salva;
    boolean fromHome;
    ImageView back;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_libretto);

        fromHome = getIntent().getBooleanExtra("FromHome", true);

        nome = findViewById(R.id.textedit);
        voto = findViewById(R.id.textedit2);
        cfu = findViewById(R.id.textedit3);

        salva = findViewById(R.id.button_save);
        back = findViewById(R.id.back_button);
        db = new DBHelper(this);

        salva.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                String nomeTEXT = nome.getText().toString();
                String votoTEXT = voto.getText().toString();
                String cfuTEXT = cfu.getText().toString();


                if(nomeTEXT.equals("")){
                    nome.setError("Inserire nome esame!");
                }else if(nomeTEXT.length()>15){
                    nome.setError("Inserire nome di massimo 15 caratteri!");
                }
                if(votoTEXT.equals("")){
                    voto.setError("Inserire voto esame!");
                }else if(Integer.parseInt(votoTEXT) < 18 || Integer.parseInt(votoTEXT) > 30){
                    voto.setError("Inserire voto compreso tra 18 e 30!");
                }
                if(cfuTEXT.equals("")){
                    cfu.setError("Inserire crediti esame!");
                }else if(Integer.parseInt(cfuTEXT) < 3 || Integer.parseInt(cfuTEXT) > 15){
                    cfu.setError("Inserire crediti compresi tra 3 e 15!");
                }


                String user = null;

                Cursor cursor = db.getlogged();
                while(cursor.moveToNext())
                    user = cursor.getString(0);


                if (!(nomeTEXT.equals("") || votoTEXT.equals("") || cfuTEXT.equals("")
                        || nomeTEXT.length()>15
                        || (Integer.parseInt(votoTEXT) < 18 || Integer.parseInt(votoTEXT) > 30)
                        || (Integer.parseInt(cfuTEXT) < 3 || Integer.parseInt(cfuTEXT) > 15)
                )){
                    Boolean salvaesame = db.salvaesame(nomeTEXT, votoTEXT, cfuTEXT, user);
                    if(salvaesame){
                        Toast.makeText(LibrettoAddActivity.this, "Salva esame", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(LibrettoAddActivity.this, "Esame già inserito", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(getApplicationContext(), LibrettoActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(!fromHome){
                    intent = new Intent(getApplicationContext(), LibrettoActivity.class);
                }
                else{
                    intent = new Intent(getApplicationContext(), HomeActivity.class);
                }
                startActivity(intent);
                finish();
            }
        });

    }
}
