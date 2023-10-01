package com.prathambudhwani.notes_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleNotesAdapter extends RecyclerView.Adapter<RecycleNotesAdapter.ViewHolder> {
    Context context;
    ArrayList<Note> arrNotes = new ArrayList<>();
    DatabaseHelper databaseHelper;

    RecycleNotesAdapter(Context context, ArrayList<Note> arrNotes, DatabaseHelper databaseHelper) {
        this.context = context;
        this.arrNotes = arrNotes;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.note_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txttitle.setText(arrNotes.get(position).getTitle());
        holder.txtcontent.setText(arrNotes.get(position).getContent());

        holder.llrow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                deleteItem(position);


                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrNotes.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txttitle, txtcontent;
        LinearLayout llrow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txttitle = itemView.findViewById(R.id.txttitle);
            txtcontent = itemView.findViewById(R.id.txtcontent);
            llrow = itemView.findViewById(R.id.llrow);
        }
    }

    public void deleteItem(int position) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databaseHelper.noteDao().deleteNote(new Note(arrNotes.get(position).getId(),
                                arrNotes.get(position).getTitle(),
                                arrNotes.get(position).getContent()));
                        ((MainActivity) context).showNotes();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }
}
