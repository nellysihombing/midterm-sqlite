package com.example.midtermweek5;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText editName, editStudentId;
    TextView textAge, textData;
    SeekBar seekAge;
    Button buttonAdd, buttonEdit, buttonDelete, buttonView;
    DBHelper db;
    int age = 17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new DBHelper(this);
        editName = findViewById(R.id.editName);
        editStudentId = findViewById(R.id.editStudentId);
        textAge = findViewById(R.id.textAge);
        textData = findViewById(R.id.textData);
        seekAge = findViewById(R.id.seekAge);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonEdit = findViewById(R.id.buttonEdit);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonView = findViewById(R.id.buttonView);

        seekAge.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                age = progress + 17;
                textAge.setText("Age: " + age);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        buttonAdd.setOnClickListener(v -> {
            if (!isValid()) return;
            boolean ok = db.addStudent(editName.getText().toString().trim(),
                    editStudentId.getText().toString().trim(), age);
            Toast.makeText(this, ok ? "Data added" : "Failed to add", Toast.LENGTH_SHORT).show();
        });

        buttonEdit.setOnClickListener(v -> {
            if (!isValid()) return;
            int rows = db.updateStudent(editName.getText().toString().trim(),
                    editStudentId.getText().toString().trim(), age);
            Toast.makeText(this, rows > 0 ? "Data updated" : "Student ID not found",
                    Toast.LENGTH_SHORT).show();
        });

        buttonDelete.setOnClickListener(v -> {
            String sid = editStudentId.getText().toString().trim();
            if (sid.isEmpty()) {
                editStudentId.setError("Student ID is required");
                return;
            }
            int rows = db.deleteStudent(sid);
            Toast.makeText(this, rows > 0 ? "Data deleted" : "Student ID not found",
                    Toast.LENGTH_SHORT).show();
        });

        buttonView.setOnClickListener(v -> textData.setText(db.getAll()));
    }

    private boolean isValid() {
        if (editName.getText().toString().trim().isEmpty()) {
            editName.setError("Name is required");
            return false;
        }
        if (editStudentId.getText().toString().trim().isEmpty()) {
            editStudentId.setError("Student ID is required");
            return false;
        }
        return true;
    }
}