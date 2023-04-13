package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextNote;
    private RadioButton radioLow;
    private RadioButton radioMedium;
    private Button btnSave;

    private AddNoteViewModel addNoteViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        addNoteViewModel = new ViewModelProvider(this).get(AddNoteViewModel.class);
        initViews();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });
    }


    private void initViews(){
        editTextNote = findViewById(R.id.editTextNote);
        radioLow = findViewById(R.id.radioLow);
        radioMedium = findViewById(R.id.radioMedium);
        btnSave = findViewById(R.id.btnSave);
    }
    private void saveNote(){
        String text = editTextNote.getText().toString().trim();
        int priority = getPriority();
        Note note = new Note(text, priority);
        addNoteViewModel.saveNote(note);
        addNoteViewModel.getShouldCloseScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldCloseScreen) {
                if(shouldCloseScreen){
                    finish();
                }
            }
        });
    }
    private int getPriority(){
        int priority;
        if(radioLow.isChecked()) priority = 0;
        else if (radioMedium.isChecked()) priority = 1;
        else priority = 2;
        return priority;
    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,AddNoteActivity.class);
        return intent;
    }

}