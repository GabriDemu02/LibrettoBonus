package com.example.unilibrettobonusium;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LibrettoActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView back, button;
    DBHelper db;
    ArrayList<String> enomi, evoti, ecfu;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_libretto);

        recyclerView = findViewById(R.id.recycler);
        button = findViewById(R.id.floatingActionButton);
        back = findViewById(R.id.back_button);

        db = new DBHelper(this);

        enomi = new ArrayList<>();
        evoti = new ArrayList<>();
        ecfu = new ArrayList<>();

        myAdapter = new MyAdapter(LibrettoActivity.this, enomi, evoti, ecfu);
        recyclerView.setAdapter(myAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LibrettoAddActivity.class);
                intent.putExtra("FromHome", false);
                startActivity(intent);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        displaydata();

    }

    @Override
    public void onResume(){
        super.onResume();
    }

    private void displaydata(){
        String user = null;

        Cursor cursor_u = db.getlogged();
        while(cursor_u.moveToNext())
            user = cursor_u.getString(0);

        Cursor cursor = db.getdata_user(user);
        if(cursor.getCount() < 0){
            Toast.makeText(LibrettoActivity.this, "Nessun esame", Toast.LENGTH_SHORT).show();
        }
        else{
            while(cursor.moveToNext()){

                enomi.add(cursor.getString(0));
                evoti.add(cursor.getString(1));
                ecfu.add(cursor.getString(2));

            }
        }
    }
}
