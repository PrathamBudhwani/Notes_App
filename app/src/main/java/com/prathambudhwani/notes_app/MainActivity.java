package com.prathambudhwani.notes_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button btncreatenote;
    FloatingActionButton fabadd;
    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    LinearLayout llnonotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initvar();

        showNotes();

        fabadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.add_note_layout);

                TextInputEditText edttitle;
                EditText edtcontent;
                Button btnadd;

                edttitle = dialog.findViewById(R.id.edttitle);
                edtcontent = dialog.findViewById(R.id.edtcontent);
                btnadd = dialog.findViewById(R.id.btnadd);

                btnadd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String title = Objects.requireNonNull(edttitle.getText()).toString();
                        String content = Objects.requireNonNull(edtcontent.getText()).toString();

                        if (!content.equals("")) {

                            databaseHelper.noteDao().addNote(new Note(title,content));
                            showNotes();

                            dialog.dismiss();

                        } else {
                            Toast.makeText(MainActivity.this, "Please enter something", Toast.LENGTH_SHORT).show();
                        }
                    }


                });
                dialog.show();

            }
        });

        btncreatenote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabadd.performClick();
            }
        });


    }
    public void showNotes() {
        ArrayList<Note> arrNotes= (ArrayList<Note>) databaseHelper.noteDao().getNotes();

        if (arrNotes.size()>0){
            recyclerView.setVisibility(View.VISIBLE);
            llnonotes.setVisibility(View.GONE);

            recyclerView.setAdapter(new RecycleNotesAdapter(this,arrNotes,databaseHelper));
        }
        else {
            llnonotes.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void initvar() {
        btncreatenote = findViewById(R.id.btncreatenote);
        recyclerView = findViewById(R.id.recyclerview);
        fabadd = findViewById(R.id.floating);
        llnonotes=findViewById(R.id.LLnoNotes);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        databaseHelper=DatabaseHelper.getInstance(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.opt_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.info) {
            Intent intent = new Intent(MainActivity.this, Info.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}