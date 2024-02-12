package com.example.unilibrettobonusium;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    static Context context;
    ArrayList<String> nome, voto, cfu;
    LayoutInflater layoutInflater;
    static DBHelper db;

    public MyAdapter(Context context, ArrayList<String> nome, ArrayList<String> voto, ArrayList<String> cfu){
        this.context = context;
        this.nome = nome;
        this.voto = voto;
        this.cfu = cfu;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_libretto, parent, false);
        return new MyViewHolder(view).LinkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.Nome.setText(String.valueOf(nome.get(position)));
        holder.Voto.setText(String.valueOf(voto.get(position)));
        holder.Cfu.setText(String.valueOf(cfu.get(position)));


    }

    @Override
    public int getItemCount() {
        return nome.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Nome, Voto, Cfu;
        ImageView d;

        private MyAdapter adapter;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Nome = itemView.findViewById(R.id.textView);
            Voto = itemView.findViewById(R.id.textView2);
            Cfu = itemView.findViewById(R.id.textView3);
            d = itemView.findViewById(R.id.del_button);

            db = new DBHelper(context);

            d.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mostraAlertDialog(context);

                }
            });

        }

        private void mostraAlertDialog(Context context) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle("Elimina esame.")
                    .setMessage("Eliminare esame?");

            builder.setPositiveButton("Conferma", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    String n = Nome.getText().toString();

                    String u = null;

                    Cursor cursor_u = db.getlogged();
                    while(cursor_u.moveToNext())
                        u = cursor_u.getString(0);

                    Integer delete_e = db.elimina_e(n, u);
                    if(delete_e>0) {
                        adapter.nome.remove(getAdapterPosition());
                        adapter.voto.remove(getAdapterPosition());
                        adapter.cfu.remove(getAdapterPosition());
                        adapter.notifyItemRemoved(getAdapterPosition());
                    }

                    dialog.dismiss();
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

        public MyViewHolder LinkAdapter(MyAdapter adapter){
            this.adapter = adapter;
            return this;
        }


    }
}
