package com.example.damthixuanminh;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> data = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.minhhoalistview);

        listView = findViewById(R.id.lvproduct);

        // Thêm dữ liệu mẫu
        data.add("Bánh mì");
        data.add("Trứng");
        data.add("Sữa");
        data.add("Phô mai");

        // Gán dữ liệu vào adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);

        // Nhận dữ liệu từ QLSanPham gửi sang
        Bundle bundle = getIntent().getBundleExtra("du lieu");
        if (bundle != null) {
            String ten = bundle.getString("Ten");
            int tuoi = bundle.getInt("Tuoi");

            Toast.makeText(this, "Nhận dữ liệu: " + ten + " - Tuổi: " + tuoi, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Không có dữ liệu được truyền!", Toast.LENGTH_SHORT).show();
        }
    }
}
