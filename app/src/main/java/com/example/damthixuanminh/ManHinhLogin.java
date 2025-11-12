package com.example.damthixuanminh;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ManHinhLogin extends AppCompatActivity {
Button btthoat,manhinh8; //button trong java
    EditText edu,edp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manhinh8);
        btthoat=(Button) findViewById(R.id.btthoat);//tim button trong xml
        manhinh8=(Button)  findViewById(R.id.btlogin);
        edu=(EditText) findViewById(R.id.eduser);
        edu=(EditText) findViewById(R.id.edpass);
        btthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ManHinhLogin.this, "Thoat ung dung", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        String u= edu.getText().toString();
        String p=edp.getText().toString();
        if(u.contains("admin") && (p.contains("123"))){
            Toast.makeText(this, "Ban dang nhap thanh cong", Toast.LENGTH_LONG).show();
        }
        else Toast.makeText(this, "Ban nhap sai thong tin,moi nhap lai", Toast.LENGTH_LONG).show();
    }

}