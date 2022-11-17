package com.example.modul9;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
public class BacaNoteActivity extends AppCompatActivity {
    private RecyclerView noteRecycler;
    private NoteAdapter noteAdapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baca_note);
        noteRecycler = findViewById(R.id.recyclerView);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        databaseReference = firebaseDatabase.getReference("notes").child(mAuth.getUid());
        setUpRecyclerView();
        initDataset();
    }
    private void setUpRecyclerView(){
        noteAdapter = new NoteAdapter(databaseReference);
        noteRecycler.setAdapter(noteAdapter);
        noteRecycler.setLayoutManager(new
                LinearLayoutManager(this));
    }
    private void initDataset() {
        ValueEventListener notesListener = new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Note> notes = new ArrayList<>();
                for(DataSnapshot data :
                        dataSnapshot.getChildren()){
                    Note note = data.getValue(Note.class);
                    note.setId(data.getKey());
                    notes.add(note);
                }
                noteAdapter.setItems(notes);
                noteAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        databaseReference.addValueEventListener(notesListener);
    }
}