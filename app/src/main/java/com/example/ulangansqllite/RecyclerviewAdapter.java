package com.example.ulangansqllite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.UserViewHolder> {
    List<Content> listContent;

    public RecyclerviewAdapter(List<Content> listContent){
        this.listContent = listContent;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter, parent, false);
        UserViewHolder userViewHolder = new UserViewHolder(view);

        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position){
        final Content content = listContent.get(position);
        holder.judul.setText(content.getJudul());
        holder.desk.setText(content.getDesk());
        holder.tgl.setText(content.getTgl());
    }

    @Override
    public int getItemCount(){
        return listContent.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        TextView judul, desk, tgl;
        private int id;
        private String Judul;
        private String deskripsi;
        CardView cardView;
        private List<Content> listContent;

        public UserViewHolder(@NonNull View itemView){
            super(itemView);
            judul = itemView.findViewById(R.id.Listjudul);
            desk = itemView.findViewById(R.id.Listdeskripsi);
            tgl = itemView.findViewById(R.id.Listtgl);

            cardView = itemView.findViewById(R.id.cardView);
            cardView.setOnLongClickListener(v ->{
                alertDialogAction(itemView.getContext(), getAdapterPosition());

                return false;
            });
        }

        public void alertDialogAction(Context context, int position){
            String[] optionDialog = { "Edit", "Delete"};
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            listContent = databaseHelper.selectContentList();

            builder.setTitle("Choose Options");
            builder.setItems(optionDialog, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(which == 0){
                        id = listContent.get(position).getId();
                        Judul = listContent.get(position).getJudul();
                        deskripsi = listContent.get(position).getDesk();

                        Intent intent = new Intent(context, EditActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("judul", Judul);
                        intent.putExtra("deskripsi", deskripsi);

                        context.startActivity(intent);
                    } else {
                        DatabaseHelper databaseHelper = new DatabaseHelper(context);
                        databaseHelper.delete(listContent.get(position).getId());

                        listContent = databaseHelper.selectContentList();
                        HomeActivity Home = new HomeActivity();
                        Home.setupRecyclerView(context, listContent, HomeActivity.recyclerView);
                        Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}
