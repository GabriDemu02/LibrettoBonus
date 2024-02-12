package com.example.unilibrettobonusium;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class InfoActivity extends AppCompatActivity {

    ImageView back;
    TextView user, nome, cognome, data;
    Button logout;
    DBHelper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_info);

        back = findViewById(R.id.back_button);
        user = findViewById(R.id.user);
        nome = findViewById(R.id.nm);
        cognome = findViewById(R.id.cm);
        data = findViewById(R.id.data);
        logout = findViewById(R.id.btnlogout);

        db = new DBHelper(this);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mostraAlertDialog(InfoActivity.this);

            }
        });

        displaydata();

    }

    private void mostraAlertDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Elimina esame.")
                .setMessage("Eliminare esame?");

        builder.setPositiveButton("Conferma", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                Cursor cursor = db.getlogged();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                while(cursor.moveToNext()){
                    db.updateData(cursor.getString(0), "f");
                }
                startActivity(intent);
                finish();

                dialog.dismiss(); // Chiudere l'AlertDialog
            }

        });

        builder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void displaydata(){
        Cursor cursor = db.getlogged();

        while(cursor.moveToNext()){

            user.setText(cursor.getString(0));
            nome.setText(cursor.getString(1));
            cognome.setText(cursor.getString(2));
            data.setText(cursor.getString(3));

        }
    }
}
