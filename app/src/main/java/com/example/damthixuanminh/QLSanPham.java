package com.example.damthixuanminh;import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class QLSanPham extends AppCompatActivity {
    ListView lvSanPham;
    // Danh sách gốc, không bao giờ thay đổi trực tiếp sau khi khởi tạo
    ArrayList<String> originalData = new ArrayList<>();
    // Adapter sẽ sử dụng danh sách này để hiển thị
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlsan_pham);

        lvSanPham = findViewById(R.id.lvproduct);

        // Thêm dữ liệu vào danh sách gốc
        originalData.add("Bánh mì");
        originalData.add("Trứng");
        originalData.add("Sữa");
        originalData.add("Phô mai");
        originalData.add("Kem");

        // Khởi tạo Adapter với một bản sao của dữ liệu gốc
        // Điều này cho phép chúng ta lọc mà không ảnh hưởng đến dữ liệu gốc
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>(originalData));
        lvSanPham.setAdapter(arrayAdapter);

        // Xử lý sự kiện nhấn giữ để xóa
        lvSanPham.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy tên sản phẩm cần xóa từ adapter
                String itemToRemove = arrayAdapter.getItem(position);

                // Xóa khỏi adapter và danh sách gốc
                arrayAdapter.remove(itemToRemove);
                originalData.remove(itemToRemove);

                // Không cần gọi notifyDataSetChanged() vì arrayAdapter.remove() đã tự làm điều đó
                Toast.makeText(QLSanPham.this, "Đã xóa: " + itemToRemove, Toast.LENGTH_SHORT).show();

                return true;
            }
        });
    }

    // "Thổi phồng" menu từ file XML
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuoption_add_exit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Xử lý các sự kiện khi nhấn vào item trên menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.thoatungdung) {
            finish(); // Thoát Activity
            return true;
        }

        if (id == R.id.truyendata) {
            Intent intent = new Intent(QLSanPham.this, MainActivity.class);
            Bundle b = new Bundle();
            b.putString("Ten", "Mỹ Anh");
            b.putInt("Tuoi", 23);
            intent.putExtra("du lieu", b);
            startActivity(intent);
            return true;
        }

        if (id == R.id.search) {
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setTitle("Tìm kiếm sản phẩm");
            final EditText input = new EditText(this);
            b.setView(input);

            b.setPositiveButton("Tìm kiếm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String keyword = input.getText().toString().toLowerCase().trim();

                    // Lọc dữ liệu từ danh sách gốc
                    ArrayList<String> filteredResults = new ArrayList<>();
                    for (String product : originalData) {
                        if (product.toLowerCase().contains(keyword)) {
                            filteredResults.add(product);
                        }
                    }

                    // Cập nhật adapter với dữ liệu đã lọc
                    arrayAdapter.clear();
                    arrayAdapter.addAll(filteredResults);

                    if (filteredResults.isEmpty()) {
                        Toast.makeText(QLSanPham.this, "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            b.setNegativeButton("Hủy", null); // Không cần làm gì khi hủy
            b.setNeutralButton("Hiện tất cả", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Hiển thị lại toàn bộ danh sách gốc
                    arrayAdapter.clear();
                    arrayAdapter.addAll(originalData);
                }
            });
            b.show();
            return true;
        }

        if (id == R.id.call) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:0942160880"));
            startActivity(intent);
            return true;
        }

        if (id == R.id.addpro) {
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setTitle("Thêm sản phẩm mới");
            final EditText input = new EditText(this);
            b.setView(input);

            b.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String newProduct = input.getText().toString().trim();
                    if (!newProduct.isEmpty()) {
                        // Thêm vào cả danh sách gốc và adapter để đồng bộ
                        originalData.add(newProduct);
                        arrayAdapter.add(newProduct);
                        // Không cần notifyDataSetChanged() vì arrayAdapter.add() đã tự làm
                        Toast.makeText(QLSanPham.this, "Đã thêm: " + newProduct, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            b.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(QLSanPham.this, "Đã hủy thêm sản phẩm", Toast.LENGTH_SHORT).show();
                }
            });
            b.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
