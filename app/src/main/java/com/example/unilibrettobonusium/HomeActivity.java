package com.example.unilibrettobonusium;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    ImageView lib_bt, lib_add_bt, pers_area_bt;
    TextView user, laurea, med_a, med_p;
    DBHelper db;

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = new DBHelper(this);

        user = findViewById(R.id.user);
        lib_bt = findViewById(R.id.listaesami);
        lib_add_bt = findViewById(R.id.aggiungiEsami);
        pers_area_bt = findViewById(R.id.user_bt);
        laurea = findViewById(R.id.text_laurea);
        med_a = findViewById(R.id.text_med_a);
        med_p = findViewById(R.id.text_med_p);

        pers_area_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(intent);
                finish();
            }
        });

        lib_bt.setOnClickListener(view -> {
                Intent intent = new Intent(getApplicationContext(), LibrettoActivity.class);
                startActivity(intent);
                finish();
            });

        lib_add_bt.setOnClickListener(view -> {
                Intent intent = new Intent(getApplicationContext(), LibrettoAddActivity.class);
                intent.putExtra("FromHome", true);
                startActivity(intent);
                finish();
            });

        String user_logged = null;

        Cursor cursor_u = db.getlogged();
        while(cursor_u.moveToNext())
            user_logged = cursor_u.getString(0);

        user.setText(user_logged);

        Cursor cursor = db.getdata_user(user_logged);

        Float lau_val = lau(cursor);

        if(lau_val.isNaN()) laurea.setText(String.format("%.2f", 0.0));
        else laurea.setText(String.format("%.2f", lau_val));

        Cursor cursor1 = db.getdata_user(user_logged);

        Float media_a_val = media_a(cursor1);

        if(media_a_val.isNaN()) med_a.setText(String.format("%.2f", 0.0));
        else med_a.setText(String.format("%.2f", media_a_val));

        Cursor cursor2 = db.getdata_user(user_logged);

        Float media_p_val = media_p(cursor2);

        if(media_p_val.isNaN()) med_p.setText(String.format("%.2f", 0.0));
        else med_p.setText(String.format("%.2f", media_p_val));
    }

    private float media_a(Cursor cursor){
        float sum = 0;
        float n = 0;

        while(cursor.moveToNext()){
            sum = sum + (cursor.getInt(1));
            n += 1;
        }

        return sum/n;
    }

    private float media_p(Cursor cursor){
        float sum = 0;
        float cr = 0;

        while(cursor.moveToNext()){
            sum = sum + (cursor.getInt(1) * cursor.getInt(2));
            cr = cr + (cursor.getInt(2));
        }

        return sum/cr;
    }

    private float lau(Cursor cursor){
        return (media_p(cursor)*11)/3;
    }
}