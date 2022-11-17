package com.example.modul9;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
public class UpdateActivity extends AppCompatActivity {
    private String itemId;
    private EditText etTitle;
    private EditText etDesc;
    private Button btnSubmit;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("notes");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Intent intent = getIntent();
        itemId = (String) intent.getExtras().get("id");
        etTitle = findViewById(R.id.et_title_update);
        etDesc = findViewById(R.id.et_description_update);
        btnSubmit = findViewById(R.id.btn_submit_update);
        btnSubmit.setOnClickListener(v -> {
            updateData(itemId);
        });
    }
    public void updateData(String key) {
        String title = etTitle.getText().toString();
        String desc = etDesc.getText().toString();
        HashMap<String, Object> newData = new HashMap<>();
        newData.put("description", desc);
        newData.put("title", title);

        databaseReference.child(mAuth.getUid()).child(key).updateChildren(n
                ewData);
    }
}